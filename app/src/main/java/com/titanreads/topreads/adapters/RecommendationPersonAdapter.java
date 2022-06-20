package com.titanreads.topreads.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.titanreads.topreads.R;
import com.titanreads.topreads.models.BookRecommenderPerson;
import com.titanreads.topreads.ui.activities.ViewRecommenderActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;


public class RecommendationPersonAdapter extends FirestoreRecyclerAdapter<BookRecommenderPerson, RecommendationPersonAdapter.RecommenderPersonVH> {

    Context context;
    boolean verticalLayout = true;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    DocumentReference documentReference, booksReference;

    public RecommendationPersonAdapter(@NonNull FirestoreRecyclerOptions<BookRecommenderPerson> options, boolean verticalLayout) {
        super(options);

        this.verticalLayout = verticalLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull RecommenderPersonVH holder, int position, @NonNull BookRecommenderPerson model) {


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

        holder.tvBooksCount.setText(model.getBooksCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // documentReference = db.collection("BookRecommenders").document(model.getBookRecommenderId());

              /*  documentReference.set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                       // binding.tvBookRecommenderAddedSuccessfully.setVisibility(View.VISIBLE);
                         Toast.makeText(context, "Book Recommender Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Book Recommender Updating Failure", Toast.LENGTH_SHORT).show();
                    }
                });*/


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

    }

    @NonNull
    @Override
    public RecommenderPersonVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View v;
        if(verticalLayout){
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_recommender_verticle,
                    parent, false);

        }else{
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_recommender,
                    parent, false);
        }

        return new RecommenderPersonVH(v);
    }

    public class RecommenderPersonVH extends RecyclerView.ViewHolder {


        private CircularImageView civRecommenderImage;
        private TextView tvName;
        private TextView tvBooksCount;


        public RecommenderPersonVH(@NonNull View itemView) {
            super(itemView);

            civRecommenderImage = itemView.findViewById(R.id.civ_profile_image_view_tutor_profile);
            tvName = itemView.findViewById(R.id.tv_book_recommender_name);
            tvBooksCount = itemView.findViewById(R.id.tv_recommended_books_count);
        }
    }
}
