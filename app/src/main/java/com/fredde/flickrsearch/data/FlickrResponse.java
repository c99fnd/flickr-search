package com.fredde.flickrsearch.data;

import com.google.gson.annotations.SerializedName;

/**
 * Flickr API Response.
 */
public class FlickrResponse {

    @SerializedName("photos")
    public PhotoEntriesHolder holder;
}
