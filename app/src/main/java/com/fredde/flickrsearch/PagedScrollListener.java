package com.fredde.flickrsearch;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * Handles paging for a scrollable.
 */
public abstract class PagedScrollListener implements OnScrollListener {


    private int mCurrentPage = 0;
    private int mLoadThreshold = 10;
    private boolean mLoading = false;

    public PagedScrollListener() {

    }

    public PagedScrollListener(int loadThreshold) {
        mLoadThreshold = loadThreshold;
    }

    public PagedScrollListener(int loadThreshold, int startPage) {
        mLoadThreshold = loadThreshold;
        mCurrentPage = startPage;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /* Do nothing. */
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {

        int remaningItems = totalItemCount - (firstVisibleItem + visibleItemCount);

        Log.d("FREDDE", "firstVisibleItem " + firstVisibleItem);
        Log.d("FREDDE", "visibleItemCount " + visibleItemCount);
        Log.d("FREDDE", "totalItemCount " + totalItemCount);
        Log.d("FREDDE", "remaningItems" + remaningItems);

        if (!mLoading &&remaningItems <= mLoadThreshold) {
            mLoading = true;
            mCurrentPage++;
            onLoadMore(mCurrentPage);
        }

    }

    public abstract void onLoadMore(int page);
}
