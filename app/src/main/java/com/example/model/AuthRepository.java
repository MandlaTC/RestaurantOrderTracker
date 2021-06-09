package com.example.model;


import android.app.DownloadManager;

import org.json.JSONObject;

import okhttp3.Request;

public class AuthRepository {

    String url = "https://lamp.ms.wits.ac.za/home/s2303145/index.php";

    public JSONObject loginUser(String username, String password) {
        Request request = new Request.Builder().url(url).build();
        return new JSONObject();
    }
}
