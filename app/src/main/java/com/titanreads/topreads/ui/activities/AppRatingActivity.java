package com.titanreads.topreads.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.titanreads.topreads.R;
import com.titanreads.topreads.ui.fragments.RatingOneFragment;

public class AppRatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_rating);

        loadFragment(new RatingOneFragment(),null);
    }



    private void loadFragment(Fragment fragment, String tag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rating_fragments_container, fragment)
                .commit();
    }

}