package com.recommendations.books.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.recommendations.books.databinding.ActivityAppUserFeedbackBinding;
import com.recommendations.books.models.UserFeedBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppUserFeedbackActivity extends AppCompatActivity {

    ActivityAppUserFeedbackBinding binding;
    String userFeedBackDescription, userEmail;

    Context context;
    UserFeedBack userFeedBackModel;


    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAppUserFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        varView = findViewById(android.R.id.content).getRootView();


        context = AppUserFeedbackActivity.this;
        binding.sendFeedbackBtnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendFeedback();

            }
        });

        binding.ibBackRatingFeedbackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.cancelBtnFeedBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void sendFeedback(){


        hideKeyboardFrom(context, binding.getRoot().getRootView());
        userEmail = binding.etEmailFeedbackActivity.getText().toString().trim();
        userFeedBackDescription = binding.etDescriptionFeedbackActivity.getText().toString().trim();


        if(!TextUtils.isEmpty(userEmail)){
            if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() ){

               // binding.tilEmailFeedbackActivity.setErrorEnabled(true);
               // binding.tilEmailFeedbackActivity.setError("Email is not valid");
                binding.etEmailFeedbackActivity.setError(null);
                binding.errTextEmailFeedbackActivity.setVisibility(View.VISIBLE);
                Toast.makeText(AppUserFeedbackActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;

            }else
            {
               // binding.tilEmailFeedbackActivity.setErrorEnabled(false);
               // binding.tilEmailFeedbackActivity.setError(null);
                binding.etEmailFeedbackActivity.setError(null);
                binding.errTextEmailFeedbackActivity.setVisibility(View.INVISIBLE);

            }
        }


        if(TextUtils.isEmpty(userFeedBackDescription)){

           // binding.tilDescriptionFeedbackActivity.setErrorEnabled(true);
           // binding.tilDescriptionFeedbackActivity.setError("Description is required");
            binding.etDescriptionFeedbackActivity.setError(null);
            binding.errTextDescriptionFeedbackActivity.setVisibility(View.VISIBLE);
            Toast.makeText(AppUserFeedbackActivity.this, "Description should no empty", Toast.LENGTH_SHORT).show();
            return;

        }else
        {
            binding.tilDescriptionFeedbackActivity.setErrorEnabled(false);
            binding.tilDescriptionFeedbackActivity.setError(null);
            binding.etDescriptionFeedbackActivity.setError(null);
            binding.errTextDescriptionFeedbackActivity.setVisibility(View.INVISIBLE);

        }

        if(!TextUtils.isEmpty(userFeedBackDescription)){

            String deviceInfo = "\n\n\n\nDevice Specs: "+"\n"+"MANUFACTURER: "+ Build.MANUFACTURER+"\n"+"MODEL: "+
                    Build.MODEL+"\n"+"BRAND: "+ Build.BRAND+"\n"+"Display: "+ Build.DISPLAY+"\n"+ "USER: "+ Build.USER+"\n"+"SDK: "+
                    Build.VERSION.SDK+"\n"+"RELEASE: "+Build.VERSION.RELEASE;

            userFeedBackModel = new UserFeedBack();

            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                    .collection("EliteReadsAndroidAppFeedback");
            String id = collectionReference.document().getId();

            if(userEmail!=null)
            userFeedBackModel.setUserEmail(userEmail);

            userFeedBackModel.setFeedBackDescription(userFeedBackDescription);
            userFeedBackModel.setFeedBackId(id);
            userFeedBackModel.setUserDeviceSpecs(deviceInfo);

            Toast.makeText(AppUserFeedbackActivity.this, "Sending ...", Toast.LENGTH_SHORT).show();

            collectionReference.document(id).set(userFeedBackModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AppUserFeedbackActivity.this, "Suggestion sent successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AppUserFeedbackActivity.this, "no internet connection error ...", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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