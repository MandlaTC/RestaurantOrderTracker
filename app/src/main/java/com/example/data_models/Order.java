package com.example.data_models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Order {
    private String orderID;
    private String restaurantID;
    private String customerID;
    private String staffID;
    private LocalDate orderCreatedAt;
    private String itemDescription;
    private String orderStatus;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Order fromMap(JSONObject jsonUser) {
        Order order;
        try {
            String orderID = jsonUser.getString("orderID");
            String customerID = jsonUser.getString("customerID");
            String staffID = jsonUser.getString("staffID");
            String restaurantID = jsonUser.getString("restaurantID");
            String itemDescription = jsonUser.getString("itemDescription");
            String status = "Cooking in the kitchen";
            //TODO: Update date and order status when this is added to the endpoint
            LocalDate date = LocalDate.now();
            order = new Order(orderID, restaurantID, customerID, staffID, date, itemDescription, status);

        } catch (JSONException e) {
            order = null;
            System.out.println(e.getMessage());
        }
        return order;
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


    public Order(String orderID, String restaurantID, String customerID, String staffID, LocalDate orderCreatedAt, String itemDescription, String orderStatus) {
        this.orderID = orderID;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.staffID = staffID;
        this.orderCreatedAt = orderCreatedAt;
        this.itemDescription = itemDescription;
        this.orderStatus = orderStatus;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public LocalDate getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(LocalDate orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }
}
