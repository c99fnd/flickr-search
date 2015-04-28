package com.fredde.flickrsearch.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 23052956 on 4/28/15.
 */
public class FlickrResponse {

    @SerializedName("photos")
    public FlickrPhotosHolder holder;
}
