package com.titanreads.topreads.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titanreads.topreads.R;
import com.titanreads.topreads.adapters.ExploreRecommendersAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreRecommendersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreRecommendersFragment extends Fragment implements ExploreRecommendersAdapter.OnRecommenderGenreItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    RecyclerView recyclerView;
    ExploreRecommendersAdapter exploreRecommendersAdapter;

    ArrayList <String> recommendersGenreList = new ArrayList<>();

    public ExploreRecommendersFragment() {
        // Required empty public constructor
    }

    public static ExploreRecommendersFragment newInstance(String param1, String param2) {
        ExploreRecommendersFragment fragment = new ExploreRecommendersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        populateList();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getContext();
        return inflater.inflate(R.layout.fragment_explore_recommenders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setQuickFiltersRecyclerView(view);
    }

    private void populateList(){

        recommendersGenreList.add("Author");
        recommendersGenreList.add("Billionaire");
        recommendersGenreList.add("Investor");
        recommendersGenreList.add("Entrepreneur");
        recommendersGenreList.add("Executive");
        recommendersGenreList.add("Engineer");
        recommendersGenreList.add("Filmmaker");
        recommendersGenreList.add("Founder");
        recommendersGenreList.add("Businessperson");
        recommendersGenreList.add("Businesswoman");
       // recommendersGenreList.add("Politician");
        recommendersGenreList.add("Model");
        recommendersGenreList.add("Actor");
        recommendersGenreList.add("Athlete");
       // recommendersGenreList.add("Musician");
        recommendersGenreList.add("Philanthropist");
        //recommendersGenreList.add("Financier");
        //recommendersGenreList.add("Producer");
        recommendersGenreList.add("Scientist");
        //recommendersGenreList.add("Comedian");
        recommendersGenreList.add("Writer");
        recommendersGenreList.add("Youtuber");
        recommendersGenreList.add("Banker");
        recommendersGenreList.add("Industrialist");
        recommendersGenreList.add("Motivational Speaker");
        recommendersGenreList.add("Marketer");
        recommendersGenreList.add("Venture Capitalist");
        recommendersGenreList.add("Doctor");

    }

    private void setQuickFiltersRecyclerView(View view){

        recyclerView=view.findViewById(R.id.explore_recommenders_genre_recyclerView);
        //recyclerView.setLayoutManager(new GridLayoutManager(context, getResources().getInteger(R.integer.explore_recommenders_grid_layout_columns)));
        recyclerView.setLayoutManager(new GridLayoutManager(context, getResources().getInteger(R.integer.recommender_genre_grid_layout_columns)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);


        exploreRecommendersAdapter = new ExploreRecommendersAdapter(context, recommendersGenreList, this);

        recyclerView.setAdapter(exploreRecommendersAdapter);
    }

    @Override
    public void genreClickListener(int filterItemPosition, boolean clearList) {

    }
}