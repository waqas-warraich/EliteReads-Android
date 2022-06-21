package com.recommendations.books.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.recommendations.books.R;
import com.recommendations.books.adapters.RecommendationPersonAdapter;
import com.recommendations.books.models.BookRecommenderPerson;
import com.recommendations.books.ui.bottomsheets.RecommendersFilterBottomSheet;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ViewAllRecommendersActivity extends AppCompatActivity {

    public static final String TAG = "AllRecommendersActivity";
    ImageButton ibBack, ibFilter;

    ArrayList<String> tagsList = new ArrayList<>();
    HashMap<String, String> filterTagsHashMap = new HashMap<String, String>();

    String filterGenreTag1, filterGenreTag2, filterGenreTag3, filterGenreTag4, filterGenreTag5;

    Query query;
    RecommendationPersonAdapter recommendationPersonAdapter;
    RecyclerView recommendationPersonRecyclerView;
    Context context;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_recommenders);
        Bundle bundle = getIntent().getExtras();
        context = ViewAllRecommendersActivity.this;


        if(bundle!=null)
        if (bundle.containsKey("tagList"))
            tagsList = bundle.getStringArrayList("tagList");

       initFiltersHashSet();
       initUi();
       setBookRecommenderRecyclerView();

        varView = findViewById(android.R.id.content).getRootView();


    }

    private void initFiltersHashSet() {

        if (tagsList != null) {

            for (int i = 0; i < tagsList.size(); i++) {


                if(i==0){

                    filterGenreTag1 = tagsList.get(i);

                    filterTagsHashMap.put("genreTag"+(i+1), filterGenreTag1.trim());



                }

                else if(i==1){
                    filterGenreTag2 = tagsList.get(i);
                    filterTagsHashMap.put("genreTag"+(i+1), filterGenreTag2.trim());

                }

                else if(i==2){
                    filterGenreTag3 = tagsList.get(i);
                    filterTagsHashMap.put("genreTag"+(i+1), filterGenreTag3.trim());

                }

                else if(i==3){
                    filterGenreTag4 = tagsList.get(i);
                    filterTagsHashMap.put("genreTag"+(i+1), filterGenreTag4.trim());

                }

                else if(i==4){
                    filterGenreTag5 = tagsList.get(i);
                    filterTagsHashMap.put("genreTag"+(i+1), filterGenreTag5.trim());

                }





            }
        }

    }

    private void initUi() {

        ibBack = findViewById(R.id.ib_back_viewAllRecommendersActivity);
        ibFilter = findViewById(R.id.ib_filter_ViewAllRecommenders);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecommendersFilterBottomSheet recommendersFilterBottomSheet = new RecommendersFilterBottomSheet();
/*
                bundle = new Bundle();
                saveBundle();
                if(bundle!=null)
                    bottomFilterFee.setArguments(bundle);*/


                recommendersFilterBottomSheet.show(getSupportFragmentManager(), "recommendersFilterBottomSheet");
            }
        });

    }

    private void setBookRecommenderRecyclerView() {







        recommendationPersonRecyclerView = findViewById(R.id.all_recommender_recyclerView);
        recommendationPersonRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.explore_recommenders_grid_layout_columns)));
        recommendationPersonRecyclerView.setHasFixedSize(true);
        recommendationPersonRecyclerView.setItemAnimator(null);


        query = setQuery();
        FirestoreRecyclerOptions<BookRecommenderPerson> options = new FirestoreRecyclerOptions.Builder<BookRecommenderPerson>()
                .setQuery(query, BookRecommenderPerson.class)
                .build();


        recommendationPersonAdapter = new RecommendationPersonAdapter(options, true);
        recommendationPersonRecyclerView.setAdapter(recommendationPersonAdapter);


    }


    private Query setQuery(){

        Log.v(TAG, filterTagsHashMap.toString() );

        if(filterTagsHashMap.size()==1) {



            String filter1Key = null, filter1Value = null;

            Iterator myVeryOwnIterator = filterTagsHashMap.keySet().iterator();
            for (int i = 0; i < filterTagsHashMap.size() && myVeryOwnIterator.hasNext(); i++) {
                if (i == 0) {

                    filter1Key = (String) myVeryOwnIterator.next();
                    filter1Value = (String) filterTagsHashMap.get(filter1Key);

                }
            }

          //  Log.v(TAG, "filter1Key: "+ filter1Key +" filter1Value : "+filter1Value.toLowerCase());

            query = FirebaseFirestore.getInstance().collection("AllBookRecommenders")
                    .whereEqualTo(filter1Key, filter1Value.toLowerCase());


        }else if(filterTagsHashMap.size()==2) {



            String filter1Key =null, filter1Value=null;
            String filter2Key =null, filter2Value=null;

            Iterator myVeryOwnIterator = filterTagsHashMap.keySet().iterator();
            for(int i = 0; i< filterTagsHashMap.size()&&myVeryOwnIterator.hasNext(); i++) {

                switch (i){

                    case 0:
                        filter1Key=(String)myVeryOwnIterator.next();
                        filter1Value=(String) filterTagsHashMap.get(filter1Key);
                        break;
                    case 1:
                        filter2Key=(String)myVeryOwnIterator.next();
                        filter2Value=(String) filterTagsHashMap.get(filter2Key);
                        break;
                    default:
                        break;

                }

            }

            query = FirebaseFirestore.getInstance().collection("AllBookRecommenders")
                    .whereEqualTo(filter1Key, filter1Value)
                    .whereEqualTo(filter2Key, filter2Value);


        }else if(filterTagsHashMap.size()==3) {


            String filter1Key =null, filter1Value=null;
            String filter2Key =null, filter2Value=null;
            String filter3Key =null, filter3Value=null;

            Iterator myVeryOwnIterator = filterTagsHashMap.keySet().iterator();
            for(int i = 0; i< filterTagsHashMap.size()&&myVeryOwnIterator.hasNext(); i++) {

                switch (i){

                    case 0:
                        filter1Key=(String)myVeryOwnIterator.next();
                        filter1Value=(String) filterTagsHashMap.get(filter1Key);
                        break;
                    case 1:
                        filter2Key=(String)myVeryOwnIterator.next();
                        filter2Value=(String) filterTagsHashMap.get(filter2Key);
                        break;
                    case 2:
                        filter3Key=(String)myVeryOwnIterator.next();
                        filter3Value=(String) filterTagsHashMap.get(filter3Key);
                        break;
                    default:
                        break;

                }

            }

            query = FirebaseFirestore.getInstance().collection("AllBookRecommenders")
                    .whereEqualTo(filter1Key, filter1Value)
                    .whereEqualTo(filter2Key, filter2Value)
                    .whereEqualTo(filter3Key, filter3Value);

        }else if(filterTagsHashMap.size()==4) {


            String filter1Key =null, filter1Value=null;
            String filter2Key =null, filter2Value=null;
            String filter3Key =null, filter3Value=null;
            String filter4Key =null, filter4Value=null;

            Iterator myVeryOwnIterator = filterTagsHashMap.keySet().iterator();
            for(int i = 0; i< filterTagsHashMap.size()&&myVeryOwnIterator.hasNext(); i++) {

                switch (i){

                    case 0:
                        filter1Key=(String)myVeryOwnIterator.next();
                        filter1Value=(String) filterTagsHashMap.get(filter1Key);
                        break;
                    case 1:
                        filter2Key=(String)myVeryOwnIterator.next();
                        filter2Value=(String) filterTagsHashMap.get(filter2Key);
                        break;
                    case 2:
                        filter3Key=(String)myVeryOwnIterator.next();
                        filter3Value=(String) filterTagsHashMap.get(filter3Key);
                        break;
                    case 3:
                        filter4Key=(String)myVeryOwnIterator.next();
                        filter4Value=(String) filterTagsHashMap.get(filter4Key);
                        break;
                    default:
                        break;

                }

            }

            query = FirebaseFirestore.getInstance().collection("AllBookRecommenders")
                    .whereEqualTo(filter1Key, filter1Value)
                    .whereEqualTo(filter2Key, filter2Value)
                    .whereEqualTo(filter3Key, filter3Value)
                    .whereEqualTo(filter4Key, filter4Value);


        }else if(filterTagsHashMap.size()==5) {


            String filter1Key =null, filter1Value=null;
            String filter2Key =null, filter2Value=null;
            String filter3Key =null, filter3Value=null;
            String filter4Key =null, filter4Value=null;
            String filter5Key =null, filter5Value=null;

            Iterator myVeryOwnIterator = filterTagsHashMap.keySet().iterator();
            for(int i = 0; i< filterTagsHashMap.size()&&myVeryOwnIterator.hasNext(); i++) {

                switch (i){

                    case 0:
                        filter1Key=(String)myVeryOwnIterator.next();
                        filter1Value=(String) filterTagsHashMap.get(filter1Key);
                        break;
                    case 1:
                        filter2Key=(String)myVeryOwnIterator.next();
                        filter2Value=(String) filterTagsHashMap.get(filter2Key);
                        break;
                    case 2:
                        filter3Key=(String)myVeryOwnIterator.next();
                        filter3Value=(String) filterTagsHashMap.get(filter3Key);
                        break;
                    case 3:
                        filter4Key=(String)myVeryOwnIterator.next();
                        filter4Value=(String) filterTagsHashMap.get(filter4Key);
                        break;
                    case 4:
                        filter5Key=(String)myVeryOwnIterator.next();
                        filter5Value=(String) filterTagsHashMap.get(filter5Key);
                        break;
                    default:
                        break;

                }

            }

            query = FirebaseFirestore.getInstance().collection("AllBookRecommenders")
                    .whereEqualTo(filter1Key, filter1Value)
                    .whereEqualTo(filter2Key, filter2Value)
                    .whereEqualTo(filter3Key, filter3Value)
                    .whereEqualTo(filter4Key, filter4Value)
                    .whereEqualTo(filter5Key, filter5Value);


        }else{
            query = FirebaseFirestore.getInstance().collection("AllBookRecommenders");

        }


        return query;


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (recommendationPersonAdapter != null)
            recommendationPersonAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (recommendationPersonAdapter != null)
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