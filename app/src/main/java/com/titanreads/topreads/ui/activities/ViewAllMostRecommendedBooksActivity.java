package com.titanreads.topreads.ui.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.titanreads.topreads.R;
import com.titanreads.topreads.adapters.RecommendedBiographyBooksAdapter;
import com.titanreads.topreads.adapters.RecommendedBitcoinBooksAdapter;


import com.titanreads.topreads.adapters.RecommendedInvestingBooksAdapter;
import com.titanreads.topreads.adapters.RecommendedTechBooksAdapter;


import com.titanreads.topreads.databinding.ActivityViewAllMostRecommendedBooksBinding;
import com.titanreads.topreads.models.Book;
import com.titanreads.topreads.util.ItemOffsetDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ViewAllMostRecommendedBooksActivity extends AppCompatActivity {

    final String TAG = "ViewAllBitcoinBooksActi";
    RecyclerView recyclerView;
    Query query;
    private ActivityViewAllMostRecommendedBooksBinding binding;

    private String bookFireStoreId, bookGenre, toolbarTitle;
    Context context;
    int spanCount = 1; // 3 columns
    int spacing = 50; // 50px
    boolean includeEdge = true;


    RecommendedBitcoinBooksAdapter recommendedBitcoinBooksAdapter;
    RecommendedBiographyBooksAdapter recommendedBiographyBooksAdapter;
    RecommendedInvestingBooksAdapter recommendedInvestingBooksAdapter;
    RecommendedTechBooksAdapter recommendedTechBooksAdapter;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllMostRecommendedBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ViewAllMostRecommendedBooksActivity.this;

        if(getIntent()!=null)
        {
            bookFireStoreId = getIntent().getStringExtra("BookId");
            bookGenre = getIntent().getStringExtra("BookGenre");
            toolbarTitle = getIntent().getStringExtra("toolbarTitle");



        }

        varView = findViewById(android.R.id.content).getRootView();


        initUi();

        if(toolbarTitle!=null)
            binding.ivToolbarTitleMostRecommendedBooks.setText(toolbarTitle);
        binding.ibBackViewAllBooksActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void initUi(){

        query = FirebaseFirestore.getInstance()
                .collection(bookFireStoreId.trim());
        recyclerView= binding.viewAllInvestmentBooksRv;


        recyclerView.setLayoutManager(new GridLayoutManager(this, getApplicationContext().getResources().getInteger(R.integer.book_grid_layout_columns)));
        recyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.book_item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        if(bookFireStoreId.contains("Bitcoin"))
        {
            recommendedBitcoinBooksAdapter = new RecommendedBitcoinBooksAdapter(options, true, context);
            recyclerView.setAdapter(recommendedBitcoinBooksAdapter);

        }else if(bookFireStoreId.contains("Tech")){
            recommendedTechBooksAdapter = new RecommendedTechBooksAdapter(options);
            recyclerView.setAdapter(recommendedTechBooksAdapter);

        }else if(bookFireStoreId.contains("Biography")){
            recommendedBiographyBooksAdapter = new RecommendedBiographyBooksAdapter(options);
            recyclerView.setAdapter(recommendedBiographyBooksAdapter);

        }else if(bookFireStoreId.contains("Investment")){
            recommendedInvestingBooksAdapter = new RecommendedInvestingBooksAdapter(options);
            recyclerView.setAdapter(recommendedInvestingBooksAdapter);

        }


    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.v(TAG, "book id:  "+ bookFireStoreId);


        if(recommendedBitcoinBooksAdapter !=null){
            recommendedBitcoinBooksAdapter.startListening();
        }


        if(recommendedTechBooksAdapter!=null){
            recommendedTechBooksAdapter.startListening();
        }

        if(recommendedBiographyBooksAdapter!=null){
            recommendedBiographyBooksAdapter.startListening();
        }

        if(recommendedInvestingBooksAdapter!=null){
            recommendedInvestingBooksAdapter.startListening();
        }



    }

    @Override
    protected void onStop() {
        super.onStop();


        if(recommendedBitcoinBooksAdapter !=null){
            recommendedBitcoinBooksAdapter.stopListening();
        }


        if(recommendedTechBooksAdapter!=null){
            recommendedTechBooksAdapter.stopListening();
        }

        if(recommendedBiographyBooksAdapter!=null){
            recommendedBiographyBooksAdapter.stopListening();
        }

        if(recommendedInvestingBooksAdapter!=null){
            recommendedInvestingBooksAdapter.stopListening();
        }


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

            Snackbar snack = Snackbar.make(varView, "No internet connection found", Snackbar.LENGTH_INDEFINITE);
            View snackBarView = snack.getView();





            TextView textView = (TextView)snackBarView.findViewById(com.google.android.material.R.id.snackbar_action);

            /*textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_share_24, 0, 0, 0);
            textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen._12sdp));
            */
            snack.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkConnectivity();

                        }
                    }).show();

            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            monitoringConnectivity = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnectivity();
    }
}