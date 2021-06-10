package com.example.model;


import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.app.Navigator;
import com.example.data_models.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.Request;

public class AuthRepository {

    String authenticationUrl = "https://lamp.ms.wits.ac.za/home/s2303145/index.php";
    public static String sharedPreferencesUserDirectory = "restaurant_order_tracker_user";

    public JSONObject loginUser(String username, String password) {
        Request request = new Request.Builder().url(authenticationUrl).build();
        return new JSONObject();
    }

    public static void storeUser(Context context, User user) {
        //preference file name
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesUserDirectory, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        sharedPreferencesEditor.putString(sharedPreferencesUserDirectory, json);
        sharedPreferencesEditor.commit();
    }

    public static User getSavedUserFromPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesUserDirectory, 0);
        if (sharedPreferences.contains(sharedPreferencesUserDirectory)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(sharedPreferencesUserDirectory, ""), User.class);
        }
        return null;
    }

    public static void logOutUser(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesUserDirectory, 0);
        if (sharedPreferences.contains(sharedPreferencesUserDirectory)) {
            sharedPreferences.edit().remove(sharedPreferencesUserDirectory).commit();
            Navigator.handleUserBasedNavigation(context, null);
        }
    }
}
