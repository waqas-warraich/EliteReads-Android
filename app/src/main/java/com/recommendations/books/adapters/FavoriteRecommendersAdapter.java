package com.recommendations.books.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.recommendations.books.R;
import com.recommendations.books.models.BookRecommenderPerson;
import com.recommendations.books.ui.activities.ViewRecommenderActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashSet;
import java.util.Set;


public class FavoriteRecommendersAdapter extends FirestoreRecyclerAdapter<BookRecommenderPerson, FavoriteRecommendersAdapter.FavoriteRecommendersVH> {

    Context context;
    boolean verticalLayout = true;
    Set<String> favRecommendersIdsSet;
    Set <String>set = new HashSet();
    String userAccountId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FavoriteRecommendersAdapter(@NonNull FirestoreRecyclerOptions<BookRecommenderPerson> options, boolean verticalLayout, Context context) {
        super(options);

        this.context = context;
        this.verticalLayout = verticalLayout;
        readUserSharedPrefData();

    }

    @Override
    protected void onBindViewHolder(@NonNull FavoriteRecommendersVH holder, int position, @NonNull BookRecommenderPerson model) {


        if(model.getImageUrl()!=null)
        {
            if(model.getImageUrl().length()>10)
                Glide.with(context)
                        .load(model.getImageUrl())
                        .centerCrop()
                        .placeholder(R.drawable.book_loader)
                        .into(holder.civRecommenderImage);
        }

        holder.tvName.setText(model.getName());

        if(model.getProfession()!=null)
            holder.tvRecommenderProfession.setText(model.getProfession());
        else{
            holder.tvRecommenderProfession.setText("N/A");
        }

        if(model.getRecommenderKnownFor()!=null)
            holder.tvRecommenderKnownFor.setText(model.getRecommenderKnownFor());
        else{
            holder.tvRecommenderKnownFor.setText("N/A");
        }

        if(model.getRecommenderCountry()!=null)
            holder.tvRecommenderCountry.setText(model.getRecommenderCountry());
        else{
            holder.tvRecommenderCountry.setText("N/A");
        }

      /*  holder.tvRecommenderProfession.setText(model.getProfession());
        holder.tvRecommenderCountry.setText(model.getRecommenderCountry());
        holder.tvRecommenderKnownFor.setText(model.getRecommenderKnownFor());*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              /*  DocumentReference documentReference = db.collection("AllBookRecommenders").document(model.getBookRecommenderId());

                documentReference.set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Document added successfully", Toast.LENGTH_SHORT).show();

                    }
                });*/


                Bundle bundle = new Bundle();
                bundle.putSerializable("BookRecommenderObject",model);

                Intent intent = new Intent(context, ViewRecommenderActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,model.getName());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Find all recommended books by "+model.getName()+" and learn or review the key takeaways for free on EliteReads: https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavorites(model);
            }
        });

    }

    @NonNull
    @Override
    public FavoriteRecommendersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_recommender,
                parent, false);

        return new FavoriteRecommendersVH(v);
    }

    public class FavoriteRecommendersVH extends RecyclerView.ViewHolder {


        private CircularImageView civRecommenderImage;
        private ImageView ivShare;
        private ImageView ivDelete;
        private TextView tvName;
        private TextView tvRecommenderProfession;
        private TextView tvRecommenderCountry;
        private TextView tvRecommenderKnownFor;

       // private TextView tvBooksCount;


        public FavoriteRecommendersVH(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_fav_recommender_name_item);
            civRecommenderImage = itemView.findViewById(R.id.civ_recommender_profile_image);
            ivDelete = itemView.findViewById(R.id.iv_remove_favorite_recommender_item);
            ivShare = itemView.findViewById(R.id.iv_share_favorite_recommender_item);
            tvRecommenderProfession = itemView.findViewById(R.id.tv_recommender_profession_value);
            tvRecommenderCountry = itemView.findViewById(R.id.tv_recommender_country_value);
            tvRecommenderKnownFor = itemView.findViewById(R.id.tv_recommender_knowFor_value_favItem);
           // tvBooksCount = itemView.findViewById(R.id.tv_recommended_books_count);
        }
    }


    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        //  name = sharedPreferences.getString("name", "");
        if(sharedPreferences.contains("favRecommendersSet"))
            favRecommendersIdsSet = sharedPreferences.getStringSet("favRecommendersSet", set);


    }

    private void updateFavoritesSharedPrefs(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userAccountData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putStringSet("favRecommendersSet", favRecommendersIdsSet);
        myEdit.apply();
    }


    public void updateFavorites(BookRecommenderPerson recommender){

        //Log.d(TAG, "updateFavorites>    user account id:   "+ currentUserAccountId);
        DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userAccountId);
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteRecommenders").document(recommender.getBookRecommenderId());

        // isFav=true;

        documentReference.update("favoriteRecommendersIds", FieldValue.arrayRemove(recommender.getBookRecommenderId())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

        favRecommendersIdsSet.remove(recommender.getBookRecommenderId());
        updateFavoritesSharedPrefs();
        favoriteDocRef.delete();
    }


    private void updateFavorites(String recommenderId){
        DocumentReference documentReference = db.collection("EliteReadsAndroidUserAccounts").document(userAccountId);
        DocumentReference favoriteDocRef= db.collection("EliteReadsAndroidUserAccounts").document(userAccountId).collection("FavoriteRecommenders").document(recommenderId);

        documentReference.update("favoriteRecommendersIds", FieldValue.arrayRemove(recommenderId));
        updateFavoritesSharedPrefs();
        favoriteDocRef.delete();
    }



}
