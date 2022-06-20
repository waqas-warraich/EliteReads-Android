package com.titanreads.topreads.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.titanreads.topreads.R;
import com.titanreads.topreads.databinding.ActivityViewAllBiographyBooksBinding;

public class ViewAllBiographyBooksActivity extends AppCompatActivity {

    ActivityViewAllBiographyBooksBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_biography_books);
    }


/*
    private void setBooksRecyclerView(){

        Query query=null;


        query = FirebaseFirestore.getInstance()
                .collection("AllBookRecommenders").document(bookRecommenderPerson.getBookRecommenderId()).collection("RecommendedBooks");


        recyclerView.addItemDecoration(itemDecoration);


        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        commonBookAdapter = new CommonBookAdapter(options);
        recyclerView.setAdapter(commonBookAdapter);

        commonBookAdapter.startListening();





    }
*/

}