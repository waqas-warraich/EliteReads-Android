package com.titanreads.topreads.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

public class RecommendedBitcoinBooksAdapter extends FirestoreRecyclerAdapter<Book, RecommendedBitcoinBooksAdapter.RecommendedBitcoinBooksVH> {


    Context context;
    boolean verticalLayout;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecommendedBitcoinBooksAdapter(@NonNull FirestoreRecyclerOptions<Book> options, boolean verticalLayout, Context context) {
        super(options);
        this.verticalLayout = verticalLayout;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RecommendedBitcoinBooksVH holder, int position, @NonNull Book model) {

        if(model.getBookImageUrl()!=null)
        {
            holder.progressBarBookImageLoader.setVisibility(View.VISIBLE);
            Glide.with(context).load(model.getBookImageUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBarBookImageLoader.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBarBookImageLoader.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.ivBookImage);

            // Picasso.get().load(model.getBookImageUrl()).placeholder(R.drawable.horizontal_placeholder).into(holder.ivBookImage);
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

    }

    @NonNull
    @Override
    public RecommendedBitcoinBooksVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v;

        if(verticalLayout){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_verticle_layout,
                    parent, false);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,
                    parent, false);
        }


        return new RecommendedBitcoinBooksAdapter.RecommendedBitcoinBooksVH(v);
    }

    public class RecommendedBitcoinBooksVH extends RecyclerView.ViewHolder{

        private ImageView ivBookImage;
        ProgressBar progressBarBookImageLoader;

        public RecommendedBitcoinBooksVH(@NonNull View itemView) {
            super(itemView);
            ivBookImage = itemView.findViewById(R.id.iv_book_item);
            progressBarBookImageLoader = itemView.findViewById(R.id.progress_bar_book_loading);

        }
    }
}
