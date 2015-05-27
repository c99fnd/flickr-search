package com.fredde.flickrsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.fredde.flickrsearch.data.PhotoEntry;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

/**
 * Activity displaying an image in fullscreen.
 */
public class FullscreenActivity extends AppCompatActivity {

    public static final String ACTION_VIEW_IMAGE = "com.fredde.VIEW_PHOTO";
    public static final String EXTRA_PHOTO_ID = "photo_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fullscreen);
        String id = getIntent().getStringExtra(EXTRA_PHOTO_ID);
        ImageView view = (ImageView) findViewById(R.id.fullscreen_view_image);



        Realm realm = Realm.getInstance(getApplicationContext());
        PhotoEntry photo = realm.where(PhotoEntry.class).equalTo("id", id).findFirst();
        Picasso.with(getApplicationContext()).load(photo.getUrl())
                .into(view);
        realm.close();
    }
}
