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
import com.titanreads.topreads.ui.activities.ExploredRecommendersActivity;

import java.util.ArrayList;


public class ExploreRecommendersAdapter extends RecyclerView.Adapter<ExploreRecommendersAdapter.ExploreRecommendersVH>{


    Context context;

    final String TAG = "ExplrRecommendersAdptr";
    ArrayList<String> recommendersGenreList;
    private OnRecommenderGenreItemClickListener onRecommenderGenreItemClickListener;


    public ExploreRecommendersAdapter(Context context, ArrayList<String> list, OnRecommenderGenreItemClickListener onRecommenderGenreItemClickListener){



        this.context = context;
        recommendersGenreList = list;
        this.onRecommenderGenreItemClickListener = onRecommenderGenreItemClickListener;

    }

    @NonNull
    @Override
    public ExploreRecommendersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore_recommenders, parent, false);
        return new ExploreRecommendersVH(v, onRecommenderGenreItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ExploreRecommendersVH holder, int position) {


        holder.tvGenreName.setText(recommendersGenreList.get(position));

       /* holder.ivCrossFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onBookGenreItemClickListener.filterClickListener(holder.getAdapterPosition(), false);

            }
        });*/



        if(recommendersGenreList.get(position)=="Author")
            holder.backGroundImage.setBackgroundResource(R.drawable.authors_photos);

        else if(recommendersGenreList.get(position)=="Billionaire")
            holder.backGroundImage.setBackgroundResource(R.drawable.billionairs_grid);

        else if(recommendersGenreList.get(position)=="Investor")
            holder.backGroundImage.setBackgroundResource(R.drawable.investors_photo);

        else if(recommendersGenreList.get(position)=="Actor")
            holder.backGroundImage.setBackgroundResource(R.drawable.startups_books);

        else if(recommendersGenreList.get(position)=="Entrepreneur")
            holder.backGroundImage.setBackgroundResource(R.drawable.entrepreneurs_photo_grid_3);

        else if(recommendersGenreList.get(position)=="Executive")
            holder.backGroundImage.setBackgroundResource(R.drawable.executives_phot_grid);

        else if(recommendersGenreList.get(position)=="Engineer")
            holder.backGroundImage.setBackgroundResource(R.drawable.engineers_photo_gird_2);

        else if(recommendersGenreList.get(position)=="Filmmaker")
            holder.backGroundImage.setBackgroundResource(R.drawable.recommenders_collage_new);

        else if(recommendersGenreList.get(position)=="Founder")
            holder.backGroundImage.setBackgroundResource(R.drawable.tech_entrep);

        else if(recommendersGenreList.get(position)=="Businessperson")
            holder.backGroundImage.setBackgroundResource(R.drawable.businessperson_photos);

        else if (recommendersGenreList.get(position)=="Businesswoman")
            holder.backGroundImage.setBackgroundResource(R.drawable.business_woman_photo);

        else
            holder.backGroundImage.setBackgroundResource(R.drawable.photo_collage_all_recommenders);


    }

    @Override
    public int getItemCount() {

        int size = 0;
        if(recommendersGenreList !=null)
        size = recommendersGenreList.size();

        return size;

    }

    public class ExploreRecommendersVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvGenreName;
        int position;
        ImageView backGroundImage;

        OnRecommenderGenreItemClickListener onRecommenderGenreItemClickListener;

        ImageView ivCrossFilters;


        public ExploreRecommendersVH(@NonNull View itemView, OnRecommenderGenreItemClickListener onRecommenderGenreItemClickListener) {
            super(itemView);

            tvGenreName = itemView.findViewById(R.id.tv_item_recommenders_genre);
            backGroundImage = itemView.findViewById(R.id.iv_recommender_item);

           /* tvFilterName = itemView.findViewById(R.id.tv_item_recommenders_genre);
            ivCrossFilters = itemView.findViewById(R.id.iv_cross_quick_filters_item);*/

            position = getAdapterPosition();
            this.onRecommenderGenreItemClickListener = onRecommenderGenreItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



            Log.v(TAG, recommendersGenreList.get(getAdapterPosition()));
            Intent intent = new Intent(context, ExploredRecommendersActivity.class);
            intent.putExtra("fireStoreId", recommendersGenreList.get(getAdapterPosition()));
            intent.putExtra("recommendersGenre", recommendersGenreList.get(getAdapterPosition()));
            intent.putExtra("toolbarTitle", recommendersGenreList.get(getAdapterPosition()));
            context.startActivity(intent);



        }
    }


    public interface OnRecommenderGenreItemClickListener {

        void genreClickListener(int filterItemPosition, boolean clearList);

    }



}
