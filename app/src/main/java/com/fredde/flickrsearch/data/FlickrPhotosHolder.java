package com.fredde.flickrsearch.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    private List<FlickrPhoto> mPhotos;

    @SerializedName("stat")
    private String mStat;

    /**
     *
     * @return
     */
    public List<FlickrPhoto> getPhotos() {
        return mPhotos;
    }

    /**
     *
     * @return
     */
    public String getStat(){
        return mStat;
    }
}
