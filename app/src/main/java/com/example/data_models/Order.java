package com.example.data_models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.app.DateFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Order {
    private String orderID;
    private String restaurantID;
    private String customerID;
    private String staffID;
    private Date orderCreatedAt;
    private String itemDescription;
    private String orderStatus;
    private String customerName;
    private String restaurantName;
    private int rating;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Order fromMap(JSONObject jsonUser) {
        Order order;
        try {
            String orderID = jsonUser.getString("orderID");
            String customerName = "";
            int rating = 0;
            if (jsonUser.has("customerName")) {
                customerName = jsonUser.getString("customerName");
            }
            if (jsonUser.has("rating")) {
                String stringedRating = jsonUser.getString("rating");
                rating = Integer.parseInt(stringedRating);
            }
            String customerID = jsonUser.getString("customerID");
            String staffID = jsonUser.getString("staffID");
            String restaurantID = jsonUser.getString("restaurantID");
            String itemDescription = jsonUser.getString("itemDescription");
            String stringedDate = jsonUser.getString("orderCreatedAt");
            String restaurantName = "";
            if (jsonUser.has("restaurantName")) {
                restaurantName = jsonUser.getString("restaurantName");
            }
            Date date = DateFormatter.getDateFromString(stringedDate);
            String orderStatus = jsonUser.getString("orderStatus");
            //TODO: Update date and order status when this is added to the endpoint
            order = new Order(orderID, restaurantID, customerID, staffID, customerName, date, itemDescription, orderStatus, restaurantName, rating);

        } catch (JSONException e) {
            order = null;
            System.out.println(e.getMessage());
        }
        return order;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", restaurantID='" + restaurantID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", staffID='" + staffID + '\'' +
                ", orderCreatedAt=" + orderCreatedAt +
                ", itemDescription='" + itemDescription + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", customerName='" + customerName + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }


    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Order(String orderID, String restaurantID, String customerID, String staffID, String customerName, Date orderCreatedAt, String itemDescription, String orderStatus, String restaurantName, int rating) {
        this.orderID = orderID;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.staffID = staffID;
        this.orderCreatedAt = orderCreatedAt;
        this.itemDescription = itemDescription;
        this.orderStatus = orderStatus;
        this.restaurantName = restaurantName;
        this.rating = rating;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Date getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(Date orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }
}
