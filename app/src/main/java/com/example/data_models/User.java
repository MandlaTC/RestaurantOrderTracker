package com.example.data_models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    public String id;
    public String name;
    public String email;
    public String userType;
    public static String staffUserType = "Staff";
    public static String customerUserType = "Customer";

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

    public static User fromMap(JSONObject data) {
        try {
            String userId = data.getString("userID");
            String username = data.getString("username");
            String email = data.getString("email");
            String userType = data.getString("userType");
            return new User(
                    userId, username, email, userType);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            return null;

        }

    }




}

