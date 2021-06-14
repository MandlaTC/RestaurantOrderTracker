package com.example.view.staff_fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.AutoSuggestAdapter;
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
 * Use the {@link AddOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText itemDescriptionsEditText;
    TextView submitButton;
    TextView successTextView;
    private RequestQueue requestQueue;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;
    private User selectedCustomer;
    AutoCompleteTextView autoCompleteTextView;

    public AddOrderFragment() {
        // Required empty public constructor
    }

    public static AddOrderFragment newInstance(String param1, String param2) {
        AddOrderFragment fragment = new AddOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewItems();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void initViewItems() {
        requestQueue = Volley.newRequestQueue(getContext());
        itemDescriptionsEditText = getView().findViewById(R.id.add_order_item_description_edit_text);
        submitButton = getView().findViewById(R.id.submit_order_button);
        successTextView = getView().findViewById(R.id.add_order_success_textview);
        setSubmitButtonLoading(false);
        initAutocompleteTextField();
    }

    private void initAutocompleteTextField() {//Autocomplete text view items
        autoCompleteTextView =
                getView().findViewById(R.id.auto_complete_edit_text);
        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new AutoSuggestAdapter(getContext(),
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        selectedCustomer = autoSuggestAdapter.getObject(position);
                    }
                });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeCustomerNameAutocompleteAPICall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    private void makeCustomerNameAutocompleteAPICall(String text) {
        ApiCall.getCustomerFromName(getContext(), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<User> userList = new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonUser = array.getJSONObject(i);
                        User user = User.fromMap(jsonUser);
                        userList.add(user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(userList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    private void makeNewOrderAPICall(String staffUsername, String customerUsername, String itemDescriptions) {
        ApiCall.addNewOrder(getContext(), staffUsername, customerUsername, itemDescriptions, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                try {
                    if (response.contains("Failure")) {
                        successTextView.setText("Failed to add new order, try again later");
                        successTextView.setTextColor(Color.RED);
                    } else {
                        successTextView.setText("succesfully added order");
                    }

                } catch (Exception e) {
                    successTextView.setText("Failed to add new order, try again later");
                    successTextView.setTextColor(Color.RED);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                successTextView.setText("Failed to add new order, try again later");
                successTextView.setTextColor(Color.RED);
            }
        });
    }

    public void submitOrder() {
        setSubmitButtonLoading(true);
        String itemDescription = itemDescriptionsEditText.getText().toString();
        if (validateInputs(itemDescription, selectedCustomer)) {
            String staffId = getUserObject().name;
            makeNewOrderAPICall(staffId, selectedCustomer.name, itemDescription);
            itemDescriptionsEditText.getText().clear();
            selectedCustomer = null;
            autoSuggestAdapter.clear();
            autoCompleteTextView.getText().clear();
            setSubmitButtonLoading(false);
        } else {
            successTextView.setText("Missing values");
            setSubmitButtonLoading(false);
            return;
        }
    }


    public void setSubmitButtonLoading(boolean isLoading) {
        if (isLoading) {
            submitButton.setText("Loading");
            submitButton.setOnClickListener(null);
        } else {
            submitButton.setText("Submit");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitOrder();
                }
            });
        }
    }

    public User getUserObject() {
        return AuthRepository.getSavedUserFromPreference(getView().getContext());
    }

    public boolean validateInputs(String itemDescription, User selectedCustomerObject) {
        if (itemDescription.isEmpty()) {
            return false;
        }
        if (selectedCustomerObject == null) {
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_order_fragment, container, false);
    }
}