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
     * Creates a flickr photo url for a given {@link PhotoEntry}.
     *
     * @param photo The photo entry to create the url for.
     */
    public static void createImageUrl(PhotoEntry photo) {
        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        int farm = photo.getFarm();
        String server = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();
        String url = HEAD + FARM + farm + TAIL + server + "/" + id + "_" + secret + JPG;

        photo.setUrl(url);
    }
}
