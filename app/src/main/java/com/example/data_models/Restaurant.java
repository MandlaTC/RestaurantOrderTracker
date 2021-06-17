package com.example.data_models;

import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant {
    private String restaurantID;
    private String restaurantName;

    public Restaurant(String restaurantID, String restaurantName) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
    }

    public static Restaurant fromMap(JSONObject data) {
        try {
            String restaurantID = data.getString("restaurantID");
            String restaurantName = data.getString("restaurantName");
            return new Restaurant(restaurantID, restaurantName);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
