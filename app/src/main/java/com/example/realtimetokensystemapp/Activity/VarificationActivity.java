package com.example.realtimetokensystemapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.realtimetokensystemapp.R;
import com.example.realtimetokensystemapp.utils.Tools;
import com.google.android.material.textfield.TextInputEditText;

public class VarificationActivity extends AppCompatActivity {
    TextInputEditText etVCode;
    AppCompatButton btnContinue,btnResend;
    LinearLayout lnrAlreadyHaveAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);
        initToolbar();
        etVCode=findViewById(R.id.etVCode);
        btnResend=findViewById(R.id.btnResend);
        btnContinue=findViewById(R.id.btnContinue);
        lnrAlreadyHaveAc=findViewById(R.id.lnrAlreadyHaveAc);

        ////take code from etVcode...

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
////api for resend code
            }
        });  btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VarificationActivity.this,DashboardFinance.class));
                ////api for checking the code is correct or not if correct go to dashboard.///
                ///else try again toast or resend ..

            }
        });
        lnrAlreadyHaveAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

