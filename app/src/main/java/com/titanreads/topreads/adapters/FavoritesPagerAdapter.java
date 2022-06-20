package com.titanreads.topreads.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.titanreads.topreads.R;
import com.titanreads.topreads.ui.fragments.FavoriteBooksFragment;
import com.titanreads.topreads.ui.fragments.FavoriteRecommendersFragment;


public class FavoritesPagerAdapter extends FragmentStateAdapter {

    final String TAG = "FavoritesPagerAdapter";

    boolean refreshed = false;
    public FavoritesPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FavoritesPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);

        if(fragment.getTag() =="f0")
        {

            FavoriteBooksFragment favoriteBooksFragment = (FavoriteBooksFragment) fragment;
            favoriteBooksFragment.setFavoriteBooksRecyclerView(favoriteBooksFragment.getView());


            Log.v(TAG, "FavoriteBooksFragment found" );

           /* if(favoritesPagerAdapter!=null)
                if(favoritesPagerAdapter.getItemCount()==2){
                    FavoriteBooksFragment favoriteBooksFragment = (FavoriteBooksFragment)getChildFragmentManager().findFragmentByTag("f"+viewPager2.getCurrentItem());
                    favoriteBooksFragment.setFavoriteBooksRecyclerView(favoriteBooksFragment.getView());
                }*/
        }



    }


    public FavoritesPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0) {
            Log.v(TAG, "FavoriteBooksFragment created");
            return new FavoriteBooksFragment();


        }else
        {
            return new FavoriteRecommendersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
