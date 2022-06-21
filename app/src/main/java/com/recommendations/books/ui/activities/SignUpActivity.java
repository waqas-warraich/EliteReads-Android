package com.recommendations.books.ui.activities;

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
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.firebase.auth.AuthCredential;
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


public class SignUpActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    TextView tvTermsNPrivacy;

    final String TAG = "SignUpActivity";
    private final static int RC_SIGN_IN = 123;
    ImageButton ibBack;
    TextView  tvSignIn;
    User user;
    String userName, userEmail, userImageUrl, favId;
    LinearLayout llEmailContainer, llContinueWithGoogle;

    Book book;
    BookRecommenderPerson bookRecommenderPerson;
    String defaultWebClientId;
    LinearLayoutCompat llProgressBarContainer;

    Set<String> favRecommendersIdsSet;
    Set<String> favBooksIdsSet;
    Set <String>set = new HashSet();
    ArrayList<String> favBooksIdsList ;
    ArrayList<String> favRecommendersIdsList;


    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       // overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);

        user = new User();
        mAuth = FirebaseAuth.getInstance();
        ibBack = findViewById(R.id.ib_cancel_signUpActivity);
       // tvEmail= findViewById(R.id.tv_continue_with_email_option);

        varView = findViewById(android.R.id.content).getRootView();

        // tvFacebook= findViewById(R.id.tv_continue_with_facebook_option);
        tvSignIn= findViewById(R.id.sign_in_option_signup_activity);
        tvTermsNPrivacy= findViewById(R.id.tv_terms_and_conditions_signup);

        llEmailContainer = findViewById(R.id.ll_sign_up_with_email_signUpActivity);
        llContinueWithGoogle = findViewById(R.id.ll_sign_up_with_google_signUpActivity);
        llProgressBarContainer = findViewById(R.id.progressBarSignUpActivity);
        Bundle extra = getIntent().getExtras();
        if (extra != null){

            book = (Book) extra.getSerializable("bookObject");
            bookRecommenderPerson = (BookRecommenderPerson) extra.getSerializable("recommenderObject");
        }


        String text = "By pressing 'SUBMIT' I declare that i have read all and I agree to the PakTutors Terms and Conditions.";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SignUpActivity.this, PrivacyPolicyActivity.class);
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

        if(getIntent()!=null){
            favId = getIntent().getStringExtra("favId");
        }


        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        llEmailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(SignUpActivity.this, SignUpWithEmailActivity.class);

                bundle.putSerializable("BookObject",book);
                intent.putExtra("recommenderObject", bookRecommenderPerson);
                intent.putExtras(bundle);

                /*if(book!=null || bookRecommenderPerson!=null){
                    bundle.putSerializable("bookObject",book);
                    intent.putExtras(bundle);
                }*/

                startActivity(intent);

            }
        });
       /* tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
*/

        createRequest();
        llContinueWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

/*
        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }



    private void createRequest() {

        // Configure Google Sign In

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("760514835810-oot7ljqsl80b0kod4sme0831h84ivfvk.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        llProgressBarContainer.setVisibility(View.VISIBLE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(TAG, "request code: "+requestCode);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Log.v(TAG, "requesting..");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                userEmail = account.getEmail();
                userName = account.getDisplayName();
                userImageUrl = account.getPhotoUrl().toString();
                String id= userEmail.trim();
                documentReference = db.collection("EliteReadsAndroidUserAccounts").document(id);

                //user.setUserImageUrl(account.getPhotoUrl().toString());
                if(userImageUrl!=null)
                    user.setUserImageUrl(account.getPhotoUrl().toString());
                if(userName!=null)
                    user.setName(userName);

                user.setEmail(userEmail);

                getUserData(userEmail);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.v(TAG, "success");


                        updateUserSharePrefsMultiple();

                        //  llProgressBarContainer.setVisibility(View.VISIBLE);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                firebaseAuthWithGoogle(account.getIdToken());
                                llProgressBarContainer.setVisibility(View.INVISIBLE);
                            }
                        }, 1000);

                    }
                });





/*
                llProgressBarContainer.setVisibility(View.VISIBLE);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SignUpActivity.this,NavigationActivity.class);
                        startActivity(intent);

                        llProgressBarContainer.setVisibility(View.INVISIBLE);
                    }
                }, 2000);*/





            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                llProgressBarContainer.setVisibility(View.GONE);
                Log.v(TAG, "failed");
                // ...
            }
        }
    }






    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();

                        Log.v(TAG, "task.isSuccessful()");

                        if(bookRecommenderPerson!=null || book !=null){
                            llProgressBarContainer.setVisibility(View.VISIBLE);
                            startFavoritesActivity();
                            finish();
                        }
                        else
                            startMainActivity();

                    }
                });
    }





    private void startMainActivity(){

        Log.v(TAG, "startActivity");

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);


    }

  /*  private void startFavoritesActivity(){


        Log.v(TAG, "startFavoritesActivity");
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userEmail).collection("Favorites").document(book.getBookId());

        favoriteDocRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                llProgressBarContainer.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userEmail.trim());
               // Intent intent = new Intent(SignUpActivity.this,Fav.class);
              *//*  intent.putExtras(bundle);
                startActivity(intent);*//*
            }
        });


    }*/


    private void startFavoritesActivity(){


      /*  String userEmail;
        if(googleLogin)
            userEmail = email.trim();
        else
            userEmail = etEmail.getText().toString().trim();*/


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

                   /* Intent intent = new Intent(SignUpActivity.this,FavoritesActivity.class);
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
/*
                    Intent intent = new Intent(SignUpActivity.this,FavoritesActivity.class);
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


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= mAuth.getCurrentUser();
        //FirebaseUser userAccount = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null){
            /*llProgressBarContainer.setVisibility(View.VISIBLE);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    llProgressBarContainer.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(SignUpActivity.this,NavigationActivity.class);
                    startActivity(intent);
                }
            }, 2000);*/

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

                                    if(favBooksIdsList!=null)
                                    favBooksIdsSet.addAll(favBooksIdsList);

                                    if(favRecommendersIdsList!=null)
                                    favRecommendersIdsSet.addAll(favRecommendersIdsList);

                                  /*  userName = user.getName();
                                    userImageUrl = user.getUserImageUrl();*/
                                    updateFavoritesSharedPrefs();
                                }


                            }


                        }else{
                            Toast.makeText(SignUpActivity.this, "SignUp Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }, 1000);


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