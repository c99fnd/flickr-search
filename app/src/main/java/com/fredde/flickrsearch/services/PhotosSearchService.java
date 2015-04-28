package com.fredde.flickrsearch.services;

import com.fredde.flickrsearch.data.FlickrPhotosService;

import android.app.IntentService;
import android.content.Intent;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;

/**
 * Service that searches flicker and persists the result in a db.
 */
public class PhotosSearchService extends IntentService{

    private static final String ENDPOINT = "https://api.flickr.com/services/rest";

    private static final String METHOD = "flickr.photos.search";

    private final static String KEY = "554ac5cebce4acd585f48e6255982909";

    private final static String SECRET = "1df6db5425b75cc4";

    FlickrPhotosService mService;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PhotosSearchService(String name) {
        super(name);
        RestAdapter adapter = new Builder().setEndpoint(ENDPOINT).build();
        mService = adapter.create(FlickrPhotosService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
