package com.recommendations.books.ui.activities;

import android.app.Activity;
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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.recommendations.books.MainActivity;
import com.recommendations.books.R;
import com.recommendations.books.models.Book;
import com.recommendations.books.models.BookRecommenderPerson;
import com.recommendations.books.models.User;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.Set;

public class SignUpWithEmailActivity extends AppCompatActivity{

    ImageButton ibCancel;

    TextInputEditText etEmail, etPassword;
    TextView tvSubmit, tvSignIn, tvTermsNPrivacy;

    MaterialTextView  mtvErrorLabelEmail,   mtvErrorLabelPass;
    User user;
    TextInputLayout tilEmail,  tilPassword;
    DocumentReference documentReference;
    FirebaseAuth mAuth;

    ProgressBar progressBar;
    LinearLayoutCompat llProgressBarContainer;

    final String TAG = "SignUpWithEmail";

    View varView= null;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AdView mAdView1;

    Book book;
    BookRecommenderPerson bookRecommenderPerson;

    Set<String> favRecommendersIdsSet;
    Set<String> favBooksIdsSet;
    Set <String>set = new HashSet();


    boolean isConnected = true;
    private boolean monitoringConnectivity = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_email);
        //overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        user =new User();
        // DocumentReference documentReference=db.collection("UserAccounts").document("tutorProfile");
        // awesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);

        varView = findViewById(android.R.id.content).getRootView();
        context = SignUpWithEmailActivity.this;

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            book= (Book) extra.getSerializable("BookObject");
            bookRecommenderPerson = (BookRecommenderPerson) extra.getSerializable("recommenderObject");
        }

        if(getIntent()!=null){


        }
        mAuth= FirebaseAuth.getInstance();


        MobileAds.initialize(SignUpWithEmailActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        /*AdRequest adRequest = new AdRequest.Builder().build();

        mAdView1 = findViewById(R.id.adViewSmallBannerSignUp);
        mAdView1.loadAd(adRequest);*/

        tvTermsNPrivacy = findViewById(R.id.tv_terms_and_conditions_signup_with_email);

        ibCancel = findViewById(R.id.ib_cancel_SignUpWithEmail);

        etEmail = findViewById(R.id.et_email_SignUpWithEmail);
        etPassword = findViewById(R.id.et_password_SignUpWithEmail);
        //  String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        tvSubmit = findViewById(R.id.submit_btn_SignUpWithEmail);
        tvSignIn = findViewById(R.id.signup_email_option_SignUpWithEmail);



        tilEmail= findViewById(R.id.til_email_SignUpWithEmail);
        tilPassword= findViewById(R.id.til_pass_SignUpWithEmail);
        mtvErrorLabelEmail= findViewById(R.id.err_text_email_SignUpWithEmail);
        mtvErrorLabelPass= findViewById(R.id.err_text_pass_SignUpWithEmail);

        progressBar = findViewById(R.id.baseProgressbar);
        llProgressBarContainer = findViewById(R.id.progressBarSignUp);





        String regexName = "[a-zA-Z\\s]+";
        String regexPhone = "^((\\\\+92)?(0092)?(92)?(0)?)(3)([0-9]{9})$";
        String regexEmail= "^[A-Za-z0-9+_.-]+@(.+)$";
        String regexPassword = "^.{3,}$";

        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpWithEmailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        String text = "By pressing 'SUBMIT' I declare that i have read all and I agree to the PakTutors Terms and Conditions.";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SignUpWithEmailActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.teal_logo));
                ds.setTextSize(35);
                ds.setUnderlineText(false);
            }
        };


        ss.setSpan(clickableSpan1, 81, 101, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvTermsNPrivacy.setText(ss);
        tvTermsNPrivacy.setMovementMethod(LinkMovementMethod.getInstance());

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "clicked");

                hideKeyboardFrom(context, varView);
                final String email = etEmail.getText().toString().trim();
                final String pass = etPassword.getText().toString().trim();

                //"5p-SJgA&5pNX@*#&"

                //    boolean isEmpty =emptyChecker();

                // Toast.makeText(RegisterActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()||TextUtils.isEmpty(email)){

                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError("Email is not valid");
                    etEmail.setError(null);
                    mtvErrorLabelEmail.setVisibility(View.VISIBLE);
                    return;

                }else
                {
                    tilEmail.setErrorEnabled(false);
                    tilEmail.setError(null);
                    etEmail.setError(null);
                    mtvErrorLabelEmail.setVisibility(View.INVISIBLE);

                }



                if(!pass.matches(regexPassword)||TextUtils.isEmpty(pass)){

                    tilPassword.setErrorEnabled(true);
                    tilPassword.setError("Password is not valid");
                    etPassword.setError(null);
                    mtvErrorLabelPass.setVisibility(View.VISIBLE);
                    return;

                }else
                {
                    tilPassword.setErrorEnabled(false);
                    tilPassword.setError(null);
                    etPassword.setError(null);
                    mtvErrorLabelPass.setVisibility(View.INVISIBLE);

                }


                Log.v(TAG, "Sign up starting");



                if ( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

                    final String pd = "5p-SJgA&5pNX@*#&"+pass.trim();

                    Log.v(TAG, "Sign up starting");

                    llProgressBarContainer.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email,pd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                          //  llProgressBarContainer.setVisibility(View.INVISIBLE);

                            if (task.isSuccessful()){
                                llProgressBarContainer.setVisibility(View.INVISIBLE);

                                Log.v(TAG, "Sign up success");


                                String id= email.trim();
                                documentReference = db.collection("EliteReadsAndroidUserAccounts").document(id);
                                //user.setName(name);
                                user.setEmail(email);
                                user.setPassword(pd);

                                documentReference.set(user);
                                writeUserDataSharePrefs();

                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(book!=null || bookRecommenderPerson!=null){
                                            llProgressBarContainer.setVisibility(View.VISIBLE);
                                            startFavoritesActivity();
                                        }else
                                            startMainActivity();
                                    }
                                }, 2000);

                            }else {
                                Log.v(TAG, "Sign up failed");

                                String error = task.getException().getMessage();
                                llProgressBarContainer.setVisibility(View.GONE);
                                Toast.makeText(SignUpWithEmailActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }






            }
        });


    }

    private void startMainActivity(){
       // writeUserDataSharePrefs();
        Intent intent = new Intent(SignUpWithEmailActivity.this, MainActivity.class);
        startActivity(intent);
    }

/*
    private void startFavoritesActivity(){
        //writeUserDataSharePrefs();

        Log.v(TAG, "startFavoritesActivity");
        DocumentReference favoriteDocRef= db.collection("UserAccounts").document(etEmail.getText().toString().trim()).collection("Favorites").document(book.getBookId());

        favoriteDocRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                llProgressBarContainer.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putString("userId", etEmail.getText().toString().trim());
             */
/*   Intent intent = new Intent(SignUpWithEmailActivity.this,FavoritesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);*//*

            }
        });




    }
*/


    private void startFavoritesActivity(){

        String userEmail;
        userEmail =  etEmail.getText().toString().trim();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference favoriteDocRef;

        if(book!=null){
            favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userEmail).collection("FavoriteBooks").document(book.getBookId());
            favoriteDocRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    favRecommendersIdsSet = new HashSet<>();
                    favBooksIdsSet.add(book.getBookId());

                    updateFavoritesSharedPrefs();
                    llProgressBarContainer.setVisibility(View.GONE);
                    Bundle bundle = new Bundle();

                    DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userEmail);
                    documentReference.update("favoriteBooksIds", FieldValue.arrayUnion(book.getBookId()));
                    bundle.putString("userId", userEmail);

                   /* Intent intent = new Intent(SignUpWithEmailActivity.this,FavoritesActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                    finish();
                }
            });

        }else if(bookRecommenderPerson!=null){
            favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userEmail).collection("FavoriteRecommenders").document(bookRecommenderPerson.getBookRecommenderId());
            favoriteDocRef.set(bookRecommenderPerson).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    favRecommendersIdsSet = new HashSet<>();
                    favRecommendersIdsSet.add(bookRecommenderPerson.getBookRecommenderId());
                    updateFavoritesSharedPrefs();

                    llProgressBarContainer.setVisibility(View.GONE);

                    Bundle bundle = new Bundle();
                    DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userEmail);
                    documentReference.update("favoriteRecommendersIds", FieldValue.arrayUnion(bookRecommenderPerson.getBookRecommenderId()));
                    bundle.putString("userId", userEmail);

                   /* Intent intent = new Intent(SignUpWithEmailActivity.this,FavoritesActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();*/
                }
            });
        }
    }

    private void updateFavoritesSharedPrefs(){

        SharedPreferences sharedPreferences = getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if(favBooksIdsSet!=null)
            myEdit.putStringSet("favBooksSet", favBooksIdsSet);
        else if(favRecommendersIdsSet!=null)
            myEdit.putStringSet("favRecommendersSet", favRecommendersIdsSet);
        myEdit.apply();
    }

    private void writeUserDataSharePrefs(){


        SharedPreferences sharedPreferences = getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("userAccountId", etEmail.getText().toString());
        myEdit.putString("email", etEmail.getText().toString());
        myEdit.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

     /*   Log.v(TAG, "request for tutor: "+ requestForTutor);*/
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