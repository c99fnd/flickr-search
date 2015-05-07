package com.fredde.flickrsearch.callbacks;

/**
 * Callback.
 */
public interface SearchListCallback {

    /**
     * Called when a list item is selected.
     *
     * @param position position of the item that was selected.
     */
    public void onListItemSelected(int position);

    /**
     * Called when the SearchView is used.
     */
    public void onSearch(String query);
}
