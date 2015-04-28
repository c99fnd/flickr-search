package com.fredde.flickrsearch.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Data representation of a photo.
 */
public class FlickrPhoto implements Serializable {

    /**
     * The id of this photo.
     */
    @SerializedName("id")
    private String mId;

    /**
     * Id for the owner of this photo.
     */
    @SerializedName("owner")
    private String mOwner;

    /**
     * Secret.
     */
    @SerializedName("secret")
    private String mSecret;

    /**
     * Server
     */
    @SerializedName("server")
    private String mServer;

    /**
     * Farm.
     */
    @SerializedName("farm")
    private String mFarm;

    /**
     * Title of this photo.
     */
    @SerializedName("title")
    private String mTitle;

    /**
     * A public photo?
     */
    @SerializedName("ispublic")
    private int mIsPublic;

    /**
     * Is photo owner a friend?
     */
    @SerializedName("isfriend")
    private int mIsFriend;

    /**
     * Is photo owner a family member?
     */
    @SerializedName("isfamily")
    private int mIsFamily;


    /**
     * Gets the photo Id.
     *
     * @return The id.
     */
    public String getId() {
        return mId;
    }

    /**
     * Set photo id.
     *
     * @param id The id to set.
     * @return this
     */
    public FlickrPhoto setId(String id) {
        mId = id;
        return this;
    }

    /**
     * Gets the id of the photo owner.
     *
     * @return this.
     */
    public String getOwner() {
        return mOwner;
    }

    /**
     * Sets id of the photo owner.
     *
     * @param owner id of the owner of the photo.
     * @return this.
     */
    public FlickrPhoto setOwner(String owner) {
        mOwner = owner;
        return this;
    }

    /**
     * Gets the secret.
     *
     * @return The secret.
     */
    public String getSecret() {
        return mSecret;
    }

    /**
     * Sets the secret
     *
     * @param secret The secret to set
     * @return this.
     */
    public FlickrPhoto setSecret(String secret) {
        mSecret = secret;
        return this;
    }

    /**
     * Gets the server
     *
     * @return The server
     */
    public String getServer() {
        return mServer;
    }

    /**
     * Set the server
     *
     * @param server The server to set.
     * @return this.
     */
    public FlickrPhoto setServer(String server) {
        mServer = server;
        return this;
    }

    /**
     * Gets the farm.
     *
     * @return the farm.
     */
    public String getFarm() {
        return mFarm;
    }

    /**
     * Sets the farm for this photo.
     *
     * @param farm The farm to set.
     * @return this
     */
    public FlickrPhoto setFarm(String farm) {
        mFarm = farm;
        return this;
    }

    /**
     * Gets goal title.
     *
     * @return The tile of the goal.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets goal tile.
     *
     * @param title The title to set.
     * @return this.
     */
    public FlickrPhoto setTitle(String title) {
        mTitle = title;
        return this;
    }

    /**
     * Return an int representing the photo being public or not.
     *
     * @return 1 if public. 0 if not.
     */
    public int getIsPublic() {
        return mIsPublic;
    }

    /**
     * Sets if this photo is public or not.
     *
     * @param isPublic 1 if public. 0 if not.
     * @return this.
     */
    public FlickrPhoto setIsPublic(int isPublic) {
        mIsPublic = isPublic;
        return this;
    }

    /**
     * Returns an int representing the photo owner being a friend or not.
     *
     * @return 1 if owner is a friend. 0 if not.
     */
    public int getIsFriend() {
        return mIsFriend;
    }

    /**
     * Sets if this photo owner is a friend or not.
     *
     * @param isFriend 1 if friend. 0 if not.
     * @return this.
     */
    public FlickrPhoto setIsFriend(int isFriend) {
        mIsFriend = isFriend;
        return this;
    }

    /**
     * Returns an int representing the photo owner being a family member or not.
     *
     * @return 1 if owner is a family member. 0 if not.
     */
    public int getIsFamily() {
        return mIsFamily;
    }

    /**
     * Sets if this photo owner is a family member or not.
     *
     * @param isFamily 1 if friend. 0 if not.
     * @return this.
     */
    public FlickrPhoto setIsFamily(int isFamily) {
        mIsFamily = isFamily;
        return this;
    }
}
