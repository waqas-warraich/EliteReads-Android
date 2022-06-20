package com.titanreads.topreads.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.titanreads.topreads.R;
import com.titanreads.topreads.adapters.RecommendationPersonAdapter;
import com.titanreads.topreads.adapters.RecommendedBiographyBooksAdapter;
import com.titanreads.topreads.adapters.RecommendedBitcoinBooksAdapter;
import com.titanreads.topreads.adapters.RecommendedInvestingBooksAdapter;
import com.titanreads.topreads.adapters.RecommendedTechBooksAdapter;

import com.titanreads.topreads.databinding.FragmentHomeBinding;
import com.titanreads.topreads.models.Book;
import com.titanreads.topreads.models.BookRecommenderPerson;
import com.titanreads.topreads.ui.activities.ViewAllMostRecommendedBooksActivity;
import com.titanreads.topreads.ui.activities.ViewAllRecommendersActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;


    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;


    View snackBarView;

    View rootView;
    Context context;
    final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    RecommendationPersonAdapter recommendationPersonAdapter;
    RecommendedTechBooksAdapter recommendedTechBooksAdapter;
    RecommendedBiographyBooksAdapter recommendedBiographyBooksAdapter;
    RecommendedInvestingBooksAdapter recommendedInvestingBooksAdapter;
    RecommendedBitcoinBooksAdapter recommendedBitcoinBooksAdapter;

    String appVersionCode;

    int rvPosition;

    RecyclerView recommendationPersonRecyclerView, techBooksRecyclerView, biographyBooksRecyclerView, investmentBooksRecyclerView, bitcoinBooksRecyclerView;
    private Parcelable recommendationPersonRecyclerViewState, techBooksRecyclerViewState, biographyBooksRecyclerViewState, investingBooksRecyclerViewState, bitcoinBooksRecyclerViewState;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    LayoutManager layoutManager;

    String themeMode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("EliteReadsVersion");




    }


    private void getData() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

               // Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        Log.v(TAG, "onCreateView");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();

        varView = binding.getRoot();

        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

       /* if(savedInstanceState==null){
            Log.v(TAG, "savedInstanceState is null");

        }

        else{

            Log.v(TAG, "savedInstanceState not null");
            layoutManager = savedInstanceState.getParcelable(RECOMMENDER_KEY_RECYCLER_STATE);
            recommendationPersonAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
            recommendationPersonRecyclerView.setLayoutManager(layoutManager);

        }*/

        setBookRecommenderRecyclerView(root);
        setRecommendedTechBooksRecyclerView();
        setRecommendedInvestingBooksRecyclerView();
        setRecommendedBiographyBooksRecyclerView();
        setRecommendedBitcoinBooksRecyclerView();

        if(recommendationPersonRecyclerView==null){
            setBookRecommenderRecyclerView(root);

        }


        if(techBooksRecyclerView==null){
            setRecommendedTechBooksRecyclerView();

        }


        if(investmentBooksRecyclerView==null){
            setRecommendedInvestingBooksRecyclerView();

        }


        if(biographyBooksRecyclerView==null){
            setRecommendedBiographyBooksRecyclerView();

        }


        if(bitcoinBooksRecyclerView==null){
            setRecommendedBitcoinBooksRecyclerView();


        }



        initUi(root);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, "onViewCreated");

      /*  if(recommendationPersonAdapter!=null)
            recommendationPersonAdapter.startListening();

        if(recommendedTechBooksAdapter!=null)
            recommendedTechBooksAdapter.startListening();

        if(recommendedInvestingBooksAdapter!=null)
            recommendedInvestingBooksAdapter.startListening();

        if(recommendedBiographyBooksAdapter!=null)
            recommendedBiographyBooksAdapter.startListening();

        if(recommendedBitcoinBooksAdapter!=null)
            recommendedBitcoinBooksAdapter.startListening();

*/


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

       /* Log.v(TAG, "onSaveInstanceState:   "+ rvPosition);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RVState",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("position", rvPosition);
        myEdit.apply();*/
    }



    private void initUi(View view){
        Log.v(TAG, "initUi");
        binding.tvViewAllRecommendersHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllRecommendersActivity.class);
                startActivity(intent);
            }
        });


        binding.tvViewAllBitcoinBooksHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ViewAllMostRecommendedBooksActivity.class);
                intent.putExtra("BookId", "RecommendedBitcoinBooks");
                intent.putExtra("toolbarTitle", "Most Recommended Bitcoin Books");
                startActivity(intent);

            }
        });

        binding.tvViewAllTechBooksHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllMostRecommendedBooksActivity.class);
                intent.putExtra("BookId", "RecommendedTechBooks");
                intent.putExtra("toolbarTitle", "Most Recommended Tech Books");
                startActivity(intent);
            }
        });

        binding.tvViewAllBiographyBooksHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ViewAllMostRecommendedBooksActivity.class);
                intent.putExtra("BookId", "RecommendedBiographyBooks");
                intent.putExtra("toolbarTitle", "Most Recommended Biographies");
                startActivity(intent);

            }
        });

        binding.tvViewAllInvestmentBooksHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllMostRecommendedBooksActivity.class);
                intent.putExtra("BookId", "RecommendedInvestmentBooks");
                intent.putExtra("toolbarTitle", "Most Recommended Investing Books");
                startActivity(intent);


            }
        });



    }

    private void setBookRecommenderRecyclerView(View view){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("BookRecommenders");

        recommendationPersonRecyclerView= binding.bookRecommenderRecycler;

        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL,
                false);
        recommendationPersonRecyclerView.setLayoutManager(layoutManager);
        recommendationPersonRecyclerView.setHasFixedSize(true);
        recommendationPersonRecyclerView.setItemAnimator(null);


        FirestoreRecyclerOptions<BookRecommenderPerson> options = new FirestoreRecyclerOptions.Builder<BookRecommenderPerson>()
                .setQuery(query, BookRecommenderPerson.class)
                .build();

        recommendationPersonAdapter = new RecommendationPersonAdapter(options, false);
        recommendationPersonRecyclerView.setAdapter(recommendationPersonAdapter);
        recommendationPersonAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recommendationPersonAdapter.startListening();
    }


    private void setRecommendedBitcoinBooksRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("RecommendedBitcoinBooks").limit(20);

        bitcoinBooksRecyclerView= binding.bitcoinBooksRecycler;
        bitcoinBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        bitcoinBooksRecyclerView.setHasFixedSize(true);
        bitcoinBooksRecyclerView.setItemAnimator(null);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        recommendedBitcoinBooksAdapter = new RecommendedBitcoinBooksAdapter(options, false, context);
        bitcoinBooksRecyclerView.setAdapter(recommendedBitcoinBooksAdapter);
        recommendedBitcoinBooksAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recommendedBitcoinBooksAdapter.startListening();
    }


    private void setRecommendedTechBooksRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("RecommendedTechBooks").limit(20);

        techBooksRecyclerView= binding.techBooksRecycler;
        techBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        techBooksRecyclerView.setHasFixedSize(true);
        techBooksRecyclerView.setItemAnimator(null);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        recommendedTechBooksAdapter = new RecommendedTechBooksAdapter(options);
        techBooksRecyclerView.setAdapter(recommendedTechBooksAdapter);
        recommendedTechBooksAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);

        recommendedTechBooksAdapter.startListening();
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
        final ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        // Getting network Info
        // give Network Access Permission in Manifest
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        // isConnected is a boolean variable
        // here we check if network is connected or is getting connected
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (!isConnected) {

            Snackbar snack = Snackbar.make(varView, "No internet connection found", Snackbar.LENGTH_INDEFINITE);
            snackBarView = snack.getView();

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


    private void setRecommendedBiographyBooksRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("RecommendedBiographyBooks").limit(20);

        biographyBooksRecyclerView = binding.biographyBooksRecycler;
        biographyBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        biographyBooksRecyclerView.setHasFixedSize(true);
        biographyBooksRecyclerView.setItemAnimator(null);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        recommendedBiographyBooksAdapter = new RecommendedBiographyBooksAdapter(options);
        biographyBooksRecyclerView.setAdapter(recommendedBiographyBooksAdapter);
        recommendedBiographyBooksAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);

        recommendedBiographyBooksAdapter.startListening();
    }



    private void setRecommendedInvestingBooksRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("RecommendedInvestmentBooks").limit(20);

        investmentBooksRecyclerView = binding.investmentBooksRecycler;
        investmentBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        investmentBooksRecyclerView.setHasFixedSize(true);
        investmentBooksRecyclerView.setItemAnimator(null);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        recommendedInvestingBooksAdapter = new RecommendedInvestingBooksAdapter(options);
        investmentBooksRecyclerView.setAdapter(recommendedInvestingBooksAdapter);
        recommendedInvestingBooksAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recommendedInvestingBooksAdapter.startListening();
    }

    private void updateCode(String version){



        appVersionCode = version;

    }
    @Override
    public void onStart() {
        super.onStart();


       /* if(snackBarView!=null)
            snackBarView.setVisibility(View.VISIBLE);*/

        Log.v(TAG, "onStart");
      /*  if(getActivity().getResources().getString(R.string.app_version_code).equals(appVersionCode)){
            buildUpdateAlert();
        }*/

      //  buildUpdateAlert();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {



            }
        }, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        if(recommendationPersonAdapter!=null)
            recommendationPersonAdapter.stopListening();

        if(recommendedTechBooksAdapter!=null)
            recommendedTechBooksAdapter.stopListening();

        if(recommendedInvestingBooksAdapter!=null)
            recommendedInvestingBooksAdapter.stopListening();

        if(recommendedBiographyBooksAdapter!=null)
            recommendedBiographyBooksAdapter.stopListening();

        if(recommendedBitcoinBooksAdapter!=null)
            recommendedBitcoinBooksAdapter.stopListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");


        if(snackBarView!=null)
            snackBarView.setVisibility(View.GONE);

        getActivity().getResources().getString(R.string.app_version_code);
    }

    @Override
    public void onResume() {
        super.onResume();




       /* if(getActivity().getResources().getString(R.string.app_version_code).contains(appVersionCode)){

            Log.v(TAG, appVersionCode);

        }else{
            //buildUpdateAlert();
        }*/

        Log.v(TAG, "onResume");
        checkConnectivity();
    }

    private void readUserSettingsSharedPrefData(){


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guestUserData", MODE_PRIVATE);

        if(sharedPreferences.contains("ThemeMode")){
            themeMode = sharedPreferences.getString("ThemeMode", "");
        }

    }






}