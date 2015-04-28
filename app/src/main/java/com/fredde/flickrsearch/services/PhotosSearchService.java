package com.fredde.flickrsearch.services;

import com.fredde.flickrsearch.api.FlickrApiService;
import com.fredde.flickrsearch.data.FlickrPhoto;
import com.fredde.flickrsearch.data.FlickrPhotosHolder;
import com.fredde.flickrsearch.data.FlickrResponse;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;

/**
 * Service that searches flicker and persists the result in a db.
 */
public class PhotosSearchService extends IntentService {

    public static final String PARAM = "query_string";

    private static final String ENDPOINT = "https://api.flickr.com/services";

    private static final String METHOD = "flickr.photos.search";

    private final static String KEY = "554ac5cebce4acd585f48e6255982909";

    private static final String FORMAT = "json";

    private final static String SECRET = "1df6db5425b75cc4";

    private static Map<String, String> sOptions = new HashMap<String, String>();

    /**
     * Options for service api.
     */
    static {
        sOptions.put("method", METHOD);
        sOptions.put("api_key", KEY);
        sOptions.put("format", FORMAT);
        sOptions.put("per_page", "10");
        sOptions.put("nojsoncallback", "1");
    }

    /**
     * The flicker api service.
     */
    private FlickrApiService mApiService;


    /**
     * Creates an IntentService.
     */
    public PhotosSearchService() {
        super("PhotoSearchService");
        RestAdapter adapter = new Builder().setEndpoint(ENDPOINT).setLogLevel(LogLevel.FULL)
                .build();
        mApiService = adapter.create(FlickrApiService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extra = intent.getExtras();
        String queryString = extra.getString(PARAM);

        queryApi(queryString);
    }

    /**
     * Query the flicker service api.
     *
     * @param query the search string.
     */
    private void queryApi(String query) {
        FlickrPhoto[] photos;
        FlickrResponse response = mApiService.getPhotos(sOptions, query);
        photos = response.holder.getPhotos();

        Log.d("Fredde", "queryApi " + photos.length);
    }
}

