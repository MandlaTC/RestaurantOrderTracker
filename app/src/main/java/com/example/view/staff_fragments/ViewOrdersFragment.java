package com.example.view.staff_fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.adapters.StaffOrderAdapter;
import com.example.data_models.Order;
import com.example.data_models.User;
import com.example.model.ApiCall;
import com.example.model.AuthRepository;
import com.example.testrequests.R;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOrdersFragment extends Fragment implements StaffOrderAdapter.OnItemClickListener {

    private TextView averageRatingTextView;
    private String staffId;
    List<Order> orders = new ArrayList<>();
    StaffOrderAdapter staffOrderAdapter;
    RecyclerView ordersRecyclerView;

    public ViewOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewOrdersFragment newInstance(String param1, String param2) {
        ViewOrdersFragment fragment = new ViewOrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_orders_fragment, container, false);
        ordersRecyclerView = view.findViewById(R.id.view_orders_recycler_view);
        getStaffObject();
        makeStaffOrdersApiCall();
        List<Order> temp = new ArrayList<>();

        return view;
    }


    private void initViewItems() {
        averageRatingTextView = getView().findViewById(R.id.view_orders_average_rating_text_view);
        System.out.println(staffId);
        getStaffObject();
        makeStaffRatingApiCall();
    }

    private void getStaffObject() {
        User staff = AuthRepository.getSavedUserFromPreference(getContext());
        staffId = staff.id;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewItems();
    }

    private void makeStaffRatingApiCall() {
        if (staffId != null) {
            ApiCall.getStaffAverageRating(getContext(), staffId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonObject = new JSONArray(response);
                        JSONObject ratingObject = jsonObject.getJSONObject(0);
                        String rating = ratingObject.getString("0");
                        averageRatingTextView.setText("Average Rating: " + rating + "/1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        averageRatingTextView.setText("Average Rating: " + "4.9");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    averageRatingTextView.setText("Failed to fetch rating");
                }
            });
        } else {
            averageRatingTextView.setText("Failed to fetch rating");
        }
    }

    private void makeStaffOrdersApiCall() {
        System.out.println("in staff Api call");
        if (staffId != null) {
            ApiCall.getStaffOrders(getContext(), staffId, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    //parsing logic, please change it as per your requirement
                    orders = new ArrayList<>();
                    System.out.println("in on response");
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonUser = array.getJSONObject(i);
                            Order order = Order.fromMap(jsonUser);
                            System.out.println(order.toString());
                            orders.add(order);
                        }
                        staffOrderAdapter = new StaffOrderAdapter(orders);
                        ordersRecyclerView.setAdapter(staffOrderAdapter);
                        staffOrderAdapter.setOnItemClickListener(ViewOrdersFragment.this);
                        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            });
        } else {
            System.out.println("staff id is nul");
            Toast.makeText(getContext(), "Missing staffID", Toast.LENGTH_SHORT);
        }
    }

    private void makeChangeOrdersAPICall(String orderID, String updatedOrderStatus) {

        ApiCall.changeOrderStatus(getContext(), orderID, updatedOrderStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        Toast.makeText(getContext(), "Succesfully updated order status", Toast.LENGTH_SHORT);

                    } else {

                        Toast.makeText(getContext(), "Failed to update status,try again later", Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to update status,try again later", Toast.LENGTH_SHORT);
            }
        });
    }

    //change order
    @Override
    public void onItemClick(int position) {
        System.out.println(position);
        String firstOrderState = "cooking";
        String secondOrderState = "collection";
        String lastOrder = "completed";
        Order order = orders.get(position);
        String updatedStatus = "";
        if (order.getOrderStatus().equals(firstOrderState)) {
            updatedStatus = secondOrderState;
        } else if (order.getOrderStatus().equals(secondOrderState)) {
            updatedStatus = lastOrder;
        } else {
            updatedStatus = lastOrder;
            Toast.makeText(getContext(), "Cannot update completed order", Toast.LENGTH_SHORT);
        }
        order.setOrderStatus(updatedStatus);
        staffOrderAdapter.notifyDataSetChanged();
        makeChangeOrdersAPICall(order.getOrderID(), updatedStatus);
    }
}