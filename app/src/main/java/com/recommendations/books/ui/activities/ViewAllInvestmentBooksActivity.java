package com.recommendations.books.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.recommendations.books.R;

import com.recommendations.books.databinding.ActivityViewAllInvestmentBooksBinding;
import com.recommendations.books.models.Book;
import com.recommendations.books.util.ItemOffsetDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class ViewAllInvestmentBooksActivity extends AppCompatActivity {

    ActivityViewAllInvestmentBooksBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewAllInvestmentBooksBinding.inflate(getLayoutInflater());

    }



    private void setBooksRecyclerView(){

        Query query=null;


/*
        query = FirebaseFirestore.getInstance()
                .collection("AllBookRecommenders").document(bookRecommenderPerson.getBookRecommenderId()).collection("RecommendedBooks");
*/

        RecyclerView recyclerView= binding.bookRecommenderBooksRecycler;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.book_item_offset);
        recyclerView.addItemDecoration(itemDecoration);


        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

      /*  commonBookAdapter = new CommonBookAdapter(options);
        recyclerView.setAdapter(commonBookAdapter);

        commonBookAdapter.startListening();*/





    }
}