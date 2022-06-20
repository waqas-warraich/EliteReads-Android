package com.titanreads.topreads.ui.activities;

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
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.titanreads.topreads.MainActivity;
import com.titanreads.topreads.R;
import com.titanreads.topreads.models.Book;
import com.titanreads.topreads.models.BookRecommenderPerson;
import com.titanreads.topreads.models.User;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    final String TAG = "LoginActivity";
    ImageButton ibBack;
    TextInputEditText etEmail, etPassword;

    Set<String> favRecommendersIdsSet;
    Set<String> favBooksIdsSet = new HashSet();
    Set <String>set = new HashSet();

    ArrayList<String> favBooksIdsList ;
    ArrayList<String> favRecommendersIdsList;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    LinearLayoutCompat llProgressBarContainer;
    private final static int RC_SIGN_IN = 123;
    TextInputLayout tilEmail, tilPassword;
    MaterialTextView mtvErrorLabelEmail, mtvErrorLabelPass;


    View varView= null;
    Context context;
    private GoogleSignInClient mGoogleSignInClient;

    Book book;
    BookRecommenderPerson bookRecommenderPerson;
    User user;
    String bookId;

    TextView tvSubmitBtn, tvSignUpBtn, tvForgetPassword;
    LinearLayout llContinueWithGoogle;
    String userName, userImageUrl, userEmail;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;

    private AdView mAdView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);


        MobileAds.initialize(LoginActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null){
             bookId = extra.getString("bookId");
             book= (Book) extra.getSerializable("BookObject");
             bookRecommenderPerson = (BookRecommenderPerson) extra.getSerializable("recommenderObject");


        }

    /*    AdRequest adRequest = new AdRequest.Builder().build();

        mAdView1 = findViewById(R.id.adViewMediumRecBannerLogin);*/
        // mAdView1.loadAd(adRequest);

        varView = findViewById(android.R.id.content).getRootView();
        // varView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.white));

        if(getIntent()!=null){
          /*  tutorRegistrationRequest = getIntent().getBooleanExtra("TutorRegistration", false);
            requestForTutor = getIntent().getBooleanExtra("requestForTutor", false);*/
        }


        context = LoginActivity.this;
        ibBack = findViewById(R.id.ib_back_LoginActivity);

        String regexPassword = "^.{3,}$";
        etEmail = findViewById(R.id.et_email_LoginActivity);
        etPassword = findViewById(R.id.et_password_LoginActivity);

        tvSubmitBtn = findViewById(R.id.submit_btn_LoginActivity);
        tvSignUpBtn = findViewById(R.id.signup_email_option_LoginActivity);
        tvForgetPassword= findViewById(R.id.forgot_pass_tv_LoginActivity);

       // llContinueWithFaceBook = findViewById(R.id.ll_continue_with_google_btn_login);
        llContinueWithGoogle = findViewById(R.id.ll_continue_with_google_btn_login);

        tilEmail = findViewById(R.id.til_email_LoginActivity);
        tilPassword= findViewById(R.id.til_pass_LoginActivity);
        mtvErrorLabelEmail = findViewById(R.id.err_text_email_LoginActivity);
        mtvErrorLabelPass = findViewById(R.id.err_text_pass_LoginActivity);


        createRequest();
        llContinueWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        progressBar = findViewById(R.id.baseProgressbar);
        llProgressBarContainer = findViewById(R.id.progressBarLogin);

        mAuth= FirebaseAuth.getInstance();

/*
        llContinueWithFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "clicked");

            }
        });*/


        tvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboardFrom(context, varView);



                String pass = etPassword.getText().toString().trim();

                String email = etEmail.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()||TextUtils.isEmpty(email)){

                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError("Email is not valid");
                    etEmail.setError(null);
                    mtvErrorLabelEmail.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Email address is not correct", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, "Enter a valid password", Toast.LENGTH_SHORT).show();
                    return;

                }else
                {
                    tilPassword.setErrorEnabled(false);
                    tilPassword.setError(null);
                    etPassword.setError(null);

                    pass= "5p-SJgA&5pNX@*#&"+pass.trim();
                    mtvErrorLabelPass.setVisibility(View.INVISIBLE);

                }

                if ( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){


                    llProgressBarContainer.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                userEmail = etEmail.getText().toString().trim();
                                getUserData(userEmail);
                               // updateUserSharePrefsMultiple(  );
                                Log.v(TAG, "you login successfully");
                                llProgressBarContainer.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "You login Successfully", Toast.LENGTH_SHORT).show();

                                if(book!=null || bookRecommenderPerson!=null){
                                    llProgressBarContainer.setVisibility(View.VISIBLE);
                                    startFavoritesActivity(false, etEmail.getText().toString().trim());
                                }else{
                                    startMainActivity();
                                }

                            }
                            else{
                                llProgressBarContainer.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Enter correct email or password ", Toast.LENGTH_SHORT).show();



                            }
                        }
                    });

                }
            }
        });



        tvSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BookObject",book);
                intent.putExtra("recommenderObject", bookRecommenderPerson);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void createRequest() {

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("760514835810-oot7ljqsl80b0kod4sme0831h84ivfvk.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }




    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        llProgressBarContainer.setVisibility(View.VISIBLE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.v(TAG, "success");


                String email = account.getEmail();

                userEmail = account.getEmail();
                userName = account.getDisplayName();
                userImageUrl = account.getPhotoUrl().toString();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  firebaseAuthWithGoogle(account.getIdToken(), account.getEmail());
                        firebaseAuthWithGoogle(account.getIdToken(), account.getEmail() );
                      //  checkTutorRegistration(account.getEmail(), true, account.getIdToken());

                        llProgressBarContainer.setVisibility(View.INVISIBLE);
                         Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, 1000);



            } catch (ApiException e) {
                llProgressBarContainer.setVisibility(View.GONE);
            }
        }
    }







    private void getUserData(String email){


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                DocumentReference documentReference =  FirebaseFirestore.getInstance()
                        .collection("EliteReadsAndroidUserAccounts").document(email);
                Source source2 = Source.SERVER;


                documentReference.get(source2).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Document found in the offline cache
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){


                                user =  document.toObject(User.class);


                                if(user!=null){

                                    favBooksIdsList = user.getFavoriteBooksIds();
                                    favRecommendersIdsList = user.getFavoriteRecommendersIds();

                                    if(favBooksIdsList!=null){
                                        favBooksIdsSet.addAll(favBooksIdsList);
                                    }

                                    if(favRecommendersIdsSet!=null){
                                        favRecommendersIdsSet.addAll(favRecommendersIdsList);
                                    }
                                   /* userName = user.getName();
                                    userImageUrl = user.getUserImageUrl();*/

                                    updateFavoritesSharedPrefs();
                                }


                            }


                        }else{
                            Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }, 1000);


    }


    private void firebaseAuthWithGoogle(String idToken, String email) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {


                        updateUserSharePrefsMultiple();

                        getUserData(email);

                        if(book!=null || bookRecommenderPerson!=null){
                            llProgressBarContainer.setVisibility(View.VISIBLE);
                            startFavoritesActivity(true, email);
                        }
                        else
                            startMainActivity();
                    }
                });
    }




    private void startMainActivity(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }



    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if userAccount is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    private void startFavoritesActivity(boolean googleLogin, String email){


        String userEmail;
        if(googleLogin)
            userEmail = email.trim();
        else
            userEmail = etEmail.getText().toString().trim();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference favoriteDocRef;

        if(book!=null){
            favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userEmail).collection("FavoriteBooks").document(book.getBookId());
            favoriteDocRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    favBooksIdsSet = new HashSet<>();
                    favBooksIdsSet.add(book.getBookId());

                    updateFavoritesSharedPrefs();
                    llProgressBarContainer.setVisibility(View.GONE);
                    Bundle bundle = new Bundle();

                    DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userEmail);
                    documentReference.update("favoriteBooksIds", FieldValue.arrayUnion(book.getBookId()));
                    bundle.putString("userId", userEmail);

                  /*  Intent intent = new Intent(LoginActivity.this,FavoritesActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();*/
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

                   /* Intent intent = new Intent(LoginActivity.this,FavoritesActivity.class);
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


    private void updateUserSharePrefsMultiple(){

        SharedPreferences sharedPreferences = getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("userAccountId", userEmail);
        myEdit.putString("name", userName);
        myEdit.putString("email", userEmail);
        myEdit.putString("imageUrl", userImageUrl);
        myEdit.putStringSet("favBooksSet", set);
        myEdit.putStringSet("favRecommendersSet", set);

        myEdit.apply();
    }


    private void updateUserCitySharePrefsSingle(String key, String value){


        SharedPreferences sharedPreferences = getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

       /* myEdit.putString("userAccountId", etEmail.getText().toString());
        myEdit.putString("name", etName.getText().toString());
        myEdit.putString("email", etEmail.getText().toString());
        myEdit.putBoolean("isTutor", false);*/

        myEdit.putString(key, value.trim());

        myEdit.apply();
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