package com.example.realtimetokensystemapp.Constant;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpref {

    public static String SharedPrefName = "MySP";
    public static String MySharedpref = "MySharedpref";
    public static String islogin = "isLogin";
    public static String userid = "userid";
    public static String emailid = "emailid";
    public static String password = "password";
    public static String role = "role";
    public static String id = "id";
    public static String name = "name";
    public static String fullname = "fullname";
    public static String gender = "gender";
    public static String isDeleted = "isDeleted";
    public static String removehost = "hoston";
    public static String hostID = "hostID";
    public static String limit = "limit";
    public static String addresse = "addresse";
    public static String status = "status";
    public static String hostCreator = "hostCreator";
    public static String displayName = "displayName";
    public static String __v = "__v";


    public static void setFireToken(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ft", value);
        editor.apply();
    }

    public static String getFireToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        return sp.getString("ft", "ft");
    }

    public static void setCreatedHost(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ch", value);
        editor.apply();
    }

    public static String getCreatedHost(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        return sp.getString("ch", "ch");
    }
    public static void setAccessToken(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("at", value);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        return sp.getString("at", "at");
    }

    public static void setHostIdd(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("hi", value);
        editor.apply();
    }

    public static String getHostIDD(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        return sp.getString("hi", "hi");
    }
    public static boolean getBool(Context activity, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static boolean getBoooleans(Context activity, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MySharedpref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static String getData(Context activity, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static String getUserid(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(userid, "");
    }

    public static void setBool(Context activity, String key, boolean val) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }
 public static void setBoooleans(Context activity, String key, boolean val) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MySharedpref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public static void setData(Context activity, String key, String val) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public static void clear(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
