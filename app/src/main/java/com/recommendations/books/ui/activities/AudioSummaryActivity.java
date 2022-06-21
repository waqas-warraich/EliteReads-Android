package com.recommendations.books.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.recommendations.books.R;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class AudioSummaryActivity extends AppCompatActivity  implements JcPlayerManagerListener {

    final String TAG = "AudioSummaryActivity";
    JcPlayerView jcPlayerView;

    ImageButton ibBack, ibStore, ibShare;
    ImageView ivBookImage, ivBackward, ivForward;
    String bookImageUrl = "", bookName = "";
    String bookAmazonBuyLink;
    Context context;

    String authorName="";
    private String summaryUrl;
    long currentDuration;

    JcStatus mJcStatus ;

    Integer position;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_audio_summary);
        context = AudioSummaryActivity.this;


        if(getIntent()!=null){

            if(getIntent().hasExtra("bookImageUrl"))
            bookImageUrl = getIntent().getStringExtra("bookImageUrl");

            if(getIntent().hasExtra("summaryUrl"))
                summaryUrl = getIntent().getStringExtra("summaryUrl");

            if(getIntent().hasExtra("bookName"))
                bookName = getIntent().getStringExtra("bookName");

            if(getIntent().hasExtra("bookSummaryUrl"))
                summaryUrl = getIntent().getStringExtra("bookSummaryUrl");

            if(getIntent().hasExtra("amazonBuyLink"))
                bookAmazonBuyLink = getIntent().getStringExtra("amazonBuyLink");

            if(getIntent().hasExtra("AuthorName"))
                authorName = getIntent().getStringExtra("AuthorName");

        }


        varView = findViewById(android.R.id.content).getRootView();

        initPlayer();
        initUi();

      /*  Integer position = new Integer(-1);
        JcAudio jcAudio = new JcAudio("Summary",position , "https://backend.quickread.com/files/351/books/audio/610bbc61bca251.79508652.mp3", Origin.URL);*/


    }


    public void initUi(){

        ivBookImage = findViewById(R.id.iv_book_image_audioSummary);
        ivForward = findViewById(R.id.forward_30_sec);
        ivBackward = findViewById(R.id.backward_30_sec);

        ibBack = findViewById(R.id.ib_back_AudioSummaryActivity);
        ibStore = findViewById(R.id.ib_store_AudioSummaryActivity);
        ibShare = findViewById(R.id.ib_share_AudioSummaryActivity);



        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(jcPlayerView!=null)
                    if(jcPlayerView.isPlaying()){
                        Log.v(TAG, "isPlaying");
                        jcPlayerView.pause();

                        jcPlayerView.getCurrentAudio().equals(null);

                        jcPlayerView = null;
                    }

                finish();
            }
        });

        ibStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "book not available", Toast.LENGTH_SHORT).show();
            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Listen summary or the key takeaways of "+bookName+" by "+authorName+ " for free on EliteReads : "+"https://play.google.com/store/apps/details?id=com.titanreads.topreads");

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        if(bookImageUrl!=null)
            Glide.with(context)
                    .load(bookImageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.book_loader)
                    .into(ivBookImage);

        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "just clicked");

                if(jcPlayerView.isPlaying()){

                    currentDuration = mJcStatus.getDuration();
                    Log.v(TAG, "current duration : "+currentDuration);
                    currentDuration = currentDuration + 30000;

                    mJcStatus.setDuration(currentDuration);
                    Log.v(TAG, "JcStatus.PlayState.PLAYING");
                }
            }
        });

        ivBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ivBackward");


            }
        });
    }

    public void initPlayer(){

        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayer);
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL(bookName + " Summary",summaryUrl));
        jcPlayerView.initPlaylist(jcAudios, this);

        jcPlayerView.setJcPlayerManagerListener(this);
        jcPlayerView.createNotification();
        jcPlayerView.createNotification(R.drawable.ic_topreads_icon_teal);
        jcPlayerView.playAudio(jcAudios.get(0));
    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onContinueAudio(@NonNull JcStatus jcStatus) {

        // if(jcPlayerView.getCurrentStatus().getPlayState() == JcStatus.)

    }

    @Override
    public void onJcpError(@NonNull Throwable throwable) {

    }

    @Override
    public void onPaused(@NonNull JcStatus jcStatus) {

    }

    @Override
    public void onPlaying(@NonNull JcStatus jcStatus) {




    }

    @Override
    public void onPreparedAudio(@NonNull JcStatus jcStatus) {
        //currentDuration = jcStatus.getDuration();

        mJcStatus = jcStatus;
    }

    @Override
    public void onStopped(@NonNull JcStatus jcStatus) {

    }

    @Override
    public void onTimeChanged(@NonNull JcStatus jcStatus) {


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(jcPlayerView!=null)
            if(jcPlayerView.isPlaying()){
                Log.v(TAG, "isPlaying");
                jcPlayerView.pause();

                jcPlayerView.getCurrentAudio().equals(null);

                jcPlayerView = null;
            }
        //jcPlayerView.getCurrentStatus().setPlayState(JcStatus.PlayState.STOP);
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


