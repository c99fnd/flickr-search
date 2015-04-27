package com.fredde.flickrsearch.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Service that searches flicker and persists the result in a db.
 */
public class SearchPhotosService extends IntentService{

    private final static String KEY = "554ac5cebce4acd585f48e6255982909";

    private final static String SECRET = "1df6db5425b75cc4";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SearchPhotosService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
