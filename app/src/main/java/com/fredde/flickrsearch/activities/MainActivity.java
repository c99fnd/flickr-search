package com.fredde.flickrsearch.activities;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.adapters.SearchResultAdapter;
import com.fredde.flickrsearch.listeners.PagedScrollListener;
import com.fredde.flickrsearch.models.PhotoEntry;
import com.fredde.flickrsearch.services.PhotoSearchService;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements OnQueryTextListener {

    /**
     * Shared Prefs key.
     */
    private static final String SEARCH_STRING_KEY = "search_string";

    /**
     * Default threshold for on-demand loading.
     */
    private static final int DEFAULT_THRESHOLD = 10;

    /**
     * Broadcast receiver used to handle messages from the server.
     */
    private BroadcastReceiver mLocalReceiver;

    /**
     * The list adapter feeding the list with search results.
     */
    private SearchResultAdapter mAdapter;

    /**
     * Realm instance.
     */
    private Realm mRealm;

    /**
     * Query string used in the last search.
     */
    private String mLastQuery;

    /**
     * The action bar SearchView.
     */
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        mRealm = Realm.getInstance(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

         /* Restore the last search made before onDestory */
        mLastQuery = readSearchStringFromPrefs();
        RealmResults<PhotoEntry> data = getPhotosFromDb(mLastQuery);
        mAdapter = new SearchResultAdapter(this, data, false);

        ListView listView = (ListView)findViewById(R.id.search_list);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoEntry photo = mAdapter.getItem(position);
                onListItemSelected(photo.getId(), view);
            }
        });

        /* Scroll listener used for on-demand loading of data. */
        listView.setOnScrollListener(new PagedScrollListener(DEFAULT_THRESHOLD) {
            @Override
            public void fetchNextPage(int page) {
                fetchPhotoData(mLastQuery, page);
            }
        });
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String query = intent.getStringExtra(PhotoSearchService.EXTRA_QUERY_STRING);
                RealmResults<PhotoEntry> results = getPhotosFromDb(query);
                mAdapter.updateRealmResults(results);
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
        writeSearchStringInPrefs(mLastQuery);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mAdapter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu, menu);

        /* Clear the search view if its already been set up. */
        clearSearchView();

        /* Find and setup the SearchView. */
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView)item.getActionView();
        mSearchView.setQueryHint(getResources().getString(R.string.action_search));
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        /** Update the list with data from Realm. **/
        RealmResults<PhotoEntry> results = getPhotosFromDb(query);
        mAdapter.updateRealmResults(results);
        mSearchView.clearFocus();

        fetchPhotoData(query, PhotoSearchService.FIRST_PAGE);
        mLastQuery = query;
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        /* Do nothing. */
        return false;
    }

    private void onListItemSelected(String id, View view) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        intent.setAction(FullscreenActivity.ACTION_VIEW_IMAGE);
        intent.putExtra(FullscreenActivity.EXTRA_PHOTO_ID, id);

        startActivityWithTransition(intent, view);
    }


    private void fetchPhotoData(String query, int page) {
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
            pairs.add(Pair.create(toolbar, toolbar.getTransitionName()));
            pairs.add(Pair.create(image, image.getTransitionName()));
            pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
            /* Navigation bar is null in landscape and will not be a part of the transition. */
            if (navigationBar != null) {
                pairs.add(Pair.create(navigationBar,
                        Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
            }

            Bundle options = ActivityOptions
                    .makeSceneTransitionAnimation(this, pairs.toArray(new Pair[pairs.size()]))
                    .toBundle();
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    /**
     * Retrieves data from database.
     *
     * @param query The query to use or null if all data is supposed to be fetched.
     * @return RealmResults The result as a list.
     */
    private RealmResults<PhotoEntry> getPhotosFromDb(@Nullable String query) {
        RealmResults<PhotoEntry> res;
        if (query != null) {
            res = mRealm.where(PhotoEntry.class).contains("tags", query.toLowerCase()).findAll();
        } else {
            res = mRealm.where(PhotoEntry.class).findAll();
        }
        return res;
    }

    /**
     * Stores the given string to a pre defined key in shared prefs.
     *
     * @param string the string to store.
     */
    private void writeSearchStringInPrefs(String string) {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SEARCH_STRING_KEY, string);
        editor.commit();
    }

    /**
     * Reads a string from a given key in shared prefs.
     *
     * @return The string read.
     */
    private String readSearchStringFromPrefs() {
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        return settings.getString(SEARCH_STRING_KEY, null);
    }

    /**
     * Clears the SearchView by setting its OnQueryListener to null.
     */
    private void clearSearchView() {
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(null);
        }
    }
}
