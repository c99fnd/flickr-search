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
     * Returns the page number for the holder.
     *
     * @return The page number.
     */
    public int getPage() {
        return mPage;
    }

    /**
     * Sets the page number for the holder.
     *
     * @param mPage The page number
     */
    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    /**
     * Get the total number of pages available for the current query.
     *
     * @return Total number of pages.
     */
    public String getPages() {
        return mPages;
    }


    /**
     * Gets the number of {@link PhotoEntry}s per page for the current query.
     *
     * @return The number of {@link PhotoEntry}s per page.
     */
    public int getPerPage() {
        return mPerPage;
    }

    /**
     * Returns the total number of {@link PhotoEntry}s matching the query.
     *
     * @return The number matching the query.
     */
    public String getTotal() {
        return mTotal;
    }

    /**
     * Returns a list of photo {@link PhotoEntry}s held by this holder.
     *
     * @return A list of {@link PhotoEntry}s
     */
    public List<PhotoEntry> getPhotos() {
        return mPhotos;
    }

    /**
     * Returns the status for the query.
     *
     * @return String "ok" if the query was a success. "fail" otherwise.
     */
    public String getStat() {
        return mStat;
    }

    /**
     * Setters used for testing.
     */
    public void setPages(String pages) {
        mPages = pages;
    }

    public void setPerPage(int perPage) {
        mPerPage = perPage;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public void setPhotos(List<PhotoEntry> photos) {
        mPhotos = photos;
    }

    public void setStat(String status) {
        mStat = status;
    }
}
