package com.fredde.flickrsearch.activities;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.activities.FullscreenActivity;
import com.fredde.flickrsearch.fragment.PhotoSearchFragment;
import com.fredde.flickrsearch.services.PhotoSearchService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements PhotoSearchFragment.Callback {

    /**
     * Id tag for the search list fragment.
     */
    private static final String SEARCH_LIST_TAG = "searchListFragment";


    /**
     * Broadcast receiver used to handle messages from the server.
     */
    private BroadcastReceiver mLocalReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PhotoSearchFragment(), SEARCH_LIST_TAG).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                PhotoSearchFragment fragment = (PhotoSearchFragment) getSupportFragmentManager()
                        .findFragmentByTag(SEARCH_LIST_TAG);
                String query = intent.getStringExtra(PhotoSearchService.EXTRA_QUERY_STRING);
                fragment.notifyQueryDataChanged(query);
            }
        };
        LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getApplicationContext());
        mgr.registerReceiver(mLocalReceiver,
                new IntentFilter(PhotoSearchService.BROADCAST_SEARCH_COMPLETED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getApplicationContext());
        mgr.unregisterReceiver(mLocalReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected(String id, View view) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        intent.setAction(FullscreenActivity.ACTION_VIEW_IMAGE);
        intent.putExtra(FullscreenActivity.EXTRA_PHOTO_ID, id);

        startActivityWithTransition(intent, view);
    }

    @Override
    public void onFetchData(String query, int page) {
        /* Create the Service Intent */
        Intent intent = new Intent(this, PhotoSearchService.class);
        intent.setAction(PhotoSearchService.ACTION_FIND_PHOTOS);

        /* Store the query string as an extra. */
        intent.putExtra(PhotoSearchService.EXTRA_QUERY_STRING, query);
        intent.putExtra(PhotoSearchService.EXTRA_PAGE_NR, page);
        startService(intent);
    }

    private void startActivityWithTransition(Intent intent, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* Get the shared views. */
            View image = view.findViewById(R.id.search_list_item_image);
            View statusBar = findViewById(android.R.id.statusBarBackground);
            View navigationBar = findViewById(android.R.id.navigationBarBackground);
            View toolbar = findViewById(R.id.toolbar);

            List<Pair<View, String>> pairs = new ArrayList<>();
            pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
            pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
            pairs.add(Pair.create(toolbar, toolbar.getTransitionName()));
            pairs.add(Pair.create(image, image.getTransitionName()));

            Bundle options = ActivityOptions.makeSceneTransitionAnimation(this,
                    pairs.toArray(new Pair[pairs.size()])).toBundle();

            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }
}
