package com.example.realtimetokensystemapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.realtimetokensystemapp.Adapter.HostListAdapter;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.Models.HostListModel;
import com.example.realtimetokensystemapp.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.realtimetokensystemapp.Constant.ConstantUrls.myhosts;

public class Hostlistactivity extends AppCompatActivity {
//

    KProgressHUD kProgressHUD;
    Dialog dialog,dialog1;
    RecyclerView recyclerhostlist;

    HostListAdapter hostListAdapter;
    LinearLayout lnrCreateHost;
    ArrayList<HostListModel> hostListModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_list);
        hostListModelArrayList = new ArrayList<>();
        recyclerhostlist = findViewById(R.id.recyclerhostlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Hostlistactivity.this);
        recyclerhostlist.setLayoutManager(layoutManager);
        recyclerhostlist.setItemAnimator(new DefaultItemAnimator());

        init();
        onClicks();
        myhosts(Sharedpref.getAccessToken(Hostlistactivity.this));

    }

    public void myhosts(final String accessToken) {
        kProgressHUD.show();
        hostListModelArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, myhosts
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "listHost: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HostListModel hostListModel = new HostListModel();
                        hostListModel.setAddress(jsonObject1.getString("address"));
                        hostListModel.setStatus(jsonObject1.getString("status"));
                        hostListModel.setDisplayName(jsonObject1.getString("displayName"));
                        hostListModel.setLimit(jsonObject1.getString("limit"));
                        hostListModel.setHostID(jsonObject1.getString("hostID"));
                        hostListModelArrayList.add(hostListModel);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("enrolledClients");
//                        for (int j = 0; j < jsonArray1.length(); j++) {
//                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
//                            hostListModel.setClientId(jsonObject2.getString("clientID"));
//                            hostListModel.setTs(jsonObject2.getString("ts"));
//                            hostListModel.setStatus(jsonObject2.getString("status"));
//                            hostListModel.setPosition(jsonObject2.getString("position"));
//
//                        }
                    }
                    hostListAdapter = new HostListAdapter(Hostlistactivity.this, hostListModelArrayList);
                    recyclerhostlist.setAdapter(hostListAdapter);
                    hostListAdapter.notifyDataSetChanged();
                    kProgressHUD.dismiss();

                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(Hostlistactivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kProgressHUD.dismiss();
                Log.e("TAG", "volley error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(Hostlistactivity.this, "try again after some time", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token",accessToken );
                Log.e("TAG","Token:-"+accessToken);
                return hashMap; }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("query", hostId);
                    ret = jsonObject.toString();
                    Log.e("TAG", "listparamsjoins: " + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                }
//                Log.e("TAG", "bytedata: " + ret.getBytes());
                return ret.getBytes();

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Hostlistactivity.this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }


    private void init() {
        kProgressHUD = new KProgressHUD(this);
        lnrCreateHost = findViewById(R.id.lnrCreateHost);

//        tvDisplayName = findViewById(R.id.tvDisplayName);
//        tvHostId = findViewById(R.id.tvHostId);
//        tvLimit = findViewById(R.id.tvLimit);
//        tvAddress = findViewById(R.id.tvAddress);
//        tvStatus = findViewById(R.id.tvStatus);
//        lnrUpdateLimit = findViewById(R.id.lnrUpdateLimit);
//        lnrRemoveTop = findViewById(R.id.lnrRemoveTop);
//        tvDisplayName.setText("Display Name: "+Sharedpref.getData(Hostlistactivity.this,Sharedpref.displayName));
//        tvHostId.setText("HostId: "+Sharedpref.getData(Hostlistactivity.this,Sharedpref.hostID));
//        tvLimit.setText("Limit: "+Sharedpref.getData(Hostlistactivity.this,Sharedpref.limit));
//        tvAddress.setText("Address: "+Sharedpref.getData(Hostlistactivity.this,Sharedpref.addresse));
//        tvStatus.setText("Status:"+Sharedpref.getData(Hostlistactivity.this,Sharedpref.status));
//        Sharedpref.setData(Hostlistactivity.this,Sharedpref.hostID,hostID);
//        Sharedpref.setData(Hostlistactivity.this,Sharedpref.limit,limit);
//        Sharedpref.setData(Hostlistactivity.this,Sharedpref.addresse,address);
//        Sharedpref.setData(Hostlistactivity.this,Sharedpref.hostCreator,hostCreator);
//        Sharedpref.setData(Hostlistactivity.this,Sharedpref.status,status);
//        Sharedpref.setData(Hostlistactivity.this,Sharedpref.displayName,displayName);
//        Sharedpref.setBool(Hostlistactivity.this,Sharedpref.hoston,true);

    }

    private void onClicks() {
//        lnrUpdateLimit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //dialog along with api...
//                dialog = new Dialog(Hostlistactivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.updatehostdialog);
//                dialog.setCancelable(false);
//                final EditText etLimit = dialog.findViewById(R.id.etLimit);
//                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
//                Button btnCancel = dialog.findViewById(R.id.btnCancel);
//                dialog.show();
//                btnUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        UpdateHost(dialog,etLimit.getText().toString(),tvHostId.getText().toString());
//                    }
//                });
//
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//
//            }
//        });
        lnrCreateHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
//                RemoveTop(tvHostId.getText().toString());

                ////for changing positions
                //reemove poptop api + list api refreshh activity...
            }
        });
    }

    public void dialog() {
        dialog1 = new Dialog(Hostlistactivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.createhostdialog);
        dialog1.setCancelable(false);
        final EditText etDisplayName = dialog1.findViewById(R.id.etDisplayName);
        final EditText etLimit = dialog1.findViewById(R.id.etLimit);
        final EditText etAddress = dialog1.findViewById(R.id.etAddress);
        Button btnSubmit = dialog1.findViewById(R.id.btnSubmit);
        Button btnCancel = dialog1.findViewById(R.id.btnCancel);
        dialog1.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                CreateHost(etDisplayName.getText().toString(), etLimit.getText().toString(), etAddress.getText().toString());

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });


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
                        kProgressHUD.dismiss();
//                        Sharedpref.setData(Hostlistactivity.this, Sharedpref.hostID, hostID);
//                        Sharedpref.setData(Hostlistactivity.this, Sharedpref.limit, limit);
//                        Sharedpref.setData(Hostlistactivity.this, Sharedpref.addresse, address);
//                        Sharedpref.setData(Hostlistactivity.this, Sharedpref.hostCreator, hostCreator);
//                        Sharedpref.setData(Hostlistactivity.this, Sharedpref.status, status);
//                        Sharedpref.setData(Hostlistactivity.this, Sharedpref.displayName, displayName);
//                        Sharedpref.setCreatedHost(Hostlistactivity.this, "HostCreated");
//                        startActivity(new Intent(getApplicationContext(), Hostlistactivity.class));
                        dialog1.cancel();
                        Toast.makeText(Hostlistactivity.this, "HostCreated Successfully!", Toast.LENGTH_SHORT).show();
                        myhosts(Sharedpref.getAccessToken(Hostlistactivity.this));
                    } else {
                        kProgressHUD.dismiss();
                        dialog1.cancel();
                        Toast.makeText(Hostlistactivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    dialog1.cancel();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(Hostlistactivity.this, "try again after some time", Toast.LENGTH_SHORT).show();

                }
                kProgressHUD.dismiss();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Toast.makeText(Hostlistactivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Error1:" + error.getMessage());
                    kProgressHUD.dismiss();
                    dialog1.cancel();
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
                hashMap.put("x-access-token", Sharedpref.getAccessToken(Hostlistactivity.this));
//                hashMap.put("Content-Type", "application/json; charset=utf-8");

                Log.e("TAG", "Token:-" + Sharedpref.getAccessToken(Hostlistactivity.this));
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
        RequestQueue requestQueue = Volley.newRequestQueue(Hostlistactivity.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }


//

}
