package com.titanreads.topreads.ui.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.titanreads.topreads.R;

public class BookTextSummaryActivity extends AppCompatActivity {
    private WebView webView;
    ImageButton ibBack, ibMore, ibShare, ibFontSize, ibBrightness;

    boolean highBrightness = false;

    Context context;
    private int fontSize = 16;
    private int textZoom = 100;

    private String summaryUrl, bookName="", bookAuthor="" ;

    AppCompatButton buttonRetry;
    LinearLayoutCompat llNotInternetConnection, llProgressBarContainer;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_text_summary_web_view);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        context = BookTextSummaryActivity.this;

        if(getIntent()!=null){
            summaryUrl = getIntent().getStringExtra("summaryUrl");
            bookName = getIntent().getStringExtra("bookName");
            bookAuthor = getIntent().getStringExtra("authorName");
        }

        /*webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(
                        "javascript:document.body.style.setProperty(\"color\", \"white\");"
                );
            }
        });*/

        initUi();
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        }

        webView.loadUrl(summaryUrl);



        /*WebSettings webSettings = webView.getSettings();
        webSettings.setForceDark(WebSettings.FORCE_DARK_ON);*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            webSettings.setForceDark(WebSettings.FORCE_DARK_ON);

        }*/

        // mkLoader = findViewById(R.id.mk_loader_web);




    }

    private void initUi(){


        buttonRetry = findViewById(R.id.btn_retry_no_internet);
        llNotInternetConnection = findViewById(R.id.no_internet_page_textSummary);
        llProgressBarContainer = findViewById(R.id.progressBarSignUpPrivacy);
        ibBack = findViewById(R.id.ib_back_bookTextSummary);
        ibBrightness = findViewById(R.id.ib_summary_brightness);
        ibFontSize = findViewById(R.id.ib_font_size_summary);
        ibMore = findViewById(R.id.ib_more_bookTextSummary);
        ibShare = findViewById(R.id.ib_more_bookTextSummary);

        ibFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final WebSettings webSettings = webView.getSettings();
                //webSettings.setDefaultFontSize(fontSize);




                PopupMenu popup = new PopupMenu(BookTextSummaryActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();

                inflater.inflate(R.menu.context_menu_font_size, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.increase_font_cm:
                            {
                                // fontSize = fontSize + 3;
                                // webSettings.setDefaultFontSize(fontSize);

                                textZoom = textZoom+10;
                                webSettings.setTextZoom(textZoom);
                                webView.loadUrl("https://www.hustleescape.com/book-summary-the-5am-club-by-robin-sharma/");
                                return true;
                            }


                            case R.id.decrease_font_cm:
                            {
                                // fontSize = fontSize -3;
                                // webSettings.setDefaultFontSize(fontSize);


                                textZoom = textZoom-10;
                                webSettings.setTextZoom(textZoom);
                                webView.loadUrl(summaryUrl);
                                return true;
                            }


                            case R.id.reset_font_cm:
                            {
                                //fontSize = 16;
                                //webSettings.setDefaultFontSize(fontSize);

                                textZoom = 100;
                                webSettings.setTextZoom(textZoom);

                                webView.loadUrl(summaryUrl);
                                return true;
                            }

                            default:
                                return false;

                        }

                    }




                });

                popup.show();

            }
        });


        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Read summary or the key takeaways of "+bookName+" by "+bookAuthor+ " for free on EliteReads : https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(BookTextSummaryActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();

                inflater.inflate(R.menu.context_menu_text_summary, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.copy_textSummary_link:
                            {
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Summary Url: ", summaryUrl);
                                clipboard.setPrimaryClip(clip);

                                Toast.makeText(context, "Copied "+summaryUrl, Toast.LENGTH_SHORT).show();
                                return true;
                            }

                            case R.id.share_textSummary_link:
                            {

                                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT,summaryUrl);
                                startActivity(Intent.createChooser(shareIntent, "Share via"));

                                return true;
                            }


                            default:
                                return false;

                        }

                    }




                });

                popup.show();


            }
        });

        /*ibSave = findViewById(R.id.cancel_ib_app_privacy_policy_activity);
        ibShare = findViewById(R.id.cancel_ib_app_privacy_policy_activity);*/




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
                if(haveNetworkConnection())
                    llProgressBarContainer.setVisibility(View.VISIBLE);
                /*if (!llNotInternetConnection.isShown()) {
                    llNotInternetConnection.setVisibility(View.VISIBLE);
                }*/
            }

            public void onPageFinished(WebView webview, String url) {
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
                    webView.loadUrl(summaryUrl);
                }

                else{
                    llNotInternetConnection.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.INVISIBLE);
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