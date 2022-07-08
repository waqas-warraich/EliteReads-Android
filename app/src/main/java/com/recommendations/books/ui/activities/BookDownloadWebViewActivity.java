package com.recommendations.books.ui.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.recommendations.books.R;

public class BookDownloadWebViewActivity extends AppCompatActivity {
    private WebView webView;
    ImageButton ibBack, ibShare ;
    ImageView ivStore;

    String bookSummaryLink, bookAmazonBuyLink;
    Context context;
    AppCompatButton buttonRetry;
    LinearLayoutCompat llNotInternetConnection, llProgressBarContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_download_web_view);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        context = BookDownloadWebViewActivity.this;

        if(getIntent()!=null){

            if(getIntent().hasExtra("summaryLink"))
                bookSummaryLink = getIntent().getStringExtra("amazonBuyLink");

            if(getIntent().hasExtra("amazonBuyLink"))
                bookAmazonBuyLink = getIntent().getStringExtra("amazonBuyLink");

        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.loadUrl("https://www.pdfdrive.com/give-and-take-why-helping-others-drives-our-success-d58864799.html");

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Mathematics II ");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();

            }
        });

        /*WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);*/

       // mkLoader = findViewById(R.id.mk_loader_web);
        buttonRetry = findViewById(R.id.bt_retry);

        llNotInternetConnection = findViewById(R.id.no_internet_page_privacy);
        llProgressBarContainer = findViewById(R.id.progressBarSignUpPrivacy);

        ivStore = findViewById(R.id.iv_store_BookWebViewDD);
        ibShare = findViewById(R.id.ib_share_BookWebViewDD);

        ivStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Book Not Available ", Toast.LENGTH_SHORT).show();


            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Want to know book recommendation from  " +
                        "Download BookRec App from PlayStore : https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                // shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, tutorProfile.getTutorName()+" on PakTutors");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        ibBack = findViewById(R.id.ib_back_BookWebViewDD);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
              /*  if(haveNetworkConnection())
                    llProgressBarContainer.setVisibility(View.VISIBLE);*/
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
                if(haveNetworkConnection())
                    llNotInternetConnection.setVisibility(View.GONE);
                else
                    llNotInternetConnection.setVisibility(View.VISIBLE);
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();

        if(!haveNetworkConnection()){
            llNotInternetConnection.setVisibility(View.VISIBLE);
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