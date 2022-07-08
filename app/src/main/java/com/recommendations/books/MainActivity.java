package com.recommendations.books;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    // private ActivityMainBinding binding;

    final String TAG = "MainActivity";

    String themeMode;

    String appVersionCode;

    ImageButton ibCancelUpdate;
    TextView tvDownloadUpdate;

    FirebaseDatabase firebaseDatabase;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference appVersionDbRef,appApprovalStatusDbRef;
    View snackBarView;


    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = MainActivity.this.getResources().getDrawable(R.drawable.shape_main_gradient);
        getWindow().setBackgroundDrawable(background);*/

        varView = findViewById(android.R.id.content).getRootView();

        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get
        // reference for our database.
        appVersionDbRef = firebaseDatabase.getReference("BookRecVersion");
        appApprovalStatusDbRef = firebaseDatabase.getReference("AppApprovalStatus");

        buildUpdateAlert();
        getData();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);


        readUserSettingsSharedPrefData();

        if(themeMode!=null){

            if(themeMode.contains("Dark")){

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }else if(themeMode.contains("Light")){

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

        }

    }



    private void readUserSettingsSharedPrefData(){


        SharedPreferences sharedPreferences = getSharedPreferences("guestUserData", MODE_PRIVATE);

        if(sharedPreferences.contains("ThemeMode")){
            themeMode = sharedPreferences.getString("ThemeMode", "");
        }

    }


    private void getData() {

        // calling add value event listener method
        // for getting the values from database.
        appVersionDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                appVersionCode = snapshot.getValue(String.class);

                if(!appVersionCode.contains(getResources().getString(R.string.app_version_code)) &&(appVersionCode.contains("approved"))){
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            alertDialog.show();
                        }
                    }, 2000);

                }else{




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void buildUpdateAlert(){


        Log.v(TAG, "buildUpdateAlert");

        if (dialogBuilder == null) {
            dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        }



        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_app_small, null);
        dialogBuilder.setView(dialogView);

        ibCancelUpdate = dialogView.findViewById(R.id.ib_cancel_update_dialog);
        tvDownloadUpdate = dialogView.findViewById(R.id.tv_download_update);


        if(alertDialog==null)
        {
            alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
        }
        // alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_round_edge_dialog));


        tvDownloadUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "builtvDownloadUpdate onClick");

                //final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.recommendations.books")));
                    dismiss(alertDialog);
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.recommendations.books")));
                }


                //dismiss(alertDialog);




            }


        });



        ibCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });


    }

    private void dismiss(AlertDialog alertDialog){
        alertDialog.dismiss();
        alertDialog.cancel();
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
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();

       // checkConnectivity();
    }
}