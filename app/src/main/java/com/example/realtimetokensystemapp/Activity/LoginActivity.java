package com.example.realtimetokensystemapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnForgetPass, btnRegisterHere;
    KProgressHUD kProgressHUD;
    ImageView login_back_button;
    TextInputEditText etUsername,etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        kProgressHUD = new KProgressHUD(LoginActivity.this);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgetPass = findViewById(R.id.btnForgetPass);
        login_back_button = findViewById(R.id.login_back_button);
        btnRegisterHere = findViewById(R.id.btnRegisterHere);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////enter email id here activity or dialog.
            }
        });
        btnRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), FormSignUp.class);
                startActivity(i);

            }
        });
        login_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Login(Objects.requireNonNull(etUsername.getText()).toString(),etPassword.getText().toString());
            }
        });
    }
    private void Login(final String username, final String password) {
        kProgressHUD.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("onLogin: ", response);
                Log.e("username & Pass ", username + " : " + password);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("roles");
                    String id = jsonObject.getString("id");
                    String role = jsonArray.getString(0);
                    String name = jsonObject.getString("username");
                    String fullname = jsonObject.getString("fullname");
                    String email = jsonObject.getString("email");
                    String gender = jsonObject.getString("gender");
                    String accessToken = jsonObject.getString("accessToken");
//                    String address = jsonObject.getString("address");
                    Sharedpref.setData(LoginActivity.this, Sharedpref.id, id);
                    Sharedpref.setData(LoginActivity.this, Sharedpref.name, name);
                    Sharedpref.setData(LoginActivity.this, Sharedpref.emailid, email);
                    Sharedpref.setData(LoginActivity.this, Sharedpref.gender, gender);
                    Sharedpref.setData(LoginActivity.this, Sharedpref.fullname, fullname);
//                    Sharedpref.setData(LoginActivity.this, Sharedpref.addresse, address);
                    Log.e("TAG","AccessToken: "+ accessToken);
                    Log.e("TAG","fullname: "+ fullname);
                    Sharedpref.setAccessToken(LoginActivity.this,accessToken);
                    if (role.equals("ROLE_USER")) {

                        startActivity(new Intent(getApplicationContext(),DashboardFinance.class));
                        Toast.makeText(LoginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(LoginActivity.this, "Failed to login, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                    kProgressHUD.dismiss();

                } catch (JSONException e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "try again after some time", Toast.LENGTH_SHORT).show();
                }
                kProgressHUD.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(LoginActivity.this, "Please Fill Vaid Credintials,Try Again!" , Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        }) { @Override
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
                    jsonObject.put("password", password);
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

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);

    }



    @Override
    public void onBackPressed() {
        finishAffinity();
    }
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

