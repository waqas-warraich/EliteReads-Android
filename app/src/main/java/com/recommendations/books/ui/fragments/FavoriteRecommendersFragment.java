package com.recommendations.books.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.recommendations.books.R;
import com.recommendations.books.adapters.FavoriteRecommendersAdapter;
import com.recommendations.books.models.BookRecommenderPerson;
import com.recommendations.books.ui.activities.LoginActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteRecommendersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteRecommendersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Set<String> favRecommendersIdsSet;
    Set <String>set = new HashSet();

    Context context;
    FavoriteRecommendersAdapter favoriteRecommendersAdapter;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userAccountId, userEmail;

    LinearLayoutCompat llNoLoginNoFavoritesContainer;
    Button btnLogin;
    public FavoriteRecommendersFragment() {
        // Required empty public constructor
    }


    RecyclerView recommendersRecyclerView;
    public static FavoriteRecommendersFragment newInstance(String param1, String param2) {
        FavoriteRecommendersFragment fragment = new FavoriteRecommendersFragment();
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

        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
        {
            readUserSharedPrefData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_recommenders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        llNoLoginNoFavoritesContainer = view.findViewById(R.id.no_login_favorite_recommenders);
        btnLogin = view.findViewById(R.id.bt_login_favorite_recommenders);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        if(currentUser!=null){


            if(recommendersRecyclerView==null)
                setBookRecommenderRecyclerView(view);

            llNoLoginNoFavoritesContainer.setVisibility(View.GONE);
        }
        else{
            llNoLoginNoFavoritesContainer.setVisibility(View.VISIBLE);
        }

    }

    private void setBookRecommenderRecyclerView(View view){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteRecommenders");

        recommendersRecyclerView= view.findViewById(R.id.favorite_recommenders_recyclerView);
        recommendersRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                false));
        recommendersRecyclerView.setHasFixedSize(true);
        recommendersRecyclerView.setItemAnimator(null);

        FirestoreRecyclerOptions<BookRecommenderPerson> options = new FirestoreRecyclerOptions.Builder<BookRecommenderPerson>()
                .setQuery(query, BookRecommenderPerson.class)
                .build();

        favoriteRecommendersAdapter = new FavoriteRecommendersAdapter(options, false, context);
        recommendersRecyclerView.setAdapter(favoriteRecommendersAdapter);

        favoriteRecommendersAdapter.startListening();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(favoriteRecommendersAdapter!=null){
            favoriteRecommendersAdapter.stopListening();

        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        //  name = sharedPreferences.getString("name", "");
        userEmail = sharedPreferences.getString("email", "");

        favRecommendersIdsSet = sharedPreferences.getStringSet("favRecommendersSet", set);



    }



}