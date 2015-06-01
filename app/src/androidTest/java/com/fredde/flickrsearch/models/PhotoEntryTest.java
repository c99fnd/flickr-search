package com.fredde.flickrsearch.models;

import junit.framework.TestCase;

/**
 * Data model test for PhotoEntry.
 */
public class PhotoEntryTest extends TestCase {

    private static final String ID = "12345678";
    private static final String OWNER = "12345678AB";
    private static final String SECRET = "1a2b3c4d5e";
    private static final String SERVER = "1234";
    private static final String TITLE = "test title";
    private static final int FARM = 9;
    private static final String TAGS = "test tags";
    private static final String HTTP_URL = "http://cloud.somewhere.now";
    private static final int IS_PUBLIC = 1;
    private static final int IS_FRIEND = 0;
    private static final int IS_FAMILY = 2;

    private PhotoEntry testPhotoEntry;


    public void setUp() throws Exception {
        super.setUp();
        testPhotoEntry = new PhotoEntry();
        testPhotoEntry.setId(ID);
        testPhotoEntry.setOwner(OWNER);
        testPhotoEntry.setSecret(SECRET);
        testPhotoEntry.setServer(SERVER);
        testPhotoEntry.setFarm(FARM);
        testPhotoEntry.setTitle(TITLE);
        testPhotoEntry.setTags(TAGS);
        testPhotoEntry.setUrl(HTTP_URL);
        testPhotoEntry.setIsPublic(IS_PUBLIC);
        testPhotoEntry.setIsFriend(IS_FRIEND);
        testPhotoEntry.setIsFamily(IS_FAMILY);
    }

    public void tearDown() throws Exception {
    }

    public void testGetId() throws Exception {
        assertEquals(testPhotoEntry.getId(), ID);
    }

    public void testGetOwner() throws Exception {
        assertEquals(testPhotoEntry.getOwner(), OWNER);
    }

    public void testGetSecret() throws Exception {
        assertEquals(testPhotoEntry.getSecret(), SECRET);
    }

    public void testGetServer() throws Exception {
        assertEquals(testPhotoEntry.getServer(), SERVER);
    }

    public void testGetFarm() throws Exception {
        assertEquals(testPhotoEntry.getFarm(), FARM);
    }

    public void testGetTitle() throws Exception {
        assertEquals(testPhotoEntry.getTitle(), TITLE);
    }

    public void testGetTags() throws Exception {
        assertEquals(testPhotoEntry.getTags(), TAGS);
    }

    public void testGetUrl() throws Exception {
        assertEquals(testPhotoEntry.getUrl(), HTTP_URL);
    }

    public void testGetIsPublic() throws Exception {
        assertEquals(testPhotoEntry.getIsPublic(), IS_PUBLIC);
    }

    public void testGetIsFriend() throws Exception {
        assertEquals(testPhotoEntry.getIsFriend(), IS_FRIEND);
    }

    public void testGetIsFamily() throws Exception {
        assertEquals(testPhotoEntry.getIsFamily(), IS_FAMILY);
    }
}

