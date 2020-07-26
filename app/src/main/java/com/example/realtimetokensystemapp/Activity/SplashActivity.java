package com.example.realtimetokensystemapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 20;
    int SPLASH_TIME_OUT = 1500;
    ImageView img_logo;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img_logo = (ImageView) findViewById(R.id.img_logo);

        Glide.with(this).load(R.drawable.logo).into(img_logo);
        splashScreen();
    }

    public void splashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Sharedpref.getBool(SplashActivity.this, Sharedpref.islogin)) {
                    startActivity(new Intent(SplashActivity.this, DashboardFinance.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

}
