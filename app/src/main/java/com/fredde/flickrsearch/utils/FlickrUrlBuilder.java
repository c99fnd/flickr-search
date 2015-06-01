package com.fredde.flickrsearch.utils;

import com.fredde.flickrsearch.models.PhotoEntry;

/**
 * Helper class for creating image url from FlickrPhoto object.
 */
public class FlickrUrlBuilder {

    private final static String HEAD = "https://";

    private final static String FARM = "farm";

    private final static String TAIL = ".staticflickr.com/";

    private final static String JPG = ".jpg";

    private final static String DEFAULT_SIZE = "m";


    /**
     * No instance.
     */
    private FlickrUrlBuilder() {
    }

    /**
     *
     * @param photo
     * @return
     */
    public static String buildUrl(PhotoEntry photo){
       // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        int farm = photo.getFarm();
        String server = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();

        return HEAD+FARM+farm+TAIL+server+"/"+id+"_"+secret+JPG;
    }

    /**
     *
     * @param photo
     * @param size
     * @return
     */
    public static String buildUrl(PhotoEntry photo, PhotoSize size){
        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size}.jpg
        int farm = photo.getFarm();
        String server = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();

        return HEAD+FARM+farm+TAIL+server+"/"+id+"_"+secret+"_"+size+JPG;
    }
}
