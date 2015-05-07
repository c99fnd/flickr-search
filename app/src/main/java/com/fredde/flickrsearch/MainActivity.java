package com.fredde.flickrsearch;

import com.fredde.flickrsearch.callbacks.SearchListCallback;
import com.fredde.flickrsearch.data.FlickrPhoto;
import com.fredde.flickrsearch.fragment.SearchPhotoFragment;
import com.fredde.flickrsearch.services.PhotosSearchService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;


public class MainActivity extends AppCompatActivity implements SearchListCallback,
        RealmChangeListener {


    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SearchPhotoFragment()).commit();
        }

        Realm.deleteRealmFile(getApplicationContext());
        mRealm = Realm.getInstance(getApplicationContext());
        mRealm.addChangeListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListItemSelected() {

    }

    @Override
    public void onSearch(String query) {
        /* Create the Service Intent */
        Intent msgIntent = new Intent(this, PhotosSearchService.class);

        /* Store the query string as an extra. */
        msgIntent.putExtra(PhotosSearchService.PARAM, query);
        startService(msgIntent);
    }

    @Override
    public void onChange() {
        Log.d("FREDDE", "realm has changed");

        List<FlickrPhoto> photos = mRealm.allObjects(FlickrPhoto.class);
        Log.d("FREDDE", "photos" + photos.size());
    }
}
