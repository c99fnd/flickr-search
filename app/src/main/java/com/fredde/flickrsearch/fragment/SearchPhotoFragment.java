package com.fredde.flickrsearch.fragment;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.adapters.SearchResultAdapter;
import com.fredde.flickrsearch.callbacks.SearchListCallback;
import com.fredde.flickrsearch.data.FlickrPhoto;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Displays a search widget and a list of search results. The start fragment of the application.
 */
public class SearchPhotoFragment extends Fragment implements OnQueryTextListener {

    /**
     * Callback used to communicate with MainActivity.
     */
    private SearchListCallback mCallback;

    /**
     * The action bar SearchView.
     */
    private SearchView mSearchView;

    /**
     * The list adapter feeding the list with search results.
     */
    private ListAdapter mAdapter;

    /**
     * Realm instance.
     */
    private Realm mRealm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (SearchListCallback)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement SearchListCallback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRealm = Realm.getInstance(getActivity().getApplicationContext());

        RealmResults<FlickrPhoto> realmResults = mRealm.where(FlickrPhoto.class).findAll();

        mAdapter = new SearchResultAdapter(getActivity().getApplicationContext(), realmResults,
                true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.search_list);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlickrPhoto photo = (FlickrPhoto)mAdapter.getItem(position);
                mCallback.onListItemSelected(photo.getId());
            }
        });
        listView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_view_menu, menu);

        /* Clear the search view if its already been set up. */
        clearSearchView();

        /* Find and setup the SearchView. */
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView)item.getActionView();
        mSearchView.setQueryHint(getResources().getString(R.string.action_search));
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        mCallback.onSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * Clears the SearchView by setting its  OnQueryListener to null.
     */
    private void clearSearchView() {
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(null);
        }
    }
}
