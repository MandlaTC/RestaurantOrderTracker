package com.example.data_models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String id;
    public String name;
    public String email;
    public String userType;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public User(String id, String name, String email, String userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public static User fromMap( JSONObject data) {
        try {
            String userId = data.getString("userId");
            String username = data.getString("username");
            String email = data.getString("email");
            String userType = data.getString("userType");
            return new User(
                    userId, username, email, userType);
        } catch (JSONException e) {
            return null;

        }

    }

    public static void storeUser(Context context, User user) {
        //preference file name
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        sharedPreferencesEditor.putString("user", json);
        sharedPreferencesEditor.commit();
    }

    public static User getSavedUserFromPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", 0);
        if (sharedPreferences.contains("user")) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString("user", ""), User.class);
        }
        return null;
    }
}

