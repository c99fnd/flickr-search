package com.fredde.flickrsearch.fragment;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.adapters.SearchResultAdapter;
import com.fredde.flickrsearch.callbacks.SearchListCallback;
import com.fredde.flickrsearch.data.PhotoEntry;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Displays a search widget and a list of search results. The start fragment of the application.
 */
public class PhotoSearchFragment extends Fragment implements OnQueryTextListener {

    /**
     * Shared Prefs key.
     */
    private static final String SEARCH_STRING_KEY = "search_string";

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
    private SearchResultAdapter mAdapter;

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

        String search = readSearchStringFromPrefs();
        RealmResults<PhotoEntry> data = getPhotosFromDb(search);

        mAdapter = new SearchResultAdapter(getActivity().getApplicationContext(), data, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.search_list);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoEntry photo = (PhotoEntry)mAdapter.getItem(position);
                mCallback.onListItemSelected(photo.getId());
            }
        });
        listView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mAdapter = null;
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
        writeSearchStringInPrefs(query);
        mSearchView.clearFocus();
        mCallback.onSearch(query);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    /**
     * Called when the query data has been changed to notify the fragment that new data is
     * needed tp be fetched.
     *
     * @param query The query to use when fetching new data.
     */
    public void notifyQueryDataChanged(String query) {
        RealmResults<PhotoEntry> results = getPhotosFromDb(query);
        mAdapter.updateRealmResults(results);
    }

    /**
     * Clears the SearchView by setting its  OnQueryListener to null.
     */
    private void clearSearchView() {
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(null);
        }
    }

    /**
     * Retrieves data from realm.
     *
     * @param query The query to use or null if all data is supposed to be fetched.
     * @return RealmResults The result as a list.
     */
    private RealmResults<PhotoEntry> getPhotosFromDb(@Nullable String query) {
        RealmResults<PhotoEntry> res;
        if (query != null) {
            res = mRealm.where(PhotoEntry.class).contains("tags", query.toLowerCase()).findAll();
        } else {
            res = mRealm.where(PhotoEntry.class).findAll();
        }
        return res;
    }

    /**
     * Stores the given string to a pre defined key in shared prefs.
     *
     * @param string the string to store.
     */
    private void writeSearchStringInPrefs(String string) {
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SEARCH_STRING_KEY, string);
        editor.commit();
    }

    /**
     * Reads a string from a given key in shared prefs.
     *
     * @return The string read.
     */
    private String readSearchStringFromPrefs() {
        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);
        return settings.getString(SEARCH_STRING_KEY, null);
    }
}