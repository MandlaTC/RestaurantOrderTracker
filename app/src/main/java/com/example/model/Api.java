package com.example.model;

import android.content.Context;

import com.example.app.Navigator;
import com.example.data_models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {

    public boolean addNewItem( String staffId, String customerId, String itemDescriptions) {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://lamp.ms.wits.ac.za/home/s2303145/new-order.php";
        // Creating request object, username, password, email, userType parsed into request
        Request request = new Request.Builder()
                .url(baseUrl +
                        "?customer" + customerId
                        + "&staff" + staffId + "&item" + itemDescriptions).build();

        try {
            Response response = client.newCall(request).execute();
            //TODO, depending on how the server handles errors - update this method accordingly
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
