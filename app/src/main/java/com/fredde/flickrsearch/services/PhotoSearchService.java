package com.fredde.flickrsearch.services;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.api.FlickrService;
import com.fredde.flickrsearch.data.FlickrResponse;
import com.fredde.flickrsearch.data.PhotoEntry;
import com.fredde.flickrsearch.utils.FlickrUrlBuilder;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
public class PhotoSearchService extends IntentService {

    public static final String ACTION_FIND_PHOTOS = "com.fredde.services.FIND_PHOTOS";

    public static final String EXTRA_QUERY_STRING = "query_string";

    public static final String EXTRA_PAGE_NR = "page_nr";

    public static final String BROADCAST_SEARCH_COMPLETED = "searchCompleted";

    private static final String NAME = "PhotoSearchService";

    /* Flickr service API option keys */
    private static final String METHOD_KEY = "method";

    private static final String API_KEY_KEY = "api_key";

    private static final String FORMAT_KEY = "format";

    private static final String PER_PAGE_KEY = "per_page";

    private static final String EXTRAS_KEY = "extras";

    private static final String CALLBACK_KEY = "nojsoncallback";


    /* Flickr Service API option values. */
    private static final String METHOD_VALUE = "flickr" + ".photos.search";

    private static final String API_KEY_VALUE = "554ac5cebce4acd585f48e6255982909";

    private static final String FORMAT_VALUE = "json";

    private static final String PER_PAGE_VALUE = "10";

    private static final String EXTRAS_VALUE = "tags";

    private static final String CALLBACK_VALUE = "1";

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
    private FlickrService mApiService;


    /**
     * Creates an IntentService.
     */
    public PhotoSearchService() {
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

        RestAdapter adapter = new Builder().setEndpoint(FlickrService.ENDPOINT)
                .setLogLevel(LogLevel.FULL).setConverter(new GsonConverter(gson)).build();
        mApiService = adapter.create(FlickrService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, getResources().getString(R.string.toast_text_search_started),
                Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (ACTION_FIND_PHOTOS.equals(action)) {
            Bundle extra = intent.getExtras();
            String queryString = extra.getString(EXTRA_QUERY_STRING);
            int page = extra.getInt(EXTRA_PAGE_NR);

            queryApi(queryString, page);
        }
    }


    /**
     * Query the flicker service api.
     *
     * @param query the search string.
     */
    private void queryApi(String query, int page) {
        List<PhotoEntry> photos;
        FlickrResponse response = mApiService.getPhotos(sOptions, page, query);
        photos = response.holder.getPhotos();
        for (int i = 0; i < photos.size(); i++) {
            photos.get(i).setUrl(FlickrUrlBuilder.buildUrl(photos.get(i)));
        }

        Realm realm = Realm.getInstance(getApplicationContext());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(photos);
        realm.commitTransaction();

        realm.close();

        Intent intent = new Intent(BROADCAST_SEARCH_COMPLETED);
        intent.putExtra(EXTRA_QUERY_STRING, query);
        sendLocalBroadcast(intent);
    }

    /**
     * Uses the {@link LocalBroadcastManager} to broadcast the given intent.
     *
     * @param intent The Intent to broadcast.
     */
    private void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getApplicationContext());
        mgr.sendBroadcast(intent);
    }
}

