package com.example.realtimetokensystemapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimetokensystemapp.Adapter.HostListAdapter;
import com.example.realtimetokensystemapp.Adapter.JoinHostListAdapter;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.Models.HostListModel;
import com.example.realtimetokensystemapp.Models.facebookmodel;
import com.example.realtimetokensystemapp.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.realtimetokensystemapp.Constant.ConstantUrls.myhosts;
import static com.example.realtimetokensystemapp.Constant.ConstantUrls.searchhost;

public class JoinhostActivity extends AppCompatActivity {
    JoinHostListAdapter joinHostListAdapter;
    RecyclerView RecyclerJoinHost;
    KProgressHUD kProgressHUD;
    ArrayList<HostListModel> facebookmodelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinhost);
        kProgressHUD = new KProgressHUD(this);
        facebookmodelArrayList = new ArrayList<>();
        RecyclerJoinHost = findViewById(R.id.RecyclerJoinHost);

        LinearLayoutManager layoutManager = new LinearLayoutManager(JoinhostActivity.this);
        RecyclerJoinHost.setLayoutManager(layoutManager);
        RecyclerJoinHost.setItemAnimator(new DefaultItemAnimator());
//        Searchhost();
        availablehosts();
    }

//    public void Searchhost() {
//        kProgressHUD.show();
//        facebookmodelArrayList.clear();
//        final StringRequest stringRequest = new StringRequest(Request.Method.POST, searchhost
//                , new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("TAG", "searchosts: " + response);
//                try {
//                    facebookmodel facebookmodel = new facebookmodel();
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("Result");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        facebookmodel.setCity(jsonObject1.getString("address"));
//                        facebookmodel.setName(jsonObject1.getString("displayName"));
//                        facebookmodel.setHostidcreator(jsonObject1.getString("hostCreator"));
//                        facebookmodel.setHostid(jsonObject1.getString("hostID"));
//                        facebookmodelArrayList.add(facebookmodel);
//
//                    }
//                    Collections.reverse(facebookmodelArrayList);
//                    joinHostListAdapter = new JoinHostListAdapter(JoinhostActivity.this, facebookmodelArrayList);
//                    RecyclerJoinHost.setAdapter(joinHostListAdapter);
//                    joinHostListAdapter.notifyDataSetChanged();
//                    kProgressHUD.dismiss();
//
//                } catch (Exception e) {
//                    kProgressHUD.dismiss();
//                    Log.e("TAG", "json error: " + e.getMessage());
//                    e.printStackTrace();
//                    Toast.makeText(JoinhostActivity.this, "try again after some time", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                kProgressHUD.dismiss();
//                Log.e("TAG", "volley error: " + error.getMessage());
//                error.printStackTrace();
//                Toast.makeText(JoinhostActivity.this, "try again after some time", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return ConstantUrls.CONTENT_TYPE2;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//
//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("x-access-token", Sharedpref.getAccessToken(JoinhostActivity.this));
////                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(DashboardFinance.this));
//                return hashMap;
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//
//
//                String ret = "";
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("query", "");
//                    ret = jsonObject.toString();
//                    Log.e("TAG", "CreateHostparam: " + ret);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e("TAG", "ExeptionParamscatch: " + "Server error");
//                }
////                Log.e("TAG", "bytedata: " + ret.getBytes());
//                return ret.getBytes();
//
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(JoinhostActivity.this);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
//        requestQueue.add(stringRequest);
//    }
    public void availablehosts() {
        kProgressHUD.show();
        facebookmodelArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, searchhost
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "listofavailableHost: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HostListModel hostListModel = new HostListModel();
                        hostListModel.setAddress(jsonObject1.getString("address"));
                        hostListModel.setDisplayName(jsonObject1.getString("displayName"));
                        hostListModel.setHostCreator(jsonObject1.getString("hostCreator"));
                        hostListModel.setHostID(jsonObject1.getString("hostID"));
                        facebookmodelArrayList.add(hostListModel);
//                        JSONArray jsonArray1 = jsonObject1.getJSONArray("enrolledClients");
//                        for (int j = 0; j < jsonArray1.length(); j++) {
//                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
//                            joinerModel.setClientId(jsonObject2.getString("clientID"));
//                            joinerModel.setTs(jsonObject2.getString("ts"));
//                            joinerModel.setStatus(jsonObject2.getString("status"));
//                            joinerModel.setPosition(jsonObject2.getString("position"));

//                        }
                    }
                    joinHostListAdapter = new JoinHostListAdapter(JoinhostActivity.this, facebookmodelArrayList);
                    RecyclerJoinHost.setAdapter(joinHostListAdapter);
                    joinHostListAdapter.notifyDataSetChanged();
                    kProgressHUD.dismiss();

                } catch (Exception e) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "json error: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(JoinhostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kProgressHUD.dismiss();
                Log.e("TAG", "volley error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(JoinhostActivity.this, "try again after some time", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return ConstantUrls.CONTENT_TYPE2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("x-access-token", Sharedpref.getAccessToken(JoinhostActivity.this));
//                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(Hostlistactivity.this));
                return hashMap;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {


                String ret = "";

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("query", "");
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
        RequestQueue requestQueue = Volley.newRequestQueue(JoinhostActivity.this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }


}
