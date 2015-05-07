package com.fredde.flickrsearch.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Data representation of a photo.
 */
public class FlickrPhoto extends RealmObject implements Serializable {

    /**
     * The id of this photo.
     */
    @PrimaryKey
    private String id;

    /**
     * Id for the owner of this photo.
     */
    @SerializedName("owner")
    private String owner;

    /**
     * Secret.
     */
    @SerializedName("secret")
    private String secret;

    /**
     * Server
     */
    @SerializedName("server")
    private String server;

    /**
     * Farm.
     */
    @SerializedName("farm")
    private String farm;

    /**
     * Title of this photo.
     */
    @SerializedName("title")
    private String title;

    /**
     * A public photo?
     */
    @SerializedName("ispublic")
    private int isPublic;

    /**
     * Is photo owner a friend?
     */
    @SerializedName("isfriend")
    private int isFriend;

    /**
     * Is photo owner a family member?
     */
    @SerializedName("isfamily")
    private int isFamily;


    /**
     * Gets the photo Id.
     *
     * @return The id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set photo id.
     *
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the id of the photo owner.
     *
     * @return the owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets id of the photo owner.
     *
     * @param owner id of the owner of the photo.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the secret.
     *
     * @return The secret.
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret
     *
     * @param secret The secret to set
     * @return this.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets the server
     *
     * @return The server
     */
    public String getServer() {
        return server;
    }

    /**
     * Set the server
     *
     * @param server The server to set.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Gets the farm.
     *
     * @return the farm.
     */
    public String getFarm() {
        return farm;
    }

    /**
     * Sets the farm for this photo.
     *
     * @param farm The farm to set.
     */
    public void setFarm(String farm) {
        this.farm = farm;
    }

    /**
     * Gets goal title.
     *
     * @return The tile of the goal.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets goal tile.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return an int representing the photo being public or not.
     *
     * @return 1 if public. 0 if not.
     */
    public int getIsPublic() {
        return isPublic;
    }

    /**
     * Sets if this photo is public or not.
     *
     * @param isPublic 1 if public. 0 if not.
     */
    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Returns an int representing the photo owner being a friend or not.
     *
     * @return 1 if owner is a friend. 0 if not.
     */
    public int getIsFriend() {
        return isFriend;
    }

    /**
     * Sets if this photo owner is a friend or not.
     *
     * @param isFriend 1 if friend. 0 if not.
     */
    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    /**
     * Returns an int representing the photo owner being a family member or not.
     *
     * @return 1 if owner is a family member. 0 if not.
     */
    public int getIsFamily() {
        return isFamily;
    }

    /**
     * Sets if this photo owner is a family member or not.
     *
     * @param isFamily 1 if friend. 0 if not.
     */
    public void setIsFamily(int isFamily) {
        this.isFamily = isFamily;
    }
}
