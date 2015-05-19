package com.fredde.flickrsearch;

import com.fredde.flickrsearch.callbacks.SearchListCallback;
import com.fredde.flickrsearch.fragment.FullscreenPhotoFragment;
import com.fredde.flickrsearch.fragment.SearchPhotoFragment;
import com.fredde.flickrsearch.services.PhotosSearchService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Visibility;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements SearchListCallback {

    private static final String SEARCH_LIST_TAG = "searchListFragment";

    private static final String FULLSCREEN_TAG = "fullscreenFragment";

    private BroadcastReceiver mLocalReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SearchPhotoFragment(), SEARCH_LIST_TAG).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SearchPhotoFragment fragment = (SearchPhotoFragment)getSupportFragmentManager().findFragmentByTag
                        (SEARCH_LIST_TAG);
                String query = intent.getStringExtra(PhotosSearchService.QUERY_STRING_EXTRA);
                fragment.updateSearchResult(query);

            }
        };
        LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getApplicationContext());
        mgr.registerReceiver(mLocalReciever,
                new IntentFilter(PhotosSearchService.BROADCAST_SEARCH_COMPLETED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getApplicationContext());
        mgr.unregisterReceiver(mLocalReciever);
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
    public void onListItemSelected(String id) {
        FullscreenPhotoFragment frag = (FullscreenPhotoFragment)getSupportFragmentManager()
                .findFragmentByTag(FULLSCREEN_TAG);

        Bundle args = new Bundle();
        args.putString(FullscreenPhotoFragment.ARG_ITEM, id);
        if (frag != null) {
            frag.setArguments(args);
        } else {
            frag = new FullscreenPhotoFragment();
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                frag.setEnterTransition(new Fade(Visibility.MODE_IN));
                frag.setExitTransition(new Fade(Visibility.MODE_OUT));
                frag.setAllowEnterTransitionOverlap(true);
            }
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, FULLSCREEN_TAG).addToBackStack(null).commit();
        }
    }

    @Override
    public void onSearch(String query) {
        /* Create the Service Intent */
        Intent msgIntent = new Intent(this, PhotosSearchService.class);

        /* Store the query string as an extra. */
        msgIntent.putExtra(PhotosSearchService.QUERY_STRING_EXTRA, query);
        startService(msgIntent);
    }
}
