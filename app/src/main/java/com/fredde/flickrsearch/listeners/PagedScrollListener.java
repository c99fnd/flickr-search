package com.fredde.flickrsearch.listeners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * Handles on-demand loading for a scrollable.
 */
public abstract class PagedScrollListener implements OnScrollListener {

    private int mCurrentPage;

    private int mLoadThreshold;

    private int mPrevTotalItemCount;

    private boolean mLoading;

    /**
     * Constructor.
     *
     * @param loadThreshold Threshold for when to start loading.
     */
    public PagedScrollListener(int loadThreshold) {
        mLoadThreshold = loadThreshold;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /* Do nothing. */
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
        int remaningItems = totalItemCount - (firstVisibleItem + visibleItemCount);

        if (!mLoading && totalItemCount == 0) {
            mLoading = true;
        }

        if (totalItemCount < mPrevTotalItemCount) {
            mCurrentPage = 0;
            mPrevTotalItemCount = totalItemCount;
        }

        if (mLoading && totalItemCount > mPrevTotalItemCount) {
            mLoading = false;
            mPrevTotalItemCount = totalItemCount;
            mCurrentPage++;
        }

        if (!mLoading && remaningItems < mLoadThreshold) {
            mLoading = true;
            fetchNextPage(mCurrentPage + 1);
        }
    }

    /**
     * Called then more data needs to be loaded.
     *
     * @param page The page to load.
     */
    public abstract void fetchNextPage(int page);
}
