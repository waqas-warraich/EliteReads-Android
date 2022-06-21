package com.recommendations.books.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.recommendations.books.R;
import com.recommendations.books.adapters.FavoritesPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FavoritesFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FavoritesPagerAdapter favoritesPagerAdapter;
    final String TAG = "FavoriteBooksFragment";
    Lifecycle lifecycle;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setPagerAdapter(view);

    }

    private void setPagerAdapter(View view){

        tabLayout=view.findViewById(R.id.favorites_tab_layout);
        viewPager2=view.findViewById(R.id.favorites_view_pager2);




        favoritesPagerAdapter = new FavoritesPagerAdapter(this);
        viewPager2.setAdapter(favoritesPagerAdapter);




        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        if(position==0){
                            tab.setText("BookShelf");



                        }else if(position==1){
                            tab.setText("Recommenders");

                        }


                    }

                }).attach();


        tabLayout.setSelected(true);
        viewPager2.setCurrentItem(0, tabLayout.isSmoothScrollingEnabled());


        if(viewPager2.getCurrentItem() == 0){
            Log.v(TAG, "Favorites Books Fragment");

        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v(TAG, "onTabSelected");


              /*  new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FavoriteBooksFragment favoriteBooksFragment = (FavoriteBooksFragment)getChildFragmentManager().findFragmentByTag("f0");
                        favoriteBooksFragment.onResume();
                    }
                }, 2000);*/


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });










    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }





}