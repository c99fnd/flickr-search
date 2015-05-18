package com.fredde.flickrsearch.adapters;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.data.FlickrPhoto;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Adapter handling data feeding to the searchlist.
 */
public class SearchResultAdapter extends RealmBaseAdapter<FlickrPhoto> implements ListAdapter {

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    ;


    /**
     * Constructor
     *
     * @param context         Context
     * @param realmResults    Results from the realm database.
     * @param automaticUpdate If auto update should be used.
     */
    public SearchResultAdapter(Context context, RealmResults<FlickrPhoto> realmResults,
            boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.search_list_item, parent, false);
            holder = new ViewHolder();
           // holder.textView = (TextView)convertView.findViewById(R.id.search_list_item_name);
            holder.imageView = (ImageView)convertView.findViewById(R.id.search_list_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        FlickrPhoto photo = realmResults.get(position);
        //holder.textView.setText(photo.getTitle());
        Picasso.with(context).load(photo.getUrl()).into(holder.imageView);
        return convertView;
    }
}
