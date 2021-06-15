package com.example.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ApiCall {
    private static ApiCall mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public ApiCall(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ApiCall getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiCall(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static void getCustomerFromName(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=cusAutocomplete&username=" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void getRestaurantAutocomplete(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=restAutocomplete&restaurant=" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void addNewOrder(Context ctx, String staffID, String customerID, String itemDescriptions, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        StringBuilder url = new StringBuilder("https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=newOrder");
        url.append("&customer=" + customerID);
        url.append("&staff=" + staffID);
        url.append("&item=" + itemDescriptions);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void getStaffAverageRating(Context ctx, String staffID, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        StringBuilder url = new StringBuilder("https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=avgRating");
        url.append("&staff=" + staffID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);

    }

    public static void getStaffOrders(Context ctx, String staffID, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        StringBuilder url = new StringBuilder("https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=staffOrders");
        url.append("&staff=" + staffID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);

    }

    public static void changeOrderStatus(Context ctx, String orderID, String updatedOrderStatus, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        StringBuilder url = new StringBuilder("https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=updateStatus");
        url.append("&status=" + updatedOrderStatus);
        url.append("&order=" + orderID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);

    }

    public static void doesStaffHaveRestaurant(Context ctx, String staffID, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        StringBuilder url = new StringBuilder("https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=staffHasRestaurant");
        url.append("&staff=" + staffID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);

    }

    public static void addStaffToRestaurant(Context ctx, String staffID, String restaurantID, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        StringBuilder url = new StringBuilder("https://lamp.ms.wits.ac.za/home/s2303145/staff.php?rType=updateStaffRest");
        url.append("&staffID=" + staffID);
        url.append("&restID=" + restaurantID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);

    }

}
