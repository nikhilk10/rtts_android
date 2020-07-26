package com.example.realtimetokensystemapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimetokensystemapp.Activity.DashboardFinance;
import com.example.realtimetokensystemapp.Activity.JoinhostActivity;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.Models.HostListModel;
import com.example.realtimetokensystemapp.Models.facebookmodel;
import com.example.realtimetokensystemapp.R;
import com.kaopiz.kprogresshud.KProgressHUD;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.realtimetokensystemapp.Constant.ConstantUrls.joinhost;

public class JoinHostListAdapter extends RecyclerView.Adapter<JoinHostListAdapter.ViewHolder> {
    Context context;
    ArrayList<HostListModel> facebookmodelArrayList;



    public JoinHostListAdapter(JoinhostActivity context, ArrayList<HostListModel> facebookmodelArrayList) {
        this.context = context;
        this.facebookmodelArrayList = facebookmodelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fb_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        HostListModel hostListModel = facebookmodelArrayList.get(position);
        ((ViewHolder) viewHolder).bind(hostListModel, position);
    }

    @Override
    public int getItemCount() {

        return facebookmodelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        KProgressHUD kProgressHUD;
        TextView txt_name, txt_City,txtHostCreator,txthostID;
        Button btnJoinHost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kProgressHUD=new KProgressHUD(context);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_City = itemView.findViewById(R.id.txt_City);
            txtHostCreator = itemView.findViewById(R.id.txtHostCreator);
            txthostID = itemView.findViewById(R.id.txthostID);
            btnJoinHost = itemView.findViewById(R.id.btnJoinHost);



        }

        public void bind(final HostListModel model, final int position) {
            txt_name.setText("Host Name: "+model.getDisplayName());
            txt_City.setText("Addresss: "+model.getAddress());
            txtHostCreator.setText("Creator: "+model.getHostCreator());
            txthostID.setText("Hostid: "+model.getHostID());
            btnJoinHost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JoinHost(model.getHostID());
                }
            });



        }

        public void JoinHost(final String hostID) {
            kProgressHUD.show();
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, joinhost
                    , new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("TAG", "joinhost: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                    if (message.equals("Joined successfully!")) {
                        context.startActivity(new Intent(context,DashboardFinance.class));
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    } else {
                        context.startActivity(new Intent(context,DashboardFinance.class));
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    }

                    } catch (Exception e) {
                        kProgressHUD.dismiss();
                        Log.e("TAG", "json error: " + e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(context, "try again after some time", Toast.LENGTH_SHORT).show();
                    }
                    kProgressHUD.dismiss();

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    kProgressHUD.dismiss();
                    Log.e("TAG", "volley error: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(context, "try again after some time", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return ConstantUrls.CONTENT_TYPE2;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("x-access-token", Sharedpref.getAccessToken(context));
//                Log.e("TAG","Token:-"+Sharedpref.getAccessToken(DashboardFinance.this));
                    return hashMap;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {


                    String ret = "";

                    try {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("hostID", hostID);
                        ret = jsonObject.toString();
                        Log.e("TAG", "joinhostparams: " + ret);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("TAG", "ExeptionParamscatch: " + "Server error");
                    }
//                Log.e("TAG", "bytedata: " + ret.getBytes());
                    return ret.getBytes();

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            requestQueue.add(stringRequest);
        }

    }
}
