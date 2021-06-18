package com.example.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.data_models.User;
import com.example.model.ApiCall;
import com.example.model.AuthRepository;
import com.example.orderTracker.R;
import com.example.view.StaffAddRestaurantActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class StaffLoadingActivity extends AppCompatActivity {
    TextView loadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_loading);
        loadingTextView = findViewById(R.id.staff_loading_loading_text_view);
        checkIfStaffHasRestaurantAndHandleNavigation();
    }

    public void checkIfStaffHasRestaurantAndHandleNavigation() {
        User user = AuthRepository.getSavedUserFromPreference(this);
        ApiCall.doesStaffHaveRestaurant(this, user.id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(StaffLoadingActivity.this, StaffAddRestaurantActivity.class);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("message")) {
                        intent = new Intent(StaffLoadingActivity.this, StaffHomeActivity.class);
                    }
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                    loadingTextView.setText("Error, try again later");
                }
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingTextView.setText("Failed to fetch relevant data, try reloading the app");
            }
        });
    }
}