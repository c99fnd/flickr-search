package com.fredde.flickrsearch;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * Handles paging for a scrollable.
 */
public abstract class PagedScrollListener implements OnScrollListener {

    private static final int DEFAULT_THRESHOLD = 10;

    private int mCurrentPage;

    private int mLoadThreshold;

    private int mPrevTotalItemCount;

    private int mStartPage;

    private boolean mLoading;

    public PagedScrollListener() {
        mLoadThreshold = DEFAULT_THRESHOLD;
    }

    public PagedScrollListener(int loadThreshold) {
        mLoadThreshold = loadThreshold;
    }

    public PagedScrollListener(int loadThreshold, int startPage) {
        mLoadThreshold = loadThreshold;
        mStartPage = startPage;
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

        Log.d("FREDDE", "totalItemCount " + totalItemCount);
        Log.d("FREDDE", "remaningItems" + remaningItems);

        if (!mLoading && totalItemCount == 0) {
            mLoading = true;
        }

        if (totalItemCount < mPrevTotalItemCount) {
            mCurrentPage = mStartPage;
            mPrevTotalItemCount = totalItemCount;
        }

        if (mLoading && totalItemCount > mPrevTotalItemCount) {
            mLoading = false;
            mPrevTotalItemCount = totalItemCount;
            mCurrentPage++;
        }

        if (!mLoading && remaningItems < mLoadThreshold) {
            mLoading = true;
            onLoadMore(mCurrentPage + 1);
        }
    }

    /**
     * Called then more data needs to be loaded.
     *
     * @param page The page to load.
     */
    public abstract void onLoadMore(int page);
}
