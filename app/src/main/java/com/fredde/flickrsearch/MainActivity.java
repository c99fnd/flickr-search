package com.fredde.flickrsearch;

import com.fredde.flickrsearch.fragment.PhotoSearchFragment;
import com.fredde.flickrsearch.fragment.PhotoViewFragment;
import com.fredde.flickrsearch.services.PhotoSearchService;

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


public class MainActivity extends AppCompatActivity implements PhotoSearchFragment.Callback {

    /**
     * Id tag for the search list fragment.
     */
    private static final String SEARCH_LIST_TAG = "searchListFragment";

    /**
     * Id tag for the fullscreen fragment.
     */
    private static final String FULLSCREEN_TAG = "fullscreenFragment";

    /**
     * Broadcast receiver used to handle messages from the server.
     */
    private BroadcastReceiver mLocalReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                PhotoSearchFragment fragment = (PhotoSearchFragment)getSupportFragmentManager()
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
    public void onListItemSelected(String id) {
        PhotoViewFragment frag = (PhotoViewFragment)getSupportFragmentManager()
                .findFragmentByTag(FULLSCREEN_TAG);

        Bundle args = new Bundle();
        args.putString(PhotoViewFragment.ARG_ITEM, id);
        if (frag != null) {
            frag.setArguments(args);
        } else {
            frag = new PhotoViewFragment();
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
    public void onFetchData(String query, int page) {
        /* Create the Service Intent */
        Intent intent = new Intent(this, PhotoSearchService.class);
        intent.setAction(PhotoSearchService.ACTION_FIND_PHOTOS);

        /* Store the query string as an extra. */
        intent.putExtra(PhotoSearchService.EXTRA_QUERY_STRING, query);
        intent.putExtra(PhotoSearchService.EXTRA_PAGE_NR, page);
        startService(intent);
    }
}
