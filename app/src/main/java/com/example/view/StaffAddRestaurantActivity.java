package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.adapters.RestaurantAutoSuggestAdapter;
import com.example.data_models.Restaurant;
import com.example.data_models.User;
import com.example.model.ApiCall;
import com.example.model.AuthRepository;
import com.example.testrequests.R;
import com.example.view.home.StaffHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StaffAddRestaurantActivity extends AppCompatActivity {
    RestaurantAutoSuggestAdapter autoSuggestAdapter;
    Restaurant selectedRestaurant;
    TextView errorTextView;
    AutoCompleteTextView autoCompleteTextView;
    private Handler handler;
    User user;
    TextView submitButton;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_add_restaurant);
        errorTextView = findViewById(R.id.add_rest_error_text_view);
        submitButton = findViewById(R.id.add_restaurant_submit_button);
        user = AuthRepository.getSavedUserFromPreference(this);
        initAutocompleteTextField();
        setSubmitButtonLoading(false);
    }

    private void initAutocompleteTextField() {//Autocomplete text view items
        autoCompleteTextView =
                findViewById(R.id.add_restaurant_autocomplete_edit_text);
        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new RestaurantAutoSuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        selectedRestaurant = autoSuggestAdapter.getObject(position);
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
                        System.out.println(autoCompleteTextView.getText());
                        makeRestaurantAutocompleteCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    public void submit() {
        if (selectedRestaurant == null || user == null) {
            errorTextView.setText("Please enter a restaurant, if your restaurant does not appear in the list - contact the administrator");
            return;
        }
        makeRestaurantSubmitAPICall();
    }

    public void setSubmitButtonLoading(boolean loading) {
        if (loading) {
            submitButton.setText("Loading");
            submitButton.setOnClickListener(null);
        } else {
            submitButton.setText("Submit");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submit();
                }
            });
        }
    }

    private void makeRestaurantSubmitAPICall() {

        ApiCall.addStaffToRestaurant(this, user.id, selectedRestaurant.getRestaurantID(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        Intent intent = new Intent(StaffAddRestaurantActivity.this, StaffHomeActivity.class);
                        startActivity(intent);
                        return;
                    }
                    errorTextView.setText("Failed to call the server, try again later");
                    setSubmitButtonLoading(false);

                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                    errorTextView.setText("Failed to add restaurant, try again later - json exception");
                    setSubmitButtonLoading(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                errorTextView.setText("Failed to add restaurant, try again later");
                setSubmitButtonLoading(false);
            }
        });
    }

    private void makeRestaurantAutocompleteCall(String text) {
        ApiCall.getRestaurantAutocomplete(this, text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<Restaurant> restaurantList = new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);
                        Restaurant restaurant = Restaurant.fromMap(data);
                        System.out.println(restaurant.getRestaurantName());
                        restaurantList.add(restaurant);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(restaurantList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }
}