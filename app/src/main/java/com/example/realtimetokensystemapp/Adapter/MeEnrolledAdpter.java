package com.example.realtimetokensystemapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.example.realtimetokensystemapp.Activity.MyHostsJoins;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.Models.HostListModel;
import com.example.realtimetokensystemapp.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.realtimetokensystemapp.Constant.ConstantUrls.joinhost;

public class MeEnrolledAdpter extends RecyclerView.Adapter<MeEnrolledAdpter.ViewHolder> {
    Context context;
    ArrayList<HostListModel> hostListModelArrayList;

    public MeEnrolledAdpter(Context context, ArrayList<HostListModel> hostListModelArrayList, MeEnrolledAdpter hostListAdapter) {
        this.context = context;
        this.hostListModelArrayList = hostListModelArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.me_enrolledadapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        HostListModel hostListModel = hostListModelArrayList.get(position);
        ((ViewHolder) viewHolder).bind(hostListModel, position);
    }

    @Override
    public int getItemCount() {

        return hostListModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        KProgressHUD kProgressHUD;
        TextView tvDisplayName, tvHostId, tvLimit, tvAddress, tvStatus;
        LinearLayout lnrUpdateLimit, lnrNext;
        ImageButton btnNext2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kProgressHUD = new KProgressHUD(context);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
            tvHostId = itemView.findViewById(R.id.tvHostId);
            tvLimit = itemView.findViewById(R.id.tvLimit);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            lnrNext = itemView.findViewById(R.id.lnrNext);
            btnNext2 = itemView.findViewById(R.id.btnNext2);
            lnrUpdateLimit = itemView.findViewById(R.id.lnrUpdateLimit);

        }

        public void bind(final HostListModel model, final int position) {
            tvDisplayName.setText("HostName: " + model.getDisplayName());
            tvHostId.setText("HostId: " + model.getHostID());
            tvStatus.setText("Status: " + model.getStatus());
            tvLimit.setText("Limit: " + model.getLimit());
            tvAddress.setText("Address: " + model.getAddress());

            lnrNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, MyHostsJoins.class);
                    i.putExtra("hostid", model.getHostID());
                    context.startActivity(i);

                }
            });
            btnNext2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, MyHostsJoins.class);
                    i.putExtra("hostid", model.getHostID());
                    context.startActivity(i);

                }
            });
            lnrUpdateLimit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lnrUpdateLimit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //dialog along with api...
                            final Dialog dialog;
                            dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.updatehostdialog);
                            dialog.setCancelable(false);
                            final EditText etLimit = dialog.findViewById(R.id.etLimit);
                            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
                            Button btnCancel = dialog.findViewById(R.id.btnCancel);
                            dialog.show();
                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    UpdateHost(dialog, etLimit.getText().toString(), model.getHostID());
                                }
                            });

                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });


                        }
                    });

                }
            });


        }

        public void UpdateHost(final Dialog dialog, final String limit, final String hostid) {
            kProgressHUD.show();
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.updatehost
                    , new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("TAG", "UpdaeHost: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if (message.equals("Host was updated successfully!")) {
                            dialog.dismiss();
                            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                            kProgressHUD.dismiss();
                            notifyDataSetChanged();
                            ViewHolder.this.notifyAll();

                        } else {
                            kProgressHUD.dismiss();
                            dialog.dismiss();
                            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        kProgressHUD.dismiss();
                        Log.e("TAG", "json error: " + e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(context, "try again after some time", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    kProgressHUD.dismiss();
                    dialog.dismiss();
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
                        jsonObject.put("updatelimit", limit);
                        jsonObject.put("pause", false);
//
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
            requestQueue.add(stringRequest);
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
//                    if (message.equals("User was registered successfully!")) {
//                        startActivity(new Intent(getApplicationContext(), DashboardFinance.class));
//                        Toast.makeText(DashboardFinance.this, "" + message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(DashboardFinance.this, "" + message, Toast.LENGTH_SHORT).show();
//                    }

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
