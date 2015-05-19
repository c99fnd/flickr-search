package com.fredde.flickrsearch.services;

import com.fredde.flickrsearch.FlickrUrlBuilder;
import com.fredde.flickrsearch.api.FlickrApiService;
import com.fredde.flickrsearch.data.FlickrPhoto;
import com.fredde.flickrsearch.data.FlickrResponse;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

/**
 * Service that searches flicker and persists the result in a db.
 */
public class PhotosSearchService extends IntentService {

    public static final String PARAM = "query_string";

    private static final String NAME = "PhotoSearchService";

    /* Flickr service API option keys */
    private final static String METHOD_KEY = "method";

    private final static String API_KEY_KEY = "api_key";

    private final static String FORMAT_KEY = "format";

    private final static String PER_PAGE_KEY = "per_page";

    private final static String EXTRAS_KEY = "extras";

    private final static String CALLBACK_KEY = "nojsoncallback";


    /* Flickr Service API option values. */
    private final static String METHOD_VALUE = "flickr" + ".photos.search";

    private final static String API_KEY_VALUE = "554ac5cebce4acd585f48e6255982909";

    private final static String FORMAT_VALUE = "json";

    private final static String PER_PAGE_VALUE = "10";

    private final static String EXTRAS_VALUE = "tags";

    private final static String CALLBACK_VALUE = "1";


    private final static String SECRET = "1df6db5425b75cc4";

    /**
     * Options map for the Flickr Service API.
     */
    private static Map<String, String> sOptions = new HashMap<String, String>();

    static {
        sOptions.put(METHOD_KEY, METHOD_VALUE);
        sOptions.put(API_KEY_KEY, API_KEY_VALUE);
        sOptions.put(FORMAT_KEY, FORMAT_VALUE);
        sOptions.put(PER_PAGE_KEY, PER_PAGE_VALUE);
        sOptions.put(EXTRAS_KEY, EXTRAS_VALUE);
        sOptions.put(CALLBACK_KEY, CALLBACK_VALUE);
    }

    /**
     * The flicker api service.
     */
    private FlickrApiService mApiService;


    /**
     * Creates an IntentService.
     */
    public PhotosSearchService() {
        super(NAME);

        /* Create a new Gson object adapted to RealmObject class */
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        RestAdapter adapter = new Builder().setEndpoint(FlickrApiService.ENDPOINT)
                .setLogLevel(LogLevel.FULL).setConverter(new GsonConverter(gson)).build();
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
        List<FlickrPhoto> photos;
        FlickrResponse response = mApiService.getPhotos(sOptions, query);
        photos = response.holder.getPhotos();
        for (int i = 0; i < photos.size(); i++) {
            photos.get(i).setUrl(FlickrUrlBuilder.buildUrl(photos.get(i)));
        }

        Realm realm = Realm.getInstance(getApplicationContext());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(photos);
        realm.commitTransaction();

        realm.close();
    }
}

