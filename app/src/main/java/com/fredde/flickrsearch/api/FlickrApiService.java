package com.fredde.flickrsearch.api;

import com.fredde.flickrsearch.data.FlickrResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Retrofit Service Interface
 */
public interface FlickrApiService {

    static final String ENDPOINT = "https://api.flickr.com/services";

    @GET("/rest")
    void getPhotos(@Query("method") String method, @Query("api_key") String apiKey,
            @Query("tags") String tags, @Query("per_page") String perPage,
            @Query("format") String format, @Query("extras") String extras,
            Callback<FlickrResponse> response);


    @GET("/rest")
    FlickrResponse getPhotos(@QueryMap Map<String, String> options);

    @GET("/rest")
    FlickrResponse getPhotos(@QueryMap Map<String, String> options, @Query("tags") String tags);
}
