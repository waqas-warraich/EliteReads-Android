package com.recommendations.books.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.recommendations.books.R;
import com.recommendations.books.adapters.CommonBookAdapter;
import com.recommendations.books.databinding.ActivityViewRecommenderBinding;
import com.recommendations.books.models.Book;
import com.recommendations.books.models.BookRecommenderPerson;
import com.recommendations.books.util.ItemOffsetDecoration;
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

import java.util.HashSet;
import java.util.Set;

import uk.co.deanwild.flowtextview.FlowTextView;


public class ViewRecommenderActivity extends AppCompatActivity {

    private ActivityViewRecommenderBinding binding;
    CommonBookAdapter commonBookAdapter;
    BookRecommenderPerson bookRecommenderPerson;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    int finalHeight, finalWidth;

    Set<String> favRecommendersIdsSet;
    Set <String>set = new HashSet();

    String userAccountId;
    ImageView ivFavorites;
    FlowTextView flowTextView;

    boolean readMore = false;
    private TextView tvSeeMoreLess;

    boolean isConnected = true;
    private boolean monitoringConnectivity = false;
    View varView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewRecommenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        context = binding.getRoot().getContext();

        flowTextView = (FlowTextView) findViewById(R.id.ftv);
        flowTextView.setTextColor(getResources().getColor(R.color.recommender_bio_text_color_1));
        flowTextView.setTextDirection(View.TEXT_ALIGNMENT_VIEW_END);
        flowTextView.setTextSize(getResources().getDimension(R.dimen.text_size_14));

        varView = findViewById(android.R.id.content).getRootView();


        Bundle extra = getIntent().getExtras();
        if (extra != null){
            // tutorProfileFireStoreId = extra.getString("tFireStoreId");
            bookRecommenderPerson= (BookRecommenderPerson) extra.getSerializable("BookRecommenderObject");

            /*currentUserAccountId= getIntent().getStringExtra("currentUserId");
            if(currentUserAccountId!=null&&!currentUserAccountId.trim().isEmpty()){
                updateFavIds();
                // updateCounters();

                if(!tutorProfile.getProfileViewsIds().contains(currentUserAccountId)){
                    DocumentReference documentRefTutorProfile = db.collection("tutorProfiles").document(tutorProfile.getEmail());
                    documentRefTutorProfile.update("profileViewsIds", FieldValue.arrayUnion(currentUserAccountId));
                }

            }*/



        }

        final ViewTreeObserver vto = binding.civRecommenderViewBookRecommender.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
               /* binding.civRecommenderViewBookRecommender.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                finalHeight = binding.civRecommenderViewBookRecommender.getMeasuredHeight();
                finalWidth = binding.civRecommenderViewBookRecommender.getMeasuredWidth();
                makeSpan();*/
            }
        });


        if(currentUser!=null){
            readUserSharedPrefData();
        }

        binding.ibBackViewRecommender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(bookRecommenderPerson!=null){

            setBooksRecyclerView();
            initViewsData();
        }



    }



    private void initViewsData(){

        //binding.tvRecommenderBio.setText(bookRecommenderPerson.getBio());
        //binding.toolbarViewBookRecommender.setTitle(bookRecommenderPerson.getName());

        ivFavorites = binding.ivFavoriteRecommenderDetails;



        tvSeeMoreLess = findViewById(R.id.tv_see_more_RecommenderDetailsActivity);

        if(favRecommendersIdsSet!=null)
        if(favRecommendersIdsSet.contains(bookRecommenderPerson.getBookRecommenderId()))
            ivFavorites.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fav_group));


        if(bookRecommenderPerson.getBio()!=null)
        {
            if(bookRecommenderPerson.getBio().length()>350)
                flowTextView.setText(bookRecommenderPerson.getBio().substring(0, 350)+ "...");
            else{
                tvSeeMoreLess.setVisibility(View.GONE);
                flowTextView.setText(bookRecommenderPerson.getBio());
            }
        }
        if(bookRecommenderPerson.getImageUrl()!=null)
        {
            if(bookRecommenderPerson.getImageUrl().length()>10)
                Glide.with(this)
                        .load(bookRecommenderPerson.getImageUrl())
                        .centerCrop()
                        .placeholder(R.drawable.book_loader)
                        .into(binding.civRecommenderViewBookRecommender);
        }

        binding.ibShareRecommenderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Find all recommended books by "+bookRecommenderPerson.getName()+" and learn or review the key takeaways for free on EliteReads: https://play.google.com/store/apps/details?id=com.titanreads.topreads");

                // shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, tutorProfile.getTutorName()+" on PakTutors");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        binding.ibRecommenderWebSiteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bookRecommenderPerson.getWebsiteLink()!=null&&bookRecommenderPerson.getWebsiteLink().length()>10)
                    openBrowser(bookRecommenderPerson.getWebsiteLink());
                else
                    Toast.makeText(ViewRecommenderActivity.this, "Website not available", Toast.LENGTH_SHORT).show();

            }
        });

        binding.ibRecommenderWikiUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookRecommenderPerson.getWikiLink()!=null&&bookRecommenderPerson.getWikiLink().length()>10)
                    openBrowser(bookRecommenderPerson.getWikiLink());
                else
                    Toast.makeText(ViewRecommenderActivity.this, "Wikipedia not available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ibRecommenderTwitterUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookRecommenderPerson.getTwitterLink()!=null&&bookRecommenderPerson.getTwitterLink().length()>10)
                    openBrowser(bookRecommenderPerson.getTwitterLink());
                else
                    Toast.makeText(ViewRecommenderActivity.this, "Twitter not available", Toast.LENGTH_SHORT).show();
            }
        });

/*
        binding.ivFavoriteRecommenderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser!=null){

                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
*/



        tvSeeMoreLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!readMore)
                {
                    flowTextView.setText(bookRecommenderPerson.getBio());

                    readMore = true;
                    tvSeeMoreLess.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_read_less_arrow_up_24, 0, 0, 0);
                    tvSeeMoreLess.setText("Read less");
                }else{

                    if(bookRecommenderPerson.getBio().length()>350)
                        flowTextView.setText(bookRecommenderPerson.getBio().substring(0, 350)+ "...");
                    else{
                        tvSeeMoreLess.setVisibility(View.GONE);
                        flowTextView.setText(bookRecommenderPerson.getBio());
                    }
                    readMore = false;
                    tvSeeMoreLess.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_read_more_arrow_down_24, 0, 0, 0);
                    tvSeeMoreLess.setText("Read more");
                }

            }
        });



        ivFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser!=null){

                    if(favRecommendersIdsSet.contains(bookRecommenderPerson.getBookRecommenderId())){


                        ivFavorites.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_outline_favorite_24));

                        Toast.makeText(getApplicationContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

                        favRecommendersIdsSet.remove(bookRecommenderPerson.getBookRecommenderId());
                        updateFavorites(bookRecommenderPerson, true);


                    }else{

                        ivFavorites.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fav_group));
                        Toast.makeText(getApplicationContext(), "Saved to Favorites", Toast.LENGTH_SHORT).show();
                        favRecommendersIdsSet.add(bookRecommenderPerson.getBookRecommenderId());

                        updateFavorites(bookRecommenderPerson, false);
                    }



                }else
                {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recommenderObject",bookRecommenderPerson);
                    intent.putExtras(bundle);
                    intent.putExtra("currentUserId", userAccountId);
                    view.getContext().startActivity(intent);

                }

            }
        });



    }

    private void openBrowser(String url)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void setBooksRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("AllBookRecommenders").document(bookRecommenderPerson.getBookRecommenderId()).collection("RecommendedBooks");

        RecyclerView recyclerView= binding.bookRecommenderBooksRecycler;
        recyclerView.setLayoutManager(new GridLayoutManager(this, getApplicationContext().getResources().getInteger(R.integer.book_grid_layout_columns)));
        recyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.book_item_offset);
        recyclerView.addItemDecoration(itemDecoration);


        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        commonBookAdapter = new CommonBookAdapter(options);
        recyclerView.setAdapter(commonBookAdapter);


        if(commonBookAdapter!=null)
            commonBookAdapter.startListening();

    }


    private void updateFavoritesSharedPrefs(){

        SharedPreferences sharedPreferences = getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putStringSet("favRecommendersSet", favRecommendersIdsSet);
        myEdit.apply();
    }


    private void updateUserSharePrefsMultiple(){

        SharedPreferences sharedPreferences = getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putStringSet("favRecommendersSet", favRecommendersIdsSet);
        myEdit.apply();
    }


    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        favRecommendersIdsSet = sharedPreferences.getStringSet("favRecommendersSet", set);


    }


    public void updateFavorites(BookRecommenderPerson recommender, boolean isFav){

        //Log.d(TAG, "updateFavorites>    user account id:   "+ currentUserAccountId);
        DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userAccountId);
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteRecommenders").document(recommender.getBookRecommenderId());

        // isFav=true;

        if(isFav)
        {
            documentReference.update("favoriteRecommendersIds", FieldValue.arrayRemove(recommender.getBookRecommenderId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    updateFavoritesSharedPrefs();
                    favoriteDocRef.delete();
                }
            });


        }
        else{
            documentReference.update("favoriteRecommendersIds", FieldValue.arrayUnion(recommender.getBookRecommenderId())).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    favoriteDocRef.set(recommender);
                    updateFavoritesSharedPrefs();


                }
            });


        }

    }



    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(commonBookAdapter!=null)
            commonBookAdapter.stopListening();
    }



    /*
    private void makeSpan() {

        */
/**
         * Get the text
         *//*

        */
/*String plainText=getResources().getString(R.string.text_sample);
        Spanned htmlText = Html.fromHtml(plainText);*//*

        SpannableString mSpannableString= new SpannableString(bookRecommenderPerson.getBio());

        int allTextStart = 0;
        int allTextEnd = bookRecommenderPerson.getBio().length() - 1;

        */
/**
         * Calculate the lines number = image height.
         * You can improve it... it is just an example
         *//*

        int lines;
        Rect bounds = new Rect();

        bounds.set(20, 20, 20, 20);

        binding.tvRecommenderBio.getPaint().getTextBounds(bookRecommenderPerson.getBio().substring(0,10), 0, 1, bounds);

        //float textLineHeight = mTextView.getPaint().getTextSize();
        float fontSpacing=binding.tvRecommenderBio.getPaint().getFontSpacing();
        lines = (int) (finalHeight/(fontSpacing));

        */
/**
         * Build the layout with LeadingMarginSpan2
         *//*

        MyLeadingMarginSpan2 span = new MyLeadingMarginSpan2(lines, finalWidth +10 );
        mSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        binding.tvRecommenderBio.setText(mSpannableString);

    }
*/

    /**
     *
     */



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