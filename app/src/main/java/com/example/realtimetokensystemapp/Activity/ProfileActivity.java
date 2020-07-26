package com.example.realtimetokensystemapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.R;

public class ProfileActivity extends AppCompatActivity {
    TextView tvFullName,tvUsername,tvGender,tvEmail,tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvFullName=findViewById(R.id.tvFullName);
        tvUsername=findViewById(R.id.tvUsername);
        tvGender=findViewById(R.id.tvGender);
        tvEmail=findViewById(R.id.tvEmail);
        tvAddress=findViewById(R.id.tvAddress);

        tvFullName.setText(Sharedpref.getData(getApplicationContext(),Sharedpref.fullname));
        tvUsername.setText(Sharedpref.getData(getApplicationContext(),Sharedpref.name));
        tvGender.setText(Sharedpref.getData(getApplicationContext(),Sharedpref.gender));
        tvEmail.setText(Sharedpref.getData(getApplicationContext(),Sharedpref.emailid));
        tvAddress.setText(Sharedpref.getData(getApplicationContext(),Sharedpref.addresse));
    }
}
