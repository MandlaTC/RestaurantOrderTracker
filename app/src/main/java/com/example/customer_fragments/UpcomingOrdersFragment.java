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
import com.example.adapters.CustomerUpcomingOrderAdapter;
import com.example.data_models.Order;
import com.example.data_models.User;
import com.example.model.ApiCall;
import com.example.model.AuthRepository;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UpcomingOrdersFragment extends Fragment {

    private TextView successTextView;
    private String customerID;
    List<Order> orders = new ArrayList<>();
    CustomerUpcomingOrderAdapter customerUpcomingOrdersAdapter;
    RecyclerView upcomingOrdersRecyclerView;
    TextView logOutButton;

    public UpcomingOrdersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UpcomingOrdersFragment newInstance(String param1, String param2) {
        UpcomingOrdersFragment fragment = new UpcomingOrdersFragment();
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
        View view = inflater.inflate(R.layout.customer_upcoming_orders_fragment, container, false);
        upcomingOrdersRecyclerView = view.findViewById(R.id.customer_view_upcoming_orders_recycler_view);
        getStaffObject();
        makeStaffOrdersApiCall();

        return view;
    }


    private void initViewItems() {
        successTextView = getView().findViewById(R.id.customer_upcoming_orders_success_text_view);
        setLogOutButton();
        getStaffObject();
    }

    private void setLogOutButton() {
        logOutButton = getView().findViewById(R.id.customer_upcoming_orders_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRepository.logOutUser(getContext());
            }
        });
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
        if (customerID != null) {
            ApiCall.getUpcomingCustomerOrders(getContext(), customerID, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    //parsing logic, please change it as per your requirement
                    orders = new ArrayList<>();
                    System.out.println("in on response");
                    try {
                        System.out.println("server response" + response);
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonUser = array.getJSONObject(i);
                            Order order = Order.fromMap(jsonUser);
                            System.out.println(order.toString());
                            orders.add(order);
                        }
                        customerUpcomingOrdersAdapter = new CustomerUpcomingOrderAdapter(orders);
                        upcomingOrdersRecyclerView.setAdapter(customerUpcomingOrdersAdapter);
                        upcomingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        successTextView.setText("");
                    } catch (Exception e) {
                        successTextView.setText("Failed to fetch orders");
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
            Toast.makeText(getContext(), "Missing customerID", Toast.LENGTH_SHORT).show();
        }
    }


}