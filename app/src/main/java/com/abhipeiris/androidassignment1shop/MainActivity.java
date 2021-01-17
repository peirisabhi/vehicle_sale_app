package com.abhipeiris.androidassignment1shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView welcomeLottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeLottie = findViewById(R.id.welcomeLottie);
        welcomeLottie.animate().translationY(3000).setDuration(500).setStartDelay(1800);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final  Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2200);

//        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
//        startActivity(intent);
//        finish();

    }
}