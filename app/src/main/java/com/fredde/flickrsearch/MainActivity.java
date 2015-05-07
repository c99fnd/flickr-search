package com.fredde.flickrsearch;

import com.fredde.flickrsearch.callbacks.SearchListCallback;
import com.fredde.flickrsearch.fragment.FullscreenPhotoFragment;
import com.fredde.flickrsearch.fragment.SearchPhotoFragment;
import com.fredde.flickrsearch.services.PhotosSearchService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements SearchListCallback {

    private static final String SEARCH_LIST_TAG = "searchListFragment";
    private static final String FULLSCREEN_TAG = "fullscreenFragment";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SearchPhotoFragment(), SEARCH_LIST_TAG).commit();
        }

        /* For debug purposes. */
        Realm.deleteRealmFile(getApplicationContext());

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
        msgIntent.putExtra(PhotosSearchService.PARAM, query);
        startService(msgIntent);
    }
}
