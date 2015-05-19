package com.fredde.flickrsearch.fragment;

import com.fredde.flickrsearch.utils.FlickrUrlBuilder;
import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.data.FlickrPhoto;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.realm.Realm;

/**
 * Displays a photo in fullscreen.
 */
public class FullscreenPhotoFragment extends Fragment {

    public static final String ARG_ITEM = "photo_id" ;

    /**
     * Constructor.
     */
    public FullscreenPhotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fullscreen, container, false);
        String id = getArguments().getString(ARG_ITEM);
        ImageView imageView = (ImageView)rootView.findViewById(R.id.fullscreen_view_image);
        setFullscreenImage(imageView, id);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return rootView;
    }

    public void setFullscreenImage(ImageView fullscreenImage, String id) {
        Realm realm = Realm.getInstance(getActivity().getApplicationContext());
        FlickrPhoto photo = realm.where(FlickrPhoto.class).equalTo("id",id)
                .findFirst();

        Picasso.with(getActivity().getApplicationContext()).load(FlickrUrlBuilder.buildUrl(photo)).into
                (fullscreenImage);

        realm.close();
    }
}
