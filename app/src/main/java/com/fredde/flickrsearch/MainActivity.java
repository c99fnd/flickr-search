package com.fredde.flickrsearch;

import com.fredde.flickrsearch.fragment.SearchFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements SearchListCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SearchFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListItemSelected() {

    }

    @Override
    public void onSearch(String query) {
        Log.d("Fredde", "Query : "+query);
    }
}
