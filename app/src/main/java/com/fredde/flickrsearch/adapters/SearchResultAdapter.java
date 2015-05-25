package com.fredde.flickrsearch.adapters;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.data.PhotoEntry;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Adapter handling data feeding to the search result list.
 */
public class SearchResultAdapter extends RealmBaseAdapter<PhotoEntry> implements ListAdapter {

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * Constructor
     *
     * @param context         Context
     * @param realmResults    Results from the realm database.
     * @param automaticUpdate If auto update should be used.
     */
    public SearchResultAdapter(Context context, RealmResults<PhotoEntry> realmResults,
            boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.search_list_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        PhotoEntry photo = realmResults.get(position);
        Picasso.with(context).load(photo.getUrl()).into(holder.imageView);
        return convertView;
    }
}
