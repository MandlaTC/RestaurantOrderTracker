package com.example.view.staff_fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOrdersFragment extends Fragment {

    private TextView averageRatingTextView;
    private String staffId;
    List<Order> orders;
    StaffOrderAdapter staffOrderAdapter;
    RecyclerView recyclerView;

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

    private void initViewItems() {
        averageRatingTextView = getView().findViewById(R.id.view_orders_average_rating_text_view);
        User staff = AuthRepository.getSavedUserFromPreference(getContext());
        staffId = staff.id;
        System.out.println(staffId);
        makeStaffRatingApiCall();
        orders = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.view_orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        staffOrderAdapter = new StaffOrderAdapter(getContext(), this.orders);
        makeStaffOrdersApiCall();
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
                    averageRatingTextView.setText("Average Rating: " + response);
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
        if (staffId != null) {
            ApiCall.getStaffOrders(getContext(), staffId, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    //parsing logic, please change it as per your requirement
                    List<Order> orderList = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonUser = array.getJSONObject(i);
                            Order order = Order.fromMap(jsonUser);
                            orderList.add(order);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //IMPORTANT: set data here and notify
                    //staffOrderAdapter.set(userList);
                    staffOrderAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            });
        } else {
            Toast.makeText(getContext(), "Missing staffID", Toast.LENGTH_SHORT);
        }
    }
}