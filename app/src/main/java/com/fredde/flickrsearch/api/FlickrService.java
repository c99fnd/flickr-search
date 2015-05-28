package com.fredde.flickrsearch.api;

import com.fredde.flickrsearch.models.FlickrResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Retrofit Service Interface
 */
public interface FlickrService {

    /**
     * Flickr API method, (ex: flickr.photos.search)
     */
    public static final String METHOD = "method";

    /**
     * The required API key.
     */
    public static final String API_KEY = "api_key";

    /**
     * The response format (ex: JSON)
     */
    public static final String FORMAT = "format";

    /**
     * The Flickr API method parameter.
     */
    public static final String TAGS = "tags";

    /**
     * Number of response items per page.
     */
    public static final String PER_PAGE = "per_page";

    /**
     * The requested response page.
     */
    public static final String PAGE = "page";

    /**
     * The extras requested in the response (ex: tags, dates, ...)
     */
    public static final String EXTRAS = "extras";

    /**
     * Callback for async method call.
     */
    public static final String CALLBACK = "nojsoncallback";

    /**
     * The flickr service endpoint.
     */
    static final String ENDPOINT = "https://api.flickr.com/services";

    @GET("/rest")
    void getPhotos(@Query(METHOD) String method, @Query(API_KEY) String apiKey,
            @Query(FORMAT) String format, @Query(TAGS) String tags,
            @Query(PER_PAGE) String perPage, @Query(PAGE) String page,
            @Query(EXTRAS) String extras, Callback<FlickrResponse> response);

    @GET("/rest")
    FlickrResponse getPhotos(@QueryMap Map<String, String> options, @Query(PAGE) int page,
            @Query(TAGS) String tags);

}
