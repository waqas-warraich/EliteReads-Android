package com.titanreads.topreads.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.titanreads.topreads.R;
import com.titanreads.topreads.models.RecommendationQuote;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

public class QuotesAdapter extends FirestoreRecyclerAdapter<RecommendationQuote, QuotesAdapter.QuotesAdapterVH> {

    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public QuotesAdapter(@NonNull FirestoreRecyclerOptions<RecommendationQuote> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuotesAdapterVH holder, int position, @NonNull RecommendationQuote model) {

        if(model.getRecommenderImageUrl()!=null)
        {
            if(model.getRecommenderImageUrl().length()>10)
                Glide.with(context)
                        .load(model.getRecommenderImageUrl())
                        .centerCrop()
                        .placeholder(R.drawable.book_loader)
                        .into(holder.civRecommender);
        }

        holder.tvName.setText(model.getRecommenderName());
        holder.tvQuote.setText(model.getQuote());

        holder.tvQuoteSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(model.getQuoteSourceLink()!=null&& model.getQuoteSourceLink().length()> 10)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getQuoteSourceLink()));
                    context.startActivity(browserIntent);
                }else
                    Toast.makeText(context, "Source not available", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @NonNull
    @Override
    public QuotesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommender_quote,
                parent, false);

        return new QuotesAdapter.QuotesAdapterVH(v);
    }

    public class QuotesAdapterVH extends RecyclerView.ViewHolder{

        private CircularImageView civRecommender;
        private TextView tvName;
        private TextView tvQuote;
        private TextView tvQuoteSource;


        public QuotesAdapterVH(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_quoteAuthorName);
            tvQuote = itemView.findViewById(R.id.tv_quoteTextBody);
            tvQuoteSource = itemView.findViewById(R.id.tvRecommenderQuoteSource);
            civRecommender = itemView.findViewById(R.id.civ_recommender_quoteItem);
        }
    }
}
