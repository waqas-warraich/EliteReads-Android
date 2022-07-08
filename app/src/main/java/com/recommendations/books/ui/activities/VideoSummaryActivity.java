package com.recommendations.books.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recommendations.books.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoSummaryActivity extends YouTubeBaseActivity {

    YouTubePlayerView ytPlayer;

    private ImageButton ibShare, ibCopy, ibBack;
    Context context;

    String videoID;

    String ApiKey;
    String bookName = "", authorName="";

    String summaryUrl;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_summary);

        if(getIntent()!=null){
            videoID = getIntent().getStringExtra("videoId");
            bookName = getIntent().getStringExtra("bookName");
            authorName = getIntent().getStringExtra("authorName");

        }
        context = VideoSummaryActivity.this;



        varView = findViewById(android.R.id.content).getRootView();

        initUi();

        ytPlayer = (YouTubePlayerView)findViewById(R.id.youtube_player_videoSummary);

        summaryUrl = "http://www.youtube.com/embed/" + videoID + "?autoplay=1&vq=small";

        db.collection("ApiKeys")
                .document("GCP Api Keys")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       // Log.d(TAG, "google_places_api_key: " + documentSnapshot.get("google_places_api_key") + "");
                        ApiKey = documentSnapshot.getString("YTPlayerKey");


                        ytPlayer.initialize(
                                ApiKey,
                                new YouTubePlayer.OnInitializedListener() {
                                    // Implement two methods by clicking on red
                                    // error bulb inside onInitializationSuccess
                                    // method add the video link or the playlist
                                    // link that you want to play In here we
                                    // also handle the play and pause
                                    // functionality
                                    @Override
                                    public void onInitializationSuccess(
                                            YouTubePlayer.Provider provider,
                                            YouTubePlayer youTubePlayer, boolean b)
                                    {
                                        youTubePlayer.loadVideo(videoID);
                                        youTubePlayer.play();
                                    }

                                    // Inside onInitializationFailure
                                    // implement the failure functionality
                                    // Here we will show toast
                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                        YouTubeInitializationResult
                                                                                youTubeInitializationResult)
                                    {
                                        Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });





    }

    private void initUi(){

        ibShare = findViewById(R.id.ib_share_videoSummaryActivity);
        ibCopy = findViewById(R.id.ib_copy_videoSummaryActivity);
        ibBack = findViewById(R.id.ib_back_videoSummaryActivity);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Summary Url: ", summaryUrl);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Copied: "+summaryUrl, Toast.LENGTH_SHORT).show();
            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Learn or review the key takeaways of "+bookName+" by "+authorName+ " for free on BookRec : https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
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