package com.titanreads.topreads.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.titanreads.topreads.R;
import com.titanreads.topreads.models.Book;
import com.titanreads.topreads.ui.activities.ViewBookDetailsActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.Set;

public class FavoriteBooksAdapter extends FirestoreRecyclerAdapter<Book, FavoriteBooksAdapter.FavoriteBooksAdapterVH> {


    Context context;
    final String TAG = "FavoriteBooksAdapter";

    Set<String> favBooksIdsSet;
    String userAccountId;
    Set <String>set = new HashSet();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public FavoriteBooksAdapter(@NonNull FirestoreRecyclerOptions<Book> options, Context context) {
        super(options);
        this.context = context;

        readUserSharedPrefData();
    }

    @Override
    protected void onBindViewHolder(@NonNull FavoriteBooksAdapterVH holder, int position, @NonNull Book model) {

        if(model.getBookImageUrl()!=null)
        {
           // holder.progressBarBookImageLoader.setVisibility(View.VISIBLE);
            Glide.with(context).load(model.getBookImageUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                   // holder.progressBarBookImageLoader.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                  //  holder.progressBarBookImageLoader.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.ivBookImage);

        }

        //updateFavorites(model.getBookId());
        holder.tvBookName.setText(model.getBookName());
        holder.tvBookAuthor.setText(model.getBookAuthor());

        if(model.getBookReleaseDate()!=null)
            holder.tvBookReleaseDate.setText(model.getBookReleaseDate());

        if(model.getBookGenreTags()!=null)
        {
            if(!model.getBookGenreTags().isEmpty())
            {
                holder.tvBookGenre.setText(model.getBookGenreTags().get(0));
            }
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("BookObject",model);

                Intent intent = new Intent(context, ViewBookDetailsActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.ivShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,model.getBookName());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Learn or review the key takeaways of "+model.getBookName()+" by "+model.getBookAuthor()+ " for free on EliteReads : https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });



        holder.ivDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavorites(model);
            }
        });

        Log.v(TAG, model.getBookName());
        Log.v(TAG, model.getBookAuthor());


    }

    @NonNull
    @Override
    public FavoriteBooksAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_book,
                parent, false);

        return new FavoriteBooksAdapterVH(v);
    }

    public class FavoriteBooksAdapterVH extends RecyclerView.ViewHolder{

        private ImageView ivBookImage;
        private ImageView ivShareBtn;
        private ImageView ivDeleteBtn;
        private TextView tvBookName;
        private TextView tvBookAuthor;
        private TextView tvBookReleaseDate;
        private TextView tvBookGenre;
        ProgressBar progressBarBookImageLoader;

        public FavoriteBooksAdapterVH(@NonNull View itemView) {
            super(itemView);

            ivBookImage = itemView.findViewById(R.id.iv_book_cover_image_item);
            ivShareBtn = itemView.findViewById(R.id.iv_share_bookItem);
            ivDeleteBtn = itemView.findViewById(R.id.iv_delete_btn_bookItem);
            tvBookName = itemView.findViewById(R.id.tv_name_bookName_fav);
            tvBookAuthor = itemView.findViewById(R.id.tv_bookAuthor_name_value);
            tvBookReleaseDate = itemView.findViewById(R.id.tv_book_publishing_date_value_fav);
            tvBookGenre = itemView.findViewById(R.id.tv_book_genre_bookFavItem);
           // progressBarBookImageLoader = itemView.findViewById(R.id.progress_bar_book_loading);
        }
    }

    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        //  name = sharedPreferences.getString("name", "");

        if(sharedPreferences.contains("favBooksSet"))
            favBooksIdsSet = sharedPreferences.getStringSet("favBooksSet", set);


    }

    private void updateFavoritesSharedPrefs(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putStringSet("favBooksSet", favBooksIdsSet);
        myEdit.apply();
    }

    public void updateFavorites(Book book){

        //Log.d(TAG, "updateFavorites>    user account id:   "+ currentUserAccountId);
        DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userAccountId);
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteBooks").document(book.getBookId());

        // isFav=true;

        documentReference.update("favoriteBooksIds", FieldValue.arrayRemove(book.getBookId())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

        favBooksIdsSet.remove(book.getBookId());
        updateFavoritesSharedPrefs();
        favoriteDocRef.delete();


    }

    private void updateFavorites(String bookId){
        DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userAccountId);
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteBooks").document(bookId);

        documentReference.update("favoriteBooksIds", FieldValue.arrayRemove(bookId));
        updateFavoritesSharedPrefs();
        favoriteDocRef.delete();
    }



}
