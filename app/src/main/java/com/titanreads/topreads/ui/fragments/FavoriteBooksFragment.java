package com.titanreads.topreads.ui.fragments;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.titanreads.topreads.R;
import com.titanreads.topreads.adapters.FavoriteBooksAdapter;
import com.titanreads.topreads.models.Book;
import com.titanreads.topreads.ui.activities.LoginActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteBooksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    final String TAG = "FavoriteBooksFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    boolean refreshed = false;
    public FavoriteBooksFragment() {
        // Required empty public constructor
    }


    Set<String> favBooksIdsSet;
    Set <String>set = new HashSet();

    View rootView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userAccountDocRef;

    Context context;
    String userAccountId, userEmail;
    RecyclerView booksRecyclerView;
    FavoriteBooksAdapter favoriteBooksAdapter;
    LinearLayoutCompat llNoLoginNoFavoritesContainer;
    Button btnLogin;
    public static FavoriteBooksFragment newInstance(String param1, String param2) {
        FavoriteBooksFragment fragment = new FavoriteBooksFragment();
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

        Log.v(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite_books, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        rootView = view;

        llNoLoginNoFavoritesContainer = view.findViewById(R.id.no_login_favorite_books);
        btnLogin = view.findViewById(R.id.bt_login_favorite_books);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        if(currentUser!=null){

            if(booksRecyclerView==null){
                setFavoriteBooksRecyclerView(view);
            }


            llNoLoginNoFavoritesContainer.setVisibility(View.GONE);
        }
        else{
            llNoLoginNoFavoritesContainer.setVisibility(View.VISIBLE);
        }


    }

    public void setFavoriteBooksRecyclerView(View view){

        Query query=null;

        Log.v(TAG, "setFavoriteBooksRecyclerView");



        query = FirebaseFirestore.getInstance()
        .collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteBooks");

        booksRecyclerView= view.findViewById(R.id.favorite_books_recyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL,
                false));
        booksRecyclerView.setHasFixedSize(true);
        booksRecyclerView.setItemAnimator(null);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        favoriteBooksAdapter = new FavoriteBooksAdapter(options, context);
        booksRecyclerView.setAdapter(favoriteBooksAdapter);
        favoriteBooksAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(favoriteBooksAdapter!=null){
            favoriteBooksAdapter.stopListening();
        }
    }

    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        //  name = sharedPreferences.getString("name", "");
        userEmail = sharedPreferences.getString("email", "");

          if(sharedPreferences.contains("favRecommendersSet"))
              favBooksIdsSet = sharedPreferences.getStringSet("favRecommendersSet", set);

    }





}