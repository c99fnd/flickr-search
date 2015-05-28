package com.fredde.flickrsearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Holder for photo entries response from FlickrApi.
 */
public class PhotoEntriesHolder {

    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private String mPages;

    @SerializedName("perpage")
    private int mPerPage;

    @SerializedName("total")
    private String mTotal;

    @SerializedName("photo")
    private List<PhotoEntry> mPhotos;

    @SerializedName("stat")
    private String mStat;

    /**
     *
     * @return
     */
    public List<PhotoEntry> getPhotos() {
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
