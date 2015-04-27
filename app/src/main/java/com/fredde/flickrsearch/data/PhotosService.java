package com.fredde.flickrsearch.data;

/**
 * Retrofit Service Interface
 */
public interface PhotosService {

    @GET("/rest/")
    void getPhotos(@Query("method") String method, @Query("api_key") String apiKey, @Query("tags")
    String tags, @Query("per_page") String perPage, @Query("format") String format,
                   Callback<com.fredde.flickrsearch.data.PhotoItem> data);
}
