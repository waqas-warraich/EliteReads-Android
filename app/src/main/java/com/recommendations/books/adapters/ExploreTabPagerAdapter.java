package com.recommendations.books.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.recommendations.books.ui.fragments.ExploreBooksFragment;
import com.recommendations.books.ui.fragments.ExploreRecommendersFragment;


public class ExploreTabPagerAdapter extends FragmentStateAdapter {
    public ExploreTabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ExploreTabPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ExploreTabPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0) {
            return new ExploreRecommendersFragment();
        }else
        {
            return new ExploreBooksFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
