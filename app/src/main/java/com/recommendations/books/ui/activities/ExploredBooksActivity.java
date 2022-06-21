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
import android.util.Log;
import android.view.View;

import com.recommendations.books.R;
import com.recommendations.books.adapters.RecommendedBitcoinBooksAdapter;
import com.recommendations.books.databinding.ActivityExploredBooksBinding;
import com.recommendations.books.models.Book;
import com.recommendations.books.util.ItemOffsetDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class ExploredBooksActivity extends AppCompatActivity {

    final String TAG = "ExploredBooksActivity";

    RecommendedBitcoinBooksAdapter recommendedBitcoinBooksAdapter;

    ActivityExploredBooksBinding binding;

    String bookGenre, toolbarTitle, fireStoreId;

    LinearLayoutCompat llNoInternetPage;



    RecyclerView recyclerView;
    Context context;
    Query query;


    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_explored_books);

        binding = ActivityExploredBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = binding.getRoot().getContext();

        varView = findViewById(android.R.id.content).getRootView();

        if(getIntent()!=null)
        {
            fireStoreId = getIntent().getStringExtra("fireStoreId");
            bookGenre = getIntent().getStringExtra("BooksGenre");
            toolbarTitle = getIntent().getStringExtra("toolbarTitle");
        }


        llNoInternetPage = findViewById(R.id.no_internetPage_explored_books);

        binding.noInternetPageExploredBooks.btnRetryNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivity();
            }
        });


        if(toolbarTitle!=null)
            binding.ivToolbarTitleViewExploredBooksActivity.setText(toolbarTitle+" Books");

        binding.ibBackViewExploredBooksActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bindUi();


    }

    private void bindUi(){


        Log.v(TAG, bookGenre.toLowerCase());

        query = FirebaseFirestore.getInstance()
                .collection("AllRecommendedBooks").whereArrayContainsAny("bookGenreTags", Arrays.asList(bookGenre.trim(), bookGenre.trim()));

        recyclerView= binding.viewAllInvestmentBooksRv;


        recyclerView.setLayoutManager(new GridLayoutManager(this, getApplicationContext().getResources().getInteger(R.integer.book_grid_layout_columns)));
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(null);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.book_item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();


        recommendedBitcoinBooksAdapter = new RecommendedBitcoinBooksAdapter(options, true, context);
        recyclerView.setAdapter(recommendedBitcoinBooksAdapter);
        recommendedBitcoinBooksAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);


        if(recommendedBitcoinBooksAdapter !=null){
            recommendedBitcoinBooksAdapter.startListening();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(recommendedBitcoinBooksAdapter !=null){
            recommendedBitcoinBooksAdapter.stopListening();
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

            llNoInternetPage.setVisibility(View.VISIBLE);

            recyclerView.setVisibility(View.INVISIBLE);


            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            monitoringConnectivity = true;


        }else{
            llNoInternetPage.setVisibility(View.GONE);

            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkConnectivity();
    }
}