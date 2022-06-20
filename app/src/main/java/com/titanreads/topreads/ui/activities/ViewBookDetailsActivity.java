package com.titanreads.topreads.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.titanreads.topreads.R;
import com.titanreads.topreads.adapters.QuotesAdapter;
import com.titanreads.topreads.adapters.RecommendationPersonAdapter;


import com.titanreads.topreads.databinding.ActivityViewBookDetailsBinding;
import com.titanreads.topreads.models.Book;
import com.titanreads.topreads.models.BookRecommenderPerson;
import com.titanreads.topreads.models.RecommendationQuote;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ViewBookDetailsActivity extends AppCompatActivity {

    final String TAG = "ViewBookDetailsActivity";
    private RecommendationPersonAdapter recommendationPersonAdapter;
    private QuotesAdapter quotesAdapter;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;

    private CardView cvBuyOnAmazon;
    private TextView tvBookName, tvBookAuthorName, tvBookReleasedDate, tvBookDescription, tvWatchVideoSummary;
    private TextView tvBookGenre;
    boolean readMore = false;
    private TextView tvSeeMoreLess;
    private TextView tvReadBookSummary, tvListenAudioSummary;

    private ImageButton ibShare, ibBack;
    private TextView tvBookWebPage, tvTorrentDownloadLink;

    ImageView ivFavorite, ivBook;

    private RecyclerView recommendationPersonRecyclerView;
    private RecyclerView quotesRecyclerView;
    Book book;
    Context context;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Set<String> favBooksIdsSet;
    String userAccountId;
    Set <String>set = new HashSet();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ActivityViewBookDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_book_details);
        context = ViewBookDetailsActivity.this;


        Bundle extra = getIntent().getExtras();
        if (extra != null){
            // tutorProfileFireStoreId = extra.getString("tFireStoreId");
            book= (Book) extra.getSerializable("BookObject");


        }

        varView = findViewById(android.R.id.content).getRootView();

        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



        if(book!=null){
            setBookRecommenderRecyclerView();
            setQuotesRecyclerView();
            initData();

        }

        if(currentUser!=null)
        {
            readUserSharedPrefData();

            if(favBooksIdsSet.contains(book.getBookId()))
                ivFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fav_group));

        }


    }


    private void initData(){


        ibBack = findViewById(R.id.ib_back_bookDetailsActivity);
        ibShare = findViewById(R.id.ib_share_bookDetailsActivity);
        ivFavorite = findViewById(R.id.iv_favorite_bookDetailsActivity);
        tvBookName = findViewById(R.id.tvBookNameViewBook);
        tvSeeMoreLess = findViewById(R.id.tv_see_more_ViewBookDetails);
        tvBookAuthorName = findViewById(R.id.tvBookAuthorNameViewBookDetails);
        tvBookReleasedDate = findViewById(R.id.tvBookReleaseDateViewBook);
        cvBuyOnAmazon = findViewById(R.id.cv_getOnAmazonLink);
        tvBookDescription = findViewById(R.id.tvBookDescriptionViewBook);
        tvBookGenre = findViewById(R.id.tv_book_genre_bookDetailsActivity);

        tvReadBookSummary = findViewById(R.id.tv_read_bookSummary);
        tvListenAudioSummary = findViewById(R.id.tv_listen_bookSummary);
        tvWatchVideoSummary = findViewById(R.id.tv_watch_bookSummary);

        tvBookWebPage = findViewById(R.id.bookFreeWebLink);

        //tvTorrentDownloadLink = findViewById(R.id.downloadTorrentLink);

        ivBook = findViewById(R.id.iv_book_item_viewBookDetails);


       /* if(currentUser!=null)
        {
            if(favBooksIdsSet!=null)
            if(favBooksIdsSet.contains(book.getBookId()))
                ivFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fav_group));

        }
*/



        if(book.getBookName()!=null)
            tvBookName.setText(book.getBookName());

        if(book.getBookReleaseDate()!=null)
            tvBookReleasedDate.setText(book.getBookReleaseDate());

        if(book.getBookGenreTags()!=null)
        {

            if(!book.getBookGenreTags().isEmpty())
            {

                tvBookGenre.setText(book.getBookGenreTags().get(0));

                /*ArrayList <String> tags = removeDuplicates(book.getBookGenreTags());

                if(tags.size()>=2){
                    tvBookGenre.setText(tags.get(0)+", "+tags.get(1));
                }else{

                }*/
            }

        }


        if(book.getBookAuthor()!=null)
            tvBookAuthorName.setText(book.getBookAuthor());

        if(book.getBookImageUrl()!=null)
        if(book.getBookImageUrl().length()>10)
            Glide.with(context)
                    .load(book.getBookImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.book_loader)
                    .into(ivBook);

        if(book.getBookDescription()!=null)
            tvBookDescription.setText(book.getBookDescription().substring(0, 150)+ "...");

        cvBuyOnAmazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(book.getBookAmazonLink()!=null){

                }else{
                    Toast.makeText(context, "this book will available soon", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvReadBookSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(book.getBookSummaryArticleUrl()!=null){
                    Intent intent = new Intent(context, BookTextSummaryActivity.class);
                    intent.putExtra("summaryUrl", book.getBookSummaryArticleUrl());

                    if(book.getBookAuthor()!=null)
                        intent.putExtra("authorName", book.getBookSummaryVideoUrl());

                    if(book.getBookName()!=null)
                        intent.putExtra("bookName", book.getBookSummaryVideoUrl());

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Sorry! Summary not Available", Toast.LENGTH_SHORT).show();
                }




            }
        });

        tvListenAudioSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(book.getBookSummaryAudioUrl()!=null){
                    Intent intent = new Intent(context, AudioSummaryActivity.class);
                    if(book.getBookImageUrl()!=null)
                        intent.putExtra("bookImageUrl", book.getBookImageUrl());
                    if(book.getBookName()!=null)
                        intent.putExtra("bookName", book.getBookName());
                    if(book.getBookAmazonLink()!=null)
                        intent.putExtra("amazonBuyLink", book.getBookAmazonLink());

                    if(book.getBookAuthor()!=null)
                        intent.putExtra("authorName", book.getBookSummaryVideoUrl());



                    if(book.getBookSummaryAudioUrl()!=null)
                        intent.putExtra("summaryUrl", book.getBookSummaryAudioUrl());
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "Sorry! Audio Summary not Available", Toast.LENGTH_SHORT).show();

                }



            }
        });

        tvWatchVideoSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(book.getBookSummaryVideoUrl()!=null){
                    Intent intent = new Intent(context, VideoSummaryActivity.class);

                    if(book.getBookAuthor()!=null)
                        intent.putExtra("authorName", book.getBookSummaryVideoUrl());

                    if(book.getBookName()!=null)
                        intent.putExtra("bookName", book.getBookSummaryVideoUrl());

                    if(book.getBookSummaryVideoUrl()!=null)
                        intent.putExtra("videoId", book.getBookSummaryVideoUrl());




                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Sorry! Video Summary not Available", Toast.LENGTH_SHORT).show();

                }


            }
        });




        tvBookWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookDownloadWebViewActivity.class);
                if(book.getBookAmazonLink()!=null)
                    intent.putExtra("amazonBuyLink", book.getBookAmazonLink());

                if(book.getBookSummaryArticleUrl()!=null)
                    intent.putExtra("summaryLink", book.getBookAmazonLink());
                startActivity(intent);
            }
        });

        tvSeeMoreLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!readMore)
                {
                    tvBookDescription.setText(book.getBookDescription());
                    readMore = true;
                    tvSeeMoreLess.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_read_less_arrow_up_24, 0, 0, 0);
                    tvSeeMoreLess.setText("Read less");
                }else{
                    tvBookDescription.setText(book.getBookDescription().substring(0, 150)+ "...");
                    readMore = false;
                    tvSeeMoreLess.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_read_more_arrow_down_24, 0, 0, 0);
                    tvSeeMoreLess.setText("Read more");
                }

            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(!tutorProfile.getProfileShareIds().contains(currentUserAccountId)){
                    DocumentReference documentRefTutorProfile = db.collection("tutorProfiles").document(tutorProfile.getEmail());
                    documentRefTutorProfile.update("profileShareIds", FieldValue.arrayUnion(currentUserAccountId));
                }*/

                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,book.getBookName());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Learn or review the key takeaways of "+book.getBookName()+" by "+book.getBookAuthor()+ " for free on EliteReads : https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                startActivity(Intent.createChooser(shareIntent, "Share via"));


            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser!=null){


                    if(favBooksIdsSet.contains(book.getBookId())){


                        ivFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_outline_favorite_24));

                        Toast.makeText(getApplicationContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

                        favBooksIdsSet.remove(book.getBookId());
                        updateFavorites(book, true);


                    }else{

                        ivFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fav_group));
                        Toast.makeText(getApplicationContext(), "Saved to Favorites", Toast.LENGTH_SHORT).show();
                        favBooksIdsSet.add(book.getBookId());

                      /*  Snackbar snack = Snackbar.make(rootView, "Saved to Favorites", Snackbar.LENGTH_SHORT);
                        snack.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                .setAction("ViewFavorites", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(rootView.getContext(), FavoritesActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("userId", currentUserAccountId);
                                        intent.putExtras(bundle);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        rootView.getContext().startActivity(intent);

                                    }
                                }).show();*/

                        updateFavorites(book, false);
                    }




                }else{
                    Intent intent = new Intent(context, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BookObject",book);
                    intent.putExtras(bundle);
                    intent.putExtra("currentUserId", userAccountId);
                    startActivity(intent);
                }
            }
        });


       /* tvBookReleasedDate.setText(book.getBookDescription());
        tvBookReleasedDate.setText(book.getBookRating());
        tvBookReleasedDate.setText(book.getBookRatingReviews());*/

       /* if(!TextUtils.isEmpty(book.getBookPdfDriveLink())){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(book.getBookPdfDriveLink()));
            startActivity(i);
        }

        if(!TextUtils.isEmpty(book.getBookTorrentLink())){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(book.getBookTorrentLink()));
            startActivity(i);
        }*/




    }


    public void updateFavorites(Book book, boolean isFav){

        //Log.d(TAG, "updateFavorites>    user account id:   "+ currentUserAccountId);
        DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userAccountId);
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteBooks").document(book.getBookId());

        // isFav=true;

        if(isFav)
        {
            documentReference.update("favoriteBooksIds", FieldValue.arrayRemove(book.getBookId())).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    updateFavoritesSharedPrefs();
                }
            });

            favoriteDocRef.delete();
        }
        else{
            documentReference.update("favoriteBooksIds", FieldValue.arrayUnion(book.getBookId())).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    favoriteDocRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            updateFavoritesSharedPrefs();
                        }
                    });

                }
            });


        }

    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    private void setQuotesRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("AllRecommendedBooks").document(book.getBookId().trim()).collection("RecommendationQuotes").limit(10);


        quotesRecyclerView= findViewById(R.id.quotes_recyclerView_bookView);
        quotesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        quotesRecyclerView.setNestedScrollingEnabled(false);
        quotesRecyclerView.setHasFixedSize(false);
        quotesRecyclerView.setItemAnimator(null);


        FirestoreRecyclerOptions<RecommendationQuote> options = new FirestoreRecyclerOptions.Builder<RecommendationQuote>()
                .setQuery(query, RecommendationQuote.class)
                .build();

        quotesAdapter = new QuotesAdapter(options);

        quotesRecyclerView.setAdapter(quotesAdapter);

        if(quotesAdapter!=null)
            quotesAdapter.startListening();


    }


    private void setBookRecommenderRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("AllRecommendedBooks").document(book.getBookId().trim()).collection("BookRecommenders").limit(10);

        recommendationPersonRecyclerView= findViewById(R.id.recommender_recyclerView_bookView);
        recommendationPersonRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        recommendationPersonRecyclerView.setHasFixedSize(true);
        recommendationPersonRecyclerView.setItemAnimator(null);

        FirestoreRecyclerOptions<BookRecommenderPerson> options = new FirestoreRecyclerOptions.Builder<BookRecommenderPerson>()
                .setQuery(query, BookRecommenderPerson.class)
                .build();

        recommendationPersonAdapter = new RecommendationPersonAdapter(options, false);
        recommendationPersonRecyclerView.setAdapter(recommendationPersonAdapter);

        recommendationPersonAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);

        if(recommendationPersonAdapter!=null)
            recommendationPersonAdapter.startListening();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        checkConnectivity();
    }

    @Override
    protected void onStop() {
        super.onStop();




    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(recommendationPersonAdapter!=null)
            recommendationPersonAdapter.stopListening();

        if(quotesAdapter!=null)
            quotesAdapter.stopListening();



    }

    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        //  name = sharedPreferences.getString("name", "");

       // if(sharedPreferences.contains("favRecommendersSet"))

        //Log.v(TAG, favBooksIdsSet.toString());

        favBooksIdsSet = sharedPreferences.getStringSet("favBooksSet", set);

    }

    private void updateFavoritesSharedPrefs(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putStringSet("favBooksSet", favBooksIdsSet);
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


}