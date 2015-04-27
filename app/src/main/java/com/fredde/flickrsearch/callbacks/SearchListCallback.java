package com.fredde.flickrsearch.callbacks;

/**
 * Callback.
 */
public interface SearchListCallback {

    /**
     * Called when a list item is selected.
     */
    public void onListItemSelected();

    /**
     * Called when the SearchView is used.
     */
    public void onSearch(String query);
}
