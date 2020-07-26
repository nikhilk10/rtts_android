package com.example.realtimetokensystemapp.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimetokensystemapp.Constant.ConstantUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SECRET_KEY = "$uNnYC$)!)#";
    public static final String SHARED_PREF = "ah_firebase";


    public static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static String USERNAME = "thisissecureusername";
    public static String PASSWORD = "thisissecurepassword";

    public static final String FACKE_CODE = "2";

    public static String imeiNumber = "";

    public static void adsFackeValue(final Context context) {

        final String package_name = context.getApplicationContext().getPackageName();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.addRetailer
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponseFacke: " + response );
                String cod,msg;
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    msg=jsonObject.getString("message");
                    cod=jsonObject.getString("code");

//                    if (cod.equals("1")){
//                        Toasty.error(context,"Please Don't Click Ads").show();
//                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                String gID = "";
//                AdvertisingIdClient.Info adInfo = null;
//                try {
//                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext());
//                    gID = adInfo.getId();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                }
//
//                params.put("package_name", package_name);
//                params.put("g_id", gID);
//
//                TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    imeiNumber = tm.getDeviceId(1).trim();
//
//                } else {
//                    imeiNumber = tm.getDeviceId().trim();
//                }
//
//                params.put("IMEI", imeiNumber);
//                params.put("ClickType", FACKE_CODE);
//                params.put("user_id",SharedPref.getUserID(context));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> paramsHeader = new HashMap<>();
                String credentials = Config.USERNAME + ":" + Config.PASSWORD;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                paramsHeader.put("Authorization", auth);
                return paramsHeader;
            }

        };


        Volley.newRequestQueue(context).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}
