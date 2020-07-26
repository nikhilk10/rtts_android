package com.example.realtimetokensystemapp.Adapter;

import android.content.Context;
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
import com.example.realtimetokensystemapp.Constant.ConstantUrls;
import com.example.realtimetokensystemapp.Constant.Sharedpref;
import com.example.realtimetokensystemapp.Models.JoinerModel;
import com.example.realtimetokensystemapp.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.realtimetokensystemapp.Constant.ConstantUrls.joinhost;

public class JoinersListAdapter extends RecyclerView.Adapter<JoinersListAdapter.ViewHolder> {
    Context context;
    ArrayList<JoinerModel> facebookmodelArrayList;

    public JoinersListAdapter(Context context, ArrayList<JoinerModel> saleArrayList) {
        this.context = context;
        this.facebookmodelArrayList = saleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fb2_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        JoinerModel joinerModel = facebookmodelArrayList.get(position);
        ((ViewHolder) viewHolder).bind(joinerModel, position);
    }

    @Override
    public int getItemCount() {

        return facebookmodelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        KProgressHUD kProgressHUD;
        TextView tvClientId, tvTs,tvStatus,tvPosition;
        Button btnJoinHost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kProgressHUD=new KProgressHUD(context);
            tvClientId = itemView.findViewById(R.id.txt_name);
            tvTs = itemView.findViewById(R.id.txt_City);
            tvStatus = itemView.findViewById(R.id.txtHostCreator);
            tvPosition = itemView.findViewById(R.id.txthostID);
            btnJoinHost = itemView.findViewById(R.id.btnJoinHost);
            btnJoinHost.setVisibility(View.GONE);

        }

        public void bind(final JoinerModel model, final int position) {
            tvClientId.setText("ClientId: "+model.getClientId());
            tvTs.setText("TimeStamp: " +model.getTs());
            tvStatus.setText("Status: " +model.getStatus());
            tvPosition.setText("Position: " +model.getPosition());
//            btnJoinHost.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    JoinHost(txthostID.getText().toString());
//                }
//            });



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
