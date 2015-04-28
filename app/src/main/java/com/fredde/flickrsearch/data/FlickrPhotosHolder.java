package com.fredde.flickrsearch.data;

import com.google.gson.annotations.SerializedName;

/**
 * Holder for flickr photos.
 */
public class FlickrPhotosHolder {

    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private String mPages;

    @SerializedName("perpage")
    private int mPerPage;

    @SerializedName("total")
    private String mTotal;

    @SerializedName("photo")
    private FlickrPhoto[] mPhotos;

    @SerializedName("stat")
    private String mStat;

    /**
     *
     * @return
     */
    public FlickrPhoto[] getPhotos() {
        return mPhotos != null ? mPhotos.clone() : new FlickrPhoto[0];
    }

    /**
     *
     * @return
     */
    public String getStat(){
        return mStat;
    }
}
