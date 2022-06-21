package com.recommendations.books.ui.fragments;

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

import com.recommendations.books.R;
import com.recommendations.books.adapters.ExploreBooksAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreBooksFragment extends Fragment implements ExploreBooksAdapter.OnBookGenreItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;
    RecyclerView recyclerView;
    ExploreBooksAdapter exploreBooksAdapter;

    ArrayList<String> recommendersGenreList = new ArrayList<>();



    public ExploreBooksFragment() {
        // Required empty public constructor
    }


    public static ExploreBooksFragment newInstance(String param1, String param2) {
        ExploreBooksFragment fragment = new ExploreBooksFragment();
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

        return inflater.inflate(R.layout.fragment_explore_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        setQuickFiltersRecyclerView(view);



    }


    private void populateList(){
        recommendersGenreList.add("Technology");
        recommendersGenreList.add("Science");
        recommendersGenreList.add("Novels");
        recommendersGenreList.add("Nonfiction");
        recommendersGenreList.add("Fiction");
        recommendersGenreList.add("Literature");
        recommendersGenreList.add("Childrens");
        recommendersGenreList.add("Science Fiction");
        recommendersGenreList.add("Science Fiction Fantasy");
        recommendersGenreList.add("Startups");
        recommendersGenreList.add("Design");
        recommendersGenreList.add("Art");
        recommendersGenreList.add("Feminism");
        recommendersGenreList.add("Self Help");
        recommendersGenreList.add("Spirituality");
        recommendersGenreList.add("Productivity");
        recommendersGenreList.add("Business");
        recommendersGenreList.add("Leadership");
        recommendersGenreList.add("Entrepreneurship");
        recommendersGenreList.add("Money");
        recommendersGenreList.add("Cryptocurrency");
        recommendersGenreList.add("Bitcoin");
        recommendersGenreList.add("Memoir");
        recommendersGenreList.add("Autobiography");
        recommendersGenreList.add("Biography");
        recommendersGenreList.add("Investing");
        recommendersGenreList.add("Politics");
        recommendersGenreList.add("Economics");
        recommendersGenreList.add("Military");
        recommendersGenreList.add("Economics");
        recommendersGenreList.add("Health");
        recommendersGenreList.add("Medical");
        recommendersGenreList.add("History");
        recommendersGenreList.add("Philosophy");
        recommendersGenreList.add("Psychology");
        recommendersGenreList.add("Fantasy");
        recommendersGenreList.add("Classics");
        recommendersGenreList.add("Teen");
        //recommendersGenreList.add("Writing");
        recommendersGenreList.add("Sports");
        recommendersGenreList.add("Space");
        recommendersGenreList.add("Sales");
       // recommendersGenreList.add("Negotiation");
        recommendersGenreList.add("Meditations");
        recommendersGenreList.add("Mindfulness");
        recommendersGenreList.add("Marketing");
        recommendersGenreList.add("Mathematics");
        recommendersGenreList.add("Artificial Intelligence");
       // recommendersGenreList.add("Makeup");
        recommendersGenreList.add("Language");
        recommendersGenreList.add("Fitness");
        recommendersGenreList.add("Finance");
       // recommendersGenreList.add("Entertainment");
        recommendersGenreList.add("Design");
       // recommendersGenreList.add("CEO");
        recommendersGenreList.add("Writing");
        recommendersGenreList.add("War");
       // recommendersGenreList.add("Agriculture");

    }


    private void setQuickFiltersRecyclerView(View view){

        recyclerView=view.findViewById(R.id.explore_books_genre_recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(context, getResources().getInteger(R.integer.recommender_genre_grid_layout_columns)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);



        exploreBooksAdapter = new ExploreBooksAdapter(context, recommendersGenreList, this);

        recyclerView.setAdapter(exploreBooksAdapter);
    }

    @Override
    public void genreClickListener(int filterItemPosition, boolean clearList) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}