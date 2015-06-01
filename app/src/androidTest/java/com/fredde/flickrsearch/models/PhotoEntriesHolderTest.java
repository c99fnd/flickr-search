package com.fredde.flickrsearch.models;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Data model test for PhotoEntry.
 */
public class PhotoEntriesHolderTest extends TestCase {

    private static final int PAGE = 1;
    private static final String PAGES = "21";
    private static final int PER_PAGE = 20;
    private static final String TOTAL_NUM_ENTRIES = "1234";
    private static final String STATUS = "ok";

    private PhotoEntriesHolder testPhotoEntryHolder;

    public void setUp() throws Exception {
        super.setUp();
        List<PhotoEntry> photos = new ArrayList<>();
        photos.add(new PhotoEntry());

        testPhotoEntryHolder = new PhotoEntriesHolder();
        testPhotoEntryHolder.setPage(PAGE);
        testPhotoEntryHolder.setPages(PAGES);
        testPhotoEntryHolder.setPerPage(PER_PAGE);
        testPhotoEntryHolder.setTotal("1234");
        testPhotoEntryHolder.setPhotos(photos);
        testPhotoEntryHolder.setStat("ok");
    }


    public void testGetPage() throws Exception {
        assertEquals(testPhotoEntryHolder.getPage(), PAGE);
    }

    public void testGetPages() throws Exception {
        assertEquals(testPhotoEntryHolder.getPages(), PAGES);
    }

    public void testGetPerPage() throws Exception {
        assertEquals(testPhotoEntryHolder.getPerPage(), PER_PAGE);
    }

    public void testGetTotal() throws Exception {
        assertEquals(testPhotoEntryHolder.getTotal(), TOTAL_NUM_ENTRIES);
    }

    public void testGetPhotos() throws Exception {
        List<PhotoEntry> photos = testPhotoEntryHolder.getPhotos();
        assertEquals(photos.size(), 1);
    }

    public void testGetStat() throws Exception {
        assertEquals(testPhotoEntryHolder.getStat(), STATUS);
    }
}
