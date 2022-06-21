package com.recommendations.books.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.recommendations.books.MainActivity;
import com.recommendations.books.R;


public class SplashscreenActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameTv,name2Tv;
    long animTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
      //  overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);

/*
        imageView = findViewById(R.id.iv_logo_splash);
        // name2Tv = findViewById(R.id.tv_splash_name2);
        nameTv = findViewById(R.id.tv_splash_name);
*/

       /* ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView,"y",400f);
        //ObjectAnimator animatorname = ObjectAnimator.ofFloat(nameTv,"x",310f);
        animatorY.setDuration(animTime);
       // animatorname.setDuration(animTime);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
                //  intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                finish();
            }
        }, 2000);




    }
}