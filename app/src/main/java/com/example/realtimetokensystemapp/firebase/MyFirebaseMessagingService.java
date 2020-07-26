package com.example.realtimetokensystemapp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;


import com.example.realtimetokensystemapp.R;
import com.example.realtimetokensystemapp.util.Config;
import com.example.realtimetokensystemapp.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.android.volley.VolleyLog.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationUtils notificationUtils;
    Intent resultIntent;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
      super.onMessageReceived(remoteMessage);


        Log.e(TAG, "onMessageReceivedNoitify: " + remoteMessage );
//
        String msg = null;
        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
            Log.e(TAG, "onMessageReceived0: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            msg = data.get("body");

        } else if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "onMessageReceived1: "+ remoteMessage.getData() );
            msg = remoteMessage.getNotification().getBody();
        }


        Log.e(TAG, "onMessageReceived: " + remoteMessage.getData().toString() + " " + msg);

        if (remoteMessage.getData().toString()!=null){

         try {
             JSONObject json = new JSONObject(remoteMessage.getData());
                String dataobject = json.getString("data");
                JSONObject jsonObject3=new JSONObject(dataobject);
                String message=jsonObject3.getString("message");
                Log.e(TAG, "onMessageReceived3: "+ message );
             handleNotification(String.valueOf(Html.fromHtml(Html.fromHtml(message).toString())));
            }catch (Exception e){

                   }
             }



        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject json = new JSONObject(params);
                int sdkVersion = Build.VERSION.SDK_INT;
                Log.e(TAG, "onMessageReceivedSDK: "+ sdkVersion );
                if (sdkVersion>=26){
                    handleDataMessage(json);
                }else {
                    handleDataMessage(json);
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

    }


    private void handleNotification(String message) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent pushNotification = new Intent(com.example.realtimetokensystemapp.util.Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//            Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
//
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            notificationUtils.showNotificationMessage(getResources().getString(R.string.app_name), message, "test", resultIntent);
        } else {
            Intent pushNotification = new Intent(com.example.realtimetokensystemapp.util.Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            notificationUtils.showNotificationMessage(getResources().getString(R.string.app_name), message, "test", resultIntent);
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void handleDataMessage(JSONObject json) {

        Log.e(TAG, "push json: " + json.toString());
        try {
            String dataobject = json.getString("data");
            JSONObject jsonObject3=new JSONObject(dataobject);
            String message=jsonObject3.getString("message");
            String title = getResources().getString(R.string.app_name);
//            String type = json.getString("notification_type");
            String message1 = String.valueOf(Html.fromHtml(Html.fromHtml(message).toString()));
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(com.example.realtimetokensystemapp.util.Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message1);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
//
//                resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                showNotification(getApplicationContext(), title, message1,resultIntent);

            } else {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message1);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
//
//                resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                showNotification(getApplicationContext(), title, message1, resultIntent);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(Context context, String title, String body, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

}