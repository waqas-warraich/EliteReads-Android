package com.recommendations.books.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.recommendations.books.R;
import com.recommendations.books.adapters.RecommendationPersonAdapter;
import com.recommendations.books.models.BookRecommenderPerson;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExploredRecommendersActivity extends AppCompatActivity {

    ImageButton ibBack, ibShare;
    TextView tvToolbarTitle;

    String fireStoreId, recommenderGenre, toolbarTitle;
    RecyclerView recommendationPersonRecyclerView;
    RecommendationPersonAdapter recommendationPersonAdapter;


    LinearLayoutCompat llNoInternetPage;
    boolean isConnected = true;
    Button buttonRetry;
    private boolean monitoringConnectivity = false;
    View varView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explored_recommenders);

        if(getIntent()!=null){
            fireStoreId = getIntent().getStringExtra("fireStoreId");
            recommenderGenre = getIntent().getStringExtra("recommendersGenre");
            toolbarTitle = getIntent().getStringExtra("toolbarTitle");
        }

        llNoInternetPage = findViewById(R.id.no_internetPage_explored_recommenders);
        varView = findViewById(android.R.id.content).getRootView();

        setBookRecommenderRecyclerView();

        initUi();



    }

    private void initUi(){

        ibBack = findViewById(R.id.ib_back_ExploredRecommendersActivity);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_Title_ExploredRecommendersActivity);

        buttonRetry = findViewById(R.id.btn_retry_no_internet);

        if(recommenderGenre!=null)
            tvToolbarTitle.setText(recommenderGenre+"s");

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivity();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setBookRecommenderRecyclerView() {
        Query query=null;

        recommendationPersonRecyclerView = findViewById(R.id.allExploredRecommendersActivity_recyclerView);
        recommendationPersonRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.explore_recommenders_grid_layout_columns)));
        recommendationPersonRecyclerView.setHasFixedSize(true);
        recommendationPersonRecyclerView.setItemAnimator(null);

       // query = FirebaseFirestore.getInstance().collection("AllBookRecommenders").whereEqualTo("recommenderGenreTagsList",recommenderGenre.toLowerCase());

        query = FirebaseFirestore.getInstance().collection("AllBookRecommenders").whereEqualTo("genreTag1",recommenderGenre.toLowerCase());

        FirestoreRecyclerOptions<BookRecommenderPerson> options = new FirestoreRecyclerOptions.Builder<BookRecommenderPerson>()
                .setQuery(query, BookRecommenderPerson.class)
                .build();


        recommendationPersonAdapter = new RecommendationPersonAdapter(options, true);
        recommendationPersonRecyclerView.setAdapter(recommendationPersonAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();

        if(recommendationPersonAdapter!=null)
            recommendationPersonAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(recommendationPersonAdapter!=null)
            recommendationPersonAdapter.stopListening();
    }


    private ConnectivityManager.NetworkCallback connectivityCallback
            = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            isConnected = true;
            // LogUtility.LOGD(TAG, "INTERNET CONNECTED");
        }

        @Override
        public void onLost(Network network) {
            isConnected = false;
            // LogUtility.LOGD(TAG, "INTERNET LOST");
        }
    };

    private void checkConnectivity() {
        // here we are getting the connectivity service from connectivity manager
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        // Getting network Info
        // give Network Access Permission in Manifest
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        // isConnected is a boolean variable
        // here we check if network is connected or is getting connected
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (!isConnected) {

            llNoInternetPage.setVisibility(View.VISIBLE);

            recommendationPersonRecyclerView.setVisibility(View.INVISIBLE);


            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            monitoringConnectivity = true;
        }else{

            llNoInternetPage.setVisibility(View.GONE);
            recommendationPersonRecyclerView.setVisibility(View.VISIBLE);
        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        checkConnectivity();
    }
}