package com.recommendations.books.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.recommendations.books.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;


public class AboutAppActivity extends AppCompatActivity {

    TextView tvOpenSourceBtn;
    TextView tvFreeGraphicsAttributionsBtn;
    ImageButton ibBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        tvOpenSourceBtn = findViewById(R.id.tv_open_source_btn);
        tvFreeGraphicsAttributionsBtn = findViewById(R.id.tv_graphics_attribution_btn);
        ibBack = findViewById(R.id.ib_back_aboutAppActivity);


        tvOpenSourceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutAppActivity.this, OssLicensesMenuActivity.class);
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.custom_license_title));
                startActivity(intent);
            }
        });

        tvFreeGraphicsAttributionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(AboutAppActivity.this, AttributionActivity.class);
                startActivity(intent);*/
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}