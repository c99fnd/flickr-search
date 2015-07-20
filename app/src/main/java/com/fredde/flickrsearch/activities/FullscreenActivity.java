package com.fredde.flickrsearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.fredde.flickrsearch.R;
import com.fredde.flickrsearch.models.PhotoEntry;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

/**
 * Activity displaying an image in fullscreen_view.
 */
public class FullscreenActivity extends AppCompatActivity {

    public static final String ACTION_VIEW_IMAGE = "com.fredde.VIEW_PHOTO";
    public static final String EXTRA_PHOTO_ID = "photo_id";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        String id = getIntent().getStringExtra(EXTRA_PHOTO_ID);
        ImageView view = (ImageView) findViewById(R.id.fullscreen_view_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportActionBar().isShowing()) {
                    hideToolbar();
                } else {
                    showToolbar();
                }
            }
        });

        Realm realm = Realm.getInstance(getApplicationContext());
        PhotoEntry photo = realm.where(PhotoEntry.class).equalTo("id", id).findFirst();
        Picasso.with(getApplicationContext()).load(photo.getUrl())
                .into(view);
        getSupportActionBar().setTitle(photo.getTitle());
        realm.close();
    }

    /**
     * Animated the alpha of the toolbar from 1 to 0 and then sets the supportActionbar to hidden.
     */
    private void hideToolbar() {
        mToolbar.animate().alpha(0).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().hide();
            }
        });
    }

    /**
     * Animated the alpha of the toolbar from 0 to 1. Sets it to showing.
     */
    private void showToolbar() {
        mToolbar.animate().alpha(1).setDuration(300).withStartAction(new Runnable() {
            @Override
            public void run() {
                mToolbar.setAlpha(0);
                getSupportActionBar().show();
            }
        });
    }
}
