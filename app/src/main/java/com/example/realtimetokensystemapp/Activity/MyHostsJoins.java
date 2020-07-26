package com.example.realtimetokensystemapp.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimetokensystemapp.Adapter.JoinersListAdapter;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.Models.JoinerModel;
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

import static com.example.realtimetokensystemapp.Constant.ConstantUrls.searchhost;

public class MyHostsJoins extends AppCompatActivity {
//

    KProgressHUD kProgressHUD;
    Dialog dialog;
    RecyclerView recyclermyhostjoins;
    LinearLayout lnrRemoveTop;

    JoinersListAdapter joinersListAdapter;
    ArrayList<JoinerModel> joinerModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhostjoins);
        init();
        joinerModelArrayList = new ArrayList<>();
        recyclermyhostjoins = findViewById(R.id.recyclermyhostjoins);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyHostsJoins.this);
        recyclermyhostjoins.setLayoutManager(layoutManager);
        recyclermyhostjoins.setItemAnimator(new DefaultItemAnimator());

        onClicks();
        myhosts(getIntent().getStringExtra("hostid"));
//        listenrolledunderhost(getIntent().getStringExtra("hostid"));
        Log.e("TAG","Removedusershostid1: "+Sharedpref.getHostIDD(getApplicationContext()));
        Log.e("TAG","Removedusershostid2: "+getIntent().getStringExtra("hostid"));

    }

    public void myhosts(final String hostid) {
        kProgressHUD.show();
        joinerModelArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, searchhost
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "listenrolledunderhost: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JoinerModel joinerModel = new JoinerModel();
//                        hostListModel.setAddress(jsonObject1.getString("address"));
//                        hostListModel.setStatus(jsonObject1.getString("status"));
//                        hostListModel.setDisplayName(jsonObject1.getString("displayName"));
//                        hostListModel.setLimit(jsonObject1.getString("limit"));
//                        hostListModel.setHostID(jsonObject1.getString("hostID"));
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("enrolledClients");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                            joinerModel.setClientId(jsonObject2.getString("clientID"));
                            joinerModel.setTs(jsonObject2.getString("ts"));
                            joinerModel.setStatus(jsonObject2.getString("status"));
                            joinerModel.setPosition(jsonObject2.getString("position"));
                            joinerModelArrayList.add(joinerModel);

                        }
                    }
                    joinersListAdapter = new JoinersListAdapter(MyHostsJoins.this, joinerModelArrayList);
                    recyclermyhostjoins.setAdapter(joinersListAdapter);
                    joinersListAdapter.notifyDataSetChanged();
                    kProgressHUD.dismiss();

                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(MyHostsJoins.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kProgressHUD.dismiss();
                Log.e("TAG", "volley error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(MyHostsJoins.this, "try again after some time", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token", Sharedpref.getAccessToken(MyHostsJoins.this));
//                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(MyHostsJoins.this));
                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("query",hostid);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MyHostsJoins.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }



    private void init() {
        kProgressHUD = new KProgressHUD(this);
        lnrRemoveTop = findViewById(R.id.lnrRemoveTop);
    }

    private void onClicks() {

        lnrRemoveTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RemoveTop(Sharedpref.getHostIDD(MyHostsJoins.this));

                ////for changing positions
                //reemove poptop api + list api refreshh activity...
            }
        });
    }


    public void RemoveTop(final String hostid) {
        kProgressHUD.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.poptop
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RemoveTop: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    String message = jsonObject.getString("message");
                    String code = jsonObject.getString("code");
                    if (code.equals("200")) {
//                        myhosts(tvHostId.getText().toString());
                        Toast.makeText(MyHostsJoins.this, "Removed top member", Toast.LENGTH_SHORT).show();
//                        listenrolledunderhost(getIntent().getStringExtra("hostid"));
                        myhosts(getIntent().getStringExtra("hostid"));
//                        dialog.dismiss();
                        kProgressHUD.dismiss();

                    } else {
                        kProgressHUD.dismiss();
//                        dialog.dismiss();
                        Toast.makeText(MyHostsJoins.this, "Still in Waiting!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
//                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(MyHostsJoins.this, "try again after some time", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kProgressHUD.dismiss();
//                dialog.dismiss();
                Log.e("TAG", "volley error: " + error.getMessage());
                error.printStackTrace();
//                Toast.makeText(MyHostsJoins.this, "try again after some time", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token", Sharedpref.getAccessToken(MyHostsJoins.this));
//                hashMap.put("Content-Type", "application/json; charset=utf-8");

//                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(Hostlistactivity.this));
                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("hostID", hostid);

                    ret = jsonObject.toString();
                    Log.e("TAG", "updatrparem: " + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                }
//                Log.e("TAG", "bytedata: " + ret.getBytes());
                return ret.getBytes();

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyHostsJoins.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

}
