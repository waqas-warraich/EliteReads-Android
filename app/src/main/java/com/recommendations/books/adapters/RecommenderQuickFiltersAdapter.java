package com.recommendations.books.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.recommendations.books.R;
import java.util.ArrayList;



public class RecommenderQuickFiltersAdapter extends RecyclerView.Adapter<RecommenderQuickFiltersAdapter.RecommenderFiltersVH>{



    ArrayList<String> quickFiltersList;
    private OnFiltersItemClickListener onFiltersItemClickListener;


    public RecommenderQuickFiltersAdapter(ArrayList<String> list, OnFiltersItemClickListener onFiltersItemClickListener){



        quickFiltersList = list;
        this.onFiltersItemClickListener = onFiltersItemClickListener;

    }

    @NonNull
    @Override
    public RecommenderFiltersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_recommenders, parent, false);
        return new RecommenderFiltersVH(v, onFiltersItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecommenderFiltersVH holder, int position) {
        holder.tvFilterName.setText(quickFiltersList.get(position));

        holder.ivCrossFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onFiltersItemClickListener.filterClickListener(holder.getAdapterPosition(), false);

            }
        });

    }

    @Override
    public int getItemCount() {

        int size = 0;
        if(quickFiltersList!=null)
        size = quickFiltersList.size();

        return size;

    }

    public class RecommenderFiltersVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvFilterName;
        int position;

        OnFiltersItemClickListener onFiltersItemClickListener;

        ImageView ivCrossFilters;


        public RecommenderFiltersVH(@NonNull View itemView, OnFiltersItemClickListener onFiltersItemClickListener) {
            super(itemView);

           /* tvFilterName = itemView.findViewById(R.id.tv_filter_name_quick_filters_item);
            ivCrossFilters = itemView.findViewById(R.id.iv_cross_quick_filters_item);*/

            position = getAdapterPosition();
            this.onFiltersItemClickListener = onFiltersItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


    public interface OnFiltersItemClickListener {

        void filterClickListener(int filterItemPosition, boolean clearList);

    }



}
