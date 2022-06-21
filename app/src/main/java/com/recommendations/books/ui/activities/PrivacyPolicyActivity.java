package com.recommendations.books.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.recommendations.books.R;


public class PrivacyPolicyActivity extends AppCompatActivity {
    private WebView webView;
    ImageButton ibBack;
    //MKLoader mkLoader;
    AppCompatButton buttonRetry;
    LinearLayoutCompat llNotInternetConnection, llProgressBarContainer;

    ImageButton ibBrightness;

    boolean highBrightness = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://paktutors-app.github.io/privacy.html");

        /*WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);*/

       // mkLoader = findViewById(R.id.mk_loader_web);
        buttonRetry = findViewById(R.id.btn_retry_no_internet);

        llNotInternetConnection = findViewById(R.id.no_internet_page_privacy);
        llProgressBarContainer = findViewById(R.id.progressBarSignUpPrivacy);

        ibBrightness = findViewById(R.id.ib_brightness_privacyPolicyActivity);
        ibBack = findViewById(R.id.ib_back_privacyPolicyActivity);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);

        }


        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(haveNetworkConnection())
                    llProgressBarContainer.setVisibility(View.VISIBLE);
                /*if (!llNotInternetConnection.isShown()) {
                    llNotInternetConnection.setVisibility(View.VISIBLE);
                }*/
            }

            public void onPageFinished(WebView view, String url) {
                llProgressBarContainer.setVisibility(View.INVISIBLE);
                /*if (llNotInternetConnection.isShown()) {
                    llNotInternetConnection.setVisibility(View.INVISIBLE);
                }*/
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                llProgressBarContainer.setVisibility(View.INVISIBLE);

                /*if (llNotInternetConnection.isShown()) {
                    llNotInternetConnection.setVisibility(View.INVISIBLE);
                }*/
            }
        });


        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveNetworkConnection()){

                    llNotInternetConnection.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                    webView.loadUrl("https://paktutors-app.github.io/privacy.html");
                }

                else
                    llNotInternetConnection.setVisibility(View.VISIBLE);
            }
        });

        ibBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!highBrightness)
                {
                    if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
                    }


                    ibBrightness.setImageResource(R.drawable.ic_low_brightness_24);

                    highBrightness = true;
                }else{

                    if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                    }

                    ibBrightness.setImageResource(R.drawable.ic_round_high_brightness_24);
                    highBrightness = false;

                }
            }
        });




    }



    @Override
    protected void onStart() {
        super.onStart();

        if(!haveNetworkConnection()){
            llNotInternetConnection.setVisibility(View.VISIBLE);
            webView.setVisibility(View.INVISIBLE);
            // llContainer.setVisibility(View.GONE);
            // coordinatorLayout.setVisibility(View.GONE);
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}