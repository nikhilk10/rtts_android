package com.example.realtimetokensystemapp.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.realtimetokensystemapp.utils.Tools;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

public class DashboardFinance extends AppCompatActivity {

    private TabLayout tab_layout;
    private NestedScrollView nested_scroll_view;
    LinearLayout cvCreateHost, cvJoinHost,cvMyJoinedAt, cvFaq;
    Dialog dialog;
    AppCompatEditText etDisplayName, etLimit, etAddress, etHostId;
    Button btnSubmit, btnCancel;
    KProgressHUD kProgressHUD;
    String device_id = "";

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_finance);
        kProgressHUD = new KProgressHUD(this);
        Sharedpref.setBool(DashboardFinance.this, Sharedpref.islogin, true);
        initComponent();
        onClick();
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("TAG", "newToken1:- "+newToken);
                Log.e("TAG", "DeviceID:- "+Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                tokensync(newToken, device_id);
            }
        });


    }




    private void onClick() {
        cvCreateHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Sharedpref.getCreatedHost(DashboardFinance.this).equals("HostCreated")) {
                    startActivity(new Intent(DashboardFinance.this, Hostlistactivity.class));
//                } else {
//                    dialog();
//                }


            }
        });


        cvJoinHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = new Dialog(DashboardFinance.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.joinhostdialog);
//                dialog.setCancelable(false);
//                etHostId=dialog.findViewById(R.id.etHostId);
//                btnSubmit=dialog.findViewById(R.id.btnSubmit);
//                btnCancel=dialog.findViewById(R.id.btnCancel);
//                dialog.show();
//                btnSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(DashboardFinance.this, "Host Created successfully", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                        JoinHost(etHostId.getText().toString());
//                        //api for submit created host.
//                    }
//                });
//
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });

                Intent i = new Intent(getApplicationContext(), JoinhostActivity.class);
                startActivity(i);

            }
        });
        cvMyJoinedAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//user profile need to show.
                startActivity(new Intent(getApplicationContext(),MeEnrolledatActivity.class));



            }
        });
        cvFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //work in progress

            }
        });
    }

    public void dialog() {
        dialog = new Dialog(DashboardFinance.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.createhostdialog);
        dialog.setCancelable(false);
        etDisplayName = dialog.findViewById(R.id.etDisplayName);
        etLimit = dialog.findViewById(R.id.etLimit);
        etAddress = dialog.findViewById(R.id.etAddress);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                CreateHost(etDisplayName.getText().toString(), etLimit.getText().toString(), etAddress.getText().toString());

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    public void Logout(final String DeviceID) {
        kProgressHUD.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.logout, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "LogoutResp: " + response);
                try {
                    Sharedpref.clear(getApplicationContext());
                    Intent i = new Intent(DashboardFinance.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(DashboardFinance.this, "try again after some time", Toast.LENGTH_SHORT).show();
                    kProgressHUD.dismiss();

                }
                finally {
                    Sharedpref.clear(getApplicationContext());
                    Intent i = new Intent(DashboardFinance.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                kProgressHUD.dismiss();

            }
        },new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kProgressHUD.dismiss();
                Log.e("TAG", "volley error: " + error.getMessage());
                error.printStackTrace();

//                Toast.makeText(DashboardFinance.this, "try again after some time", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token", Sharedpref.getAccessToken(DashboardFinance.this));
//                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(Hostlistactivity.this));
                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceID", DeviceID);
                    ret = jsonObject.toString();
                    Log.e("TAG", "deviceID: " + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                }
//                Log.e("TAG", "bytedata: " + ret.getBytes());
                return ret.getBytes();

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardFinance.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

    public void CreateHost(final String displayname, final String limit, final String address) {
        kProgressHUD.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.createhost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "HostCreatedResponce: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                    if (jsonObject1.has("limit")) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("enrolledClients");
                        String limit = jsonObject1.getString("limit");
                        String address = jsonObject1.getString("address");
                        String status = jsonObject1.getString("status");
                        String displayName = jsonObject1.getString("displayName");
                        String hostCreator = jsonObject1.getString("hostCreator");
                        String hostID = jsonObject1.getString("hostID");
                        boolean showBySearch = jsonObject1.getBoolean("showBySearch");
                        Sharedpref.setData(DashboardFinance.this, Sharedpref.hostID, hostID);
                        Sharedpref.setData(DashboardFinance.this, Sharedpref.limit, limit);
                        Sharedpref.setData(DashboardFinance.this, Sharedpref.addresse, address);
                        Sharedpref.setData(DashboardFinance.this, Sharedpref.hostCreator, hostCreator);
                        Sharedpref.setData(DashboardFinance.this, Sharedpref.status, status);
                        Sharedpref.setData(DashboardFinance.this, Sharedpref.displayName, displayName);
                        Sharedpref.setCreatedHost(DashboardFinance.this, "HostCreated");
                        startActivity(new Intent(getApplicationContext(), Hostlistactivity.class));
                        Toast.makeText(DashboardFinance.this, "HostCreated Successfully!", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(DashboardFinance.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(DashboardFinance.this, "try again after some time", Toast.LENGTH_SHORT).show();
                    kProgressHUD.dismiss();

                }
                kProgressHUD.dismiss();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Toast.makeText(DashboardFinance.this, "" + error, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Error1:" + error.getMessage());
                    kProgressHUD.dismiss();
                    //handler error 401 unauthorized from here
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token", Sharedpref.getAccessToken(DashboardFinance.this));
//                hashMap.put("Content-Type", "application/json; charset=utf-8");

                Log.e("TAG", "Token:-" + Sharedpref.getAccessToken(DashboardFinance.this));
                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("limit", limit);
                    jsonObject.put("address", address);
                    jsonObject.put("status", "ON");
                    jsonObject.put("displayName", displayname);
                    jsonObject.put("showBySearch", true);
//
                    ret = jsonObject.toString();
                    Log.e("TAG", "CreateHostparam: " + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                }
//                Log.e("TAG", "bytedata: " + ret.getBytes());
                return ret.getBytes();

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardFinance.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

    public void tokensync(final String token, final String deviceID) {
        kProgressHUD.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.tokensync
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "tokensyncresp: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.contains("Token saved successfully!")) {
                        Toast.makeText(DashboardFinance.this, "" + message, Toast.LENGTH_SHORT).show();
                        kProgressHUD.dismiss();

                    } else {
                        kProgressHUD.dismiss();
                        Toast.makeText(DashboardFinance.this, "" + message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(DashboardFinance.this, "try again after some time", Toast.LENGTH_SHORT).show();
                    kProgressHUD.dismiss();

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kProgressHUD.dismiss();
                Log.e("TAG", "volley error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(DashboardFinance.this, "try again after some time", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token", Sharedpref.getAccessToken(DashboardFinance.this));
//                hashMap.put("Content-Type", "application/json; charset=utf-8");

//                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(DashboardFinance.this));
                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("token", token);
                    jsonObject.put("deviceID", deviceID);

                    ret = jsonObject.toString();
                    Log.e("TAG", "CreateHostparam: " + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                }
//                Log.e("TAG", "bytedata: " + ret.getBytes());
                return ret.getBytes();

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardFinance.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

    private void initComponent() {

        cvCreateHost = findViewById(R.id.cvCreateHost);
        cvJoinHost = findViewById(R.id.cvJoinHost);
        cvMyJoinedAt = findViewById(R.id.cvMyJoinedAt);
        cvFaq = findViewById(R.id.cvFaq);
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);

        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_home), 0);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_data_usage), 1);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 2);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_menu), 3);

        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.blue_grey_400), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.blue_grey_400), PorterDuff.Mode.SRC_IN);
                onTabClicked(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabClicked(tab);
            }
        });

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }


    private void onTabClicked(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Sharedpref.clear(getApplicationContext());
                Intent i = new Intent(DashboardFinance.this, LoginActivity.class);
                startActivity(i);
                finish();

                Logout(device_id);

//                Toast.makeText(getApplicationContext(), "Statistics", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
//                Toast.makeText(getApplicationContext(), "Communication", Toast.LENGTH_SHORT).show();
                break;
            case 3:
//                Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
                break;
        }
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