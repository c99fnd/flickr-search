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

    /**
     * Called when the SearchView requires more data to be loaded.
     *
     * @param page    The page number to load.
     * @param perPage Number of items per page.
     */
    public void onLoadMore(int page, int perPage);
}
