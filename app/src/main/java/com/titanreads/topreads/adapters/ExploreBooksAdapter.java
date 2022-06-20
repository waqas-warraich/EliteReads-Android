package com.titanreads.topreads.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titanreads.topreads.R;
import com.titanreads.topreads.ui.activities.ExploredBooksActivity;

import java.util.ArrayList;


public class ExploreBooksAdapter extends RecyclerView.Adapter<ExploreBooksAdapter.ExploreBooksVH>{


    Context context;
    final String TAG = "ExploreBooksAdapter";

    ArrayList<String> recommendersGenreList;
    private OnBookGenreItemClickListener onBookGenreItemClickListener;


    public ExploreBooksAdapter(Context context, ArrayList<String> list, OnBookGenreItemClickListener onBookGenreItemClickListener){



        this.context = context;
        recommendersGenreList = list;
        this.onBookGenreItemClickListener = onBookGenreItemClickListener;

    }

    @NonNull
    @Override
    public ExploreBooksVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore_books, parent, false);
        return new ExploreBooksVH(v, onBookGenreItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ExploreBooksVH holder, int position) {


        holder.tvGenreName.setText(recommendersGenreList.get(position));




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, recommendersGenreList.get(holder.getAdapterPosition()));
                Intent intent = new Intent(context, ExploredBooksActivity.class);
                intent.putExtra("fireStoreId", recommendersGenreList.get(holder.getAdapterPosition()));
                intent.putExtra("BooksGenre", recommendersGenreList.get(holder.getAdapterPosition()));
                intent.putExtra("toolbarTitle", recommendersGenreList.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });

        if(recommendersGenreList.get(position)=="Technology")
            holder.backGroundImage.setBackgroundResource(R.drawable.tech_books);

        else if(recommendersGenreList.get(position)=="Science")
            holder.backGroundImage.setBackgroundResource(R.drawable.science_books);

        else if(recommendersGenreList.get(position)=="Startups")
            holder.backGroundImage.setBackgroundResource(R.drawable.startups_books);



        else if(recommendersGenreList.get(position)=="Science Fiction")
            holder.backGroundImage.setBackgroundResource(R.drawable.scifi_books);

        else if(recommendersGenreList.get(position)=="Science Fiction Fantasy")
            holder.backGroundImage.setBackgroundResource(R.drawable.tech_books);

        else if(recommendersGenreList.get(position)=="Childrens")
            holder.backGroundImage.setBackgroundResource(R.drawable.childs_books);

        else if(recommendersGenreList.get(position)=="Literature")
            holder.backGroundImage.setBackgroundResource(R.drawable.litrature_books);

        else if(recommendersGenreList.get(position)=="Nonfiction")
            holder.backGroundImage.setBackgroundResource(R.drawable.self_help_books);

        else if(recommendersGenreList.get(position)=="Novels")
            holder.backGroundImage.setBackgroundResource(R.drawable.novels_books);

        else if (recommendersGenreList.get(position)=="Fiction")
            holder.backGroundImage.setBackgroundResource(R.drawable.fiction_books);

        else
            holder.backGroundImage.setBackgroundResource(R.drawable.books_collage);


       /* holder.ivCrossFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onBookGenreItemClickListener.filterClickListener(holder.getAdapterPosition(), false);

            }
        });*/

    }

    @Override
    public int getItemCount() {

        int size = 0;
        if(recommendersGenreList !=null)
        size = recommendersGenreList.size();

        return size;

    }

    public class ExploreBooksVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvGenreName;
        int position;

        ImageView backGroundImage;
        OnBookGenreItemClickListener onBookGenreItemClickListener;

        ImageView ivCrossFilters;


        public ExploreBooksVH(@NonNull View itemView, OnBookGenreItemClickListener onBookGenreItemClickListener) {
            super(itemView);

            tvGenreName = itemView.findViewById(R.id.tv_item_recommenders_genre);
            backGroundImage = itemView.findViewById(R.id.iv_book_item_explore_books);

           /* tvFilterName = itemView.findViewById(R.id.tv_item_recommenders_genre);
            ivCrossFilters = itemView.findViewById(R.id.iv_cross_quick_filters_item);*/

            position = getAdapterPosition();
            this.onBookGenreItemClickListener = onBookGenreItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //startActivity( getAdapterPosition());


        }
    }


    public interface OnBookGenreItemClickListener {

        void genreClickListener(int filterItemPosition, boolean clearList);

    }


    private void startActivity(int position){

        String toolBarTitle, fireStoreId, BooksGenre;

       /* for(int i= 0; i<=recommendersGenreList.size(); i++){

            if(i == 0){

            }else if(i==1){

            }


        }*/


    }

}
