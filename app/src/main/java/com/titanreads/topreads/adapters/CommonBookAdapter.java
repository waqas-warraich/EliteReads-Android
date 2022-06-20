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

public class CommonBookAdapter extends FirestoreRecyclerAdapter<Book, CommonBookAdapter.CommonBookAdapterVH> {


    Context context;

    public CommonBookAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommonBookAdapterVH holder, int position, @NonNull Book model) {

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
    public CommonBookAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_verticle_layout,
                parent, false);

        return new CommonBookAdapter.CommonBookAdapterVH(v);
    }

    public class CommonBookAdapterVH extends RecyclerView.ViewHolder{

        private ImageView ivBookImage;
        ProgressBar progressBarBookImageLoader;

        public CommonBookAdapterVH(@NonNull View itemView) {
            super(itemView);

            ivBookImage = itemView.findViewById(R.id.iv_book_item);
            progressBarBookImageLoader = itemView.findViewById(R.id.progress_bar_book_loading);
        }
    }
}
