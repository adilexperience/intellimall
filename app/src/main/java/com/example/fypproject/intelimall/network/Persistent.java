package com.example.fypproject.intelimall.network;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.utils.Constant;
import com.google.gson.Gson;

public class Persistent {
    static SharedPreferences.Editor editor;
    static Gson gson = new Gson();

    public static void persistLoggedInUser(Context context, UserModal user) {
        editor = context.getSharedPreferences(Constant.PERSISTENT, MODE_PRIVATE).edit();
        editor.putString(Constant.LOGGED_IN_USER, gson.toJson(user));
        editor.commit();
        editor.apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        return Persistent.getLoggedInUser(context) != null;
    }

    public static void removePersistentData(Context context) {
        editor = context.getSharedPreferences(Constant.PERSISTENT, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        editor.apply();
    }

    public static UserModal getLoggedInUser(Context context) {
        return gson.fromJson(context.getSharedPreferences(Constant.PERSISTENT, MODE_PRIVATE).getString(Constant.LOGGED_IN_USER, ""), UserModal.class);
    }
}
