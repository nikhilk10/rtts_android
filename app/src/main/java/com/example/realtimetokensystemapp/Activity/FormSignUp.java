package com.example.realtimetokensystemapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.R;
import com.example.realtimetokensystemapp.utils.Tools;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

public class FormSignUp extends AppCompatActivity {

    Button btnRegister, btnSignIn;
    ImageView imgBack;
    KProgressHUD kProgressHUD;
    AutoCompleteTextView etFullName, etUsername, etEmail;
    EditText etPassword, etConfirmPassword;
    AppCompatRadioButton rbFemale, rbMale;
    RadioGroup gender;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_sign_up);
        initToolbar();
        kProgressHUD = new KProgressHUD(this);
        etEmail = findViewById(R.id.etEmail);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        imgBack = findViewById(R.id.login_back_button);
        gender = findViewById(R.id.gender);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                  radioButton = (RadioButton) findViewById(checkedId);
//                                                  Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                              }
                                          }
        );

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUps(etEmail.getText().toString(), etConfirmPassword.getText().toString(), etFullName.getText().toString(), etUsername.getText().toString(), radioButton.getText().toString());
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
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

    private void SignUps(final String email, final String confirmpassword, final String fullname, final String username, final String gen) {
        kProgressHUD.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.SignUp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("onSugnUp: ", response);
//                Log.e("username & Pass ", username + " : " + password);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    if (message.equals("User was registered successfully!")) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(FormSignUp.this, "" + message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FormSignUp.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                    kProgressHUD.dismiss();

                } catch (JSONException e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(FormSignUp.this, "try again after some time", Toast.LENGTH_SHORT).show();
                }
                kProgressHUD.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(FormSignUp.this, "Please Fill Vaid Credintials,Try Again!", Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {


                return ConstantUrls.CONTENT_TYPE;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("Content-Type", "application/json");

                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", username);
                    jsonObject.put("email", email);
                    jsonObject.put("fullname", fullname);
                    jsonObject.put("gender", gen);
                    jsonObject.put("password", confirmpassword);

//
                    ret = jsonObject.toString();
                    Log.e("TAG", "LoginParams: " + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                }
                Log.e("TAG", "bytedata: " + ret.getBytes());

                return ret.getBytes();

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FormSignUp.this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);

    }

//    public void Signup(final String email, final String confirmpassword, final String fullname, final String username, final String gen) {
//        if (NetworkInfo.isNetworkAvailable(getApplicationContext())) {
//            kProgressHUD.show();
//            final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.SignUp
//                    , new com.android.volley.Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("TAG", "SignUp: " + response);
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String message = jsonObject.getString("message");
//                        if (message.equals("User was registered successfully!")) {
//                            startActivity(new Intent(getApplicationContext(), DashboardFinance.class));
//                            Toast.makeText(FormSignUp.this, "" + message, Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(FormSignUp.this, "" + message, Toast.LENGTH_SHORT).show();
//                        }
//
//                    } catch (Exception e) {
//                        kProgressHUD.dismiss();
//                        Log.e("TAG", "json error: " + e.getMessage());
//                        e.printStackTrace();
//                        Toast.makeText(FormSignUp.this, "try again after some time", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new com.android.volley.Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    kProgressHUD.dismiss();
//                    kProgressHUD.dismiss();
//                    Toast.makeText(FormSignUp.this, "Basic Network problem", Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return ConstantUrls.CONTENT_TYPE;
//                }
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//
//                    HashMap<String, String> hashMap = new HashMap<>();
////                hashMap.put("Content-Type", "application/json");
//
//                    return hashMap;
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//
//
//                    String ret = "";
//
//                    try {
//
//                        JSONObject jsonObject = new JSONObject();
//
//                        jsonObject.put("username", username);
//                        jsonObject.put("email", email);
//                        jsonObject.put("fullname", fullname);
//                        jsonObject.put("gender", gen);
//                        jsonObject.put("password", confirmpassword);
//                        ret = jsonObject.toString();
//                        Log.e("TAG", "signupparams: " + ret);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e("TAG", "ExeptionParamscatch: " + "Server error");
//                    }
//                    Log.e("TAG", "bytedata: " + ret.getBytes());
//                    return ret.getBytes();
//
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(FormSignUp.this);
//            RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//            stringRequest.setRetryPolicy(retryPolicy);
//            requestQueue.add(stringRequest);
//        }else {
//            Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }
}

//////////////-------------------*************************************////////////////////////////////////////
//
//[17:31, 5/13/2020] Nikhil Ssj: URL : /api/auth/signup
//
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//        .build();
//        MediaType mediaType = MediaType.parse("application/json,text/plain");
//        RequestBody body = RequestBody.create(mediaType, "{\n\"username\":\"nikhil6\",\n\"email\":\"nikhil6@gmail.com\",\n\"fullname\":\"nikill\",\n\"gender\":\"F\",\n\"password\":\"Nikhil@123\"\n}");
//        Request request = new Request.Builder()
//        .url(URL_HERE)
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Content-Type", "text/plain")
//        .build();
//        Response response = client.newCall(request).execute();
//        [17:32, 5/13/2020] Nikhil Ssj: URL : /api/auth/signin
//
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//        .build();
//        MediaType mediaType = MediaType.parse("application/json,text/plain");
//        RequestBody body = RequestBody.create(mediaType, "{\n\"username\":\"nikhil6\",\n\"password\":\"Nikhil@123\"\n}");
//        Request request = new Request.Builder()
//        .url(URL_HERE)
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Content-Type", "text/plain")
//        .build();
//        Response response = client.newCall(request).execute();
