package com.fredde.flickrsearch.callbacks;

/**
 * Callback.
 */
public interface SearchListCallback {

    /**
     * Called when a list item is selected.
     *
     * @param id The id of the item that was selected.
     */
    public void onListItemSelected(String id);

    /**
     * Called when the SearchView is used.
     */
    public void onSearch(String query);
}
