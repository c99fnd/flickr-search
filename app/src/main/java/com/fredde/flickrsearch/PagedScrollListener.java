package com.fredde.flickrsearch;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * Handles paging for a scrollable.
 */
public class PagedScrollListener implements OnScrollListener {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /* Do nothing. */
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
        Log.d("FREDDE", "PagedScrollListener.onScroll " + totalItemCount);
    }
}
