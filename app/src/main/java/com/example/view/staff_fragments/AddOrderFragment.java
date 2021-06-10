package com.example.view.staff_fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.Navigator;
import com.example.data_models.User;
import com.example.model.Api;
import com.example.model.AuthRepository;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    EditText customerEditText;
    private boolean isLoading;
    TextView submitButton;
    TextView successTextView;

    public AddOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        customerEditText = getView().findViewById(R.id.add_order_customer_input_edit_text);
        itemDescriptionsEditText = getView().findViewById(R.id.add_order_item_description_edit_text);
        submitButton = getView().findViewById(R.id.submit_order_button);
        successTextView = getView().findViewById(R.id.add_order_success_textview);
        setSubmitButtonLoading(false);
    }

    public void submitOrder() {
        setSubmitButtonLoading(true);
        String itemDescription = itemDescriptionsEditText.getText().toString();
        String customerName = customerEditText.getText().toString();
        Api api = new Api();
        if (validateInputs(itemDescription, customerName)) {
            String staffId = getUserObject().name;
            handleNetworkRequest(customerName, staffId, itemDescription);
        } else {
            successTextView.setText("Missing values");
            setSubmitButtonLoading(false);
            return;
        }
    }

    public void handleNetworkRequest(String customer, String staffName, String itemDescriptions) {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://lamp.ms.wits.ac.za/home/s2303145/new-order.php";
        // Creating request object, username, password, email, userType parsed into request
        Request request = new Request.Builder()
                .url(baseUrl +
                        "?customer" + customer
                        + "&staff" + staffName + "&item" + itemDescriptions).build();
        ;

        // Client object method newCall called assyncronously
        client.newCall(request).enqueue(new Callback() {

            // On failure print error to the signUpText Text view
            // TODO: comment out contents of onFailure method
            @Override
            public void onFailure(Call call, IOException e) {
                successTextView.setText("Failed to add items, try again later");
                setSubmitButtonLoading(false);
                return;

                /*getView().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        errorTextView.setText("Failed to get call the server, try again later");
                        setButtonLoadingText(false);
                        return;
                    }
                });*/
            }

            /*
             * On response the system print response data, and using UI thread navigateToHomePage()
             * method is called
             */
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonResponseData = response.body().string();
                //Attempt to do jsonResponsedata depednign on if request is succesful
                successTextView.setText("Added new item succesfully");
                setSubmitButtonLoading(false);
                return;


            }
        });
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

    public boolean validateInputs(String itemDescription, String username) {
        if (itemDescription.isEmpty()) {
            return false;
        }
        if (username.isEmpty()) {
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