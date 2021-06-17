package com.example.customer_fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.adapters.CustomerPastOrdersAdapter;
import com.example.data_models.Order;
import com.example.data_models.User;
import com.example.model.ApiCall;
import com.example.model.AuthRepository;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PastOrdersFragment extends Fragment implements CustomerPastOrdersAdapter.OnItemClickListener {

    private TextView successTextView;
    private String customerID;
    List<Order> orders = new ArrayList<>();
    CustomerPastOrdersAdapter customerPastOrdersAdapter;
    RecyclerView pastOrdersRecyclerView;
    TextView logOutButton;

    public PastOrdersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PastOrdersFragment newInstance(String param1, String param2) {
        PastOrdersFragment fragment = new PastOrdersFragment();
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
        View view = inflater.inflate(R.layout.customer_past_orders_fragment, container, false);
        pastOrdersRecyclerView = view.findViewById(R.id.customer_view_past_orders_recycler_view);
        getStaffObject();
        makeStaffOrdersApiCall();

        return view;
    }

    private void setLogOutButton() {
        logOutButton = getView().findViewById(R.id.customer_past_orders_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRepository.logOutUser(getContext());
            }
        });
    }

    private void initViewItems() {
        successTextView = getView().findViewById(R.id.customer_past_orders_success_text_view);
        setLogOutButton();
        getStaffObject();
    }

    private void getStaffObject() {
        User customer = AuthRepository.getSavedUserFromPreference(getContext());
        customerID = customer.id;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewItems();
    }


    private void makeStaffOrdersApiCall() {
        System.out.println("in staff Api call");
        if (customerID != null) {
            ApiCall.getPastCustomerOrders(getContext(), customerID, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    //parsing logic, please change it as per your requirement
                    orders = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonUser = array.getJSONObject(i);
                            Order order = Order.fromMap(jsonUser);
                            System.out.println(order.toString());
                            orders.add(order);
                        }
                        customerPastOrdersAdapter = new CustomerPastOrdersAdapter(orders);
                        pastOrdersRecyclerView.setAdapter(customerPastOrdersAdapter);
                        customerPastOrdersAdapter.setOnItemClickListener(PastOrdersFragment.this);
                        pastOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        successTextView.setText("");
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
            System.out.println("customer id is null in makeStaffOrdersAPICall");
            Toast.makeText(getContext(), "Missing staffID", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeAPIRatingCall(String orderID, int rating) {
        ApiCall.rateOrder(getContext(), orderID, rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("success")) {
                        int success = jsonResponse.getInt("success");
                        if (success == 1) {
                            Toast.makeText(getContext(), "Successfully updated rating", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Failed to update rating", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to update rating", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to update rating", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // for rating system:
    @Override
    public void onThumbsUpItemClick(int position) {
        Order order = orders.get(position);
        makeAPIRatingCall(order.getOrderID(), 1);
        order.setRating(1);
        customerPastOrdersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onThumbsDownItemClick(int position) {
        Order order = orders.get(position);
        makeAPIRatingCall(order.getOrderID(), 0);
        order.setRating(0);
        customerPastOrdersAdapter.notifyDataSetChanged();
    }
}