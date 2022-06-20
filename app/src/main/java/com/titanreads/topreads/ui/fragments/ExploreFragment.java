package com.titanreads.topreads.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.titanreads.topreads.R;
import com.titanreads.topreads.adapters.ExploreTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ExploreFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ExploreTabPagerAdapter exploreTabPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPagerAdapter(view);

    }

    private void setPagerAdapter(View view){

        tabLayout=view.findViewById(R.id.explore_tab_layout);
        viewPager2=view.findViewById(R.id.explore_view_pager2);

        exploreTabPagerAdapter = new ExploreTabPagerAdapter(this);

        viewPager2.setAdapter(exploreTabPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        if(position==0){

                            tab.setText("Recommenders");

                        }else if(position==1){
                            tab.setText("Books");

                        }


                    }
                }).attach();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}