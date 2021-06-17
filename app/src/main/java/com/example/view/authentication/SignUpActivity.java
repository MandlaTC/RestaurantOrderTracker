package com.example.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Navigator;
import com.example.data_models.User;
import com.example.model.AuthRepository;
import com.example.testrequests.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView errorTextView;
    Context context;
    TextView signUpButton;
    Spinner userTypeSpinner;
    public static String firstSpinnerString = "Please select customer or staff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        View view = new View(this);
        initSpinner();
        context = this;
        errorTextView = findViewById(R.id.signUpErrorTextView);
        signUpButton = findViewById(R.id.signUpButton);
        setButtonLoadingText(false);
        /*
         * Creating items for spinner object. Items incl. (user, staff)
         */

    }

    public void initSpinner() {
        userTypeSpinner = (Spinner) findViewById(R.id.user_type_spinner);

        //Creating the ArrayAdapter instance having the country list
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(firstSpinnerString);
        arrayList.add(User.staffUserType);
        arrayList.add(User.customerUserType);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        userTypeSpinner.setAdapter(aa);

        // Upon selection small popup to confirm selection

        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                parent.setSelection(position);
                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Please select a valid user type", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSignUpButtonOnClick() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpRequest(view);
            }
        });
    }

    public void setButtonLoadingText(boolean isBusy) {
        if (isBusy) {
            signUpButton.setText("Loading");
            signUpButton.setOnClickListener(null);
        } else {
            signUpButton.setText("Sign Up");
            setSignUpButtonOnClick();
        }

    }

    public boolean validateInputs(String username, String password, String email, String userType) {

        if (username.isEmpty()) {
            return false;
        }
        if (password.isEmpty()) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        if (userType.equals(firstSpinnerString)) {
            return false;
        }
        return true;
    }

    public void signUpRequest(View view) {
        // Creating OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Retrieve username, email, user type and password
        TextView t = (TextView) findViewById(R.id.username_edit_text);
        String username = t.getText().toString();
        t = (TextView) findViewById(R.id.email_address_edit_text);
        String email = t.getText().toString();
        t = (TextView) findViewById(R.id.password_edit_text);
        String password = t.getText().toString();
        String userType = userTypeSpinner.getSelectedItem().toString();
        if (!validateInputs(username, password, email, userType)) {
            errorTextView.setText("Missing fields for either username, password, email, or usertype");
            setButtonLoadingText(false);
            return;
        }

        // Creating request object, username, password, email, userType parsed into request
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2303145/authentication.php?username="
                        + username + "&password=" + password
                        + "&email=" + email + "&userType=" + userType).build();

        // Client object method newCall called assyncronously
        client.newCall(request).enqueue(new Callback() {

            // On failure print error to the signUpText Text view
            // TODO: comment out contents of onFailure method
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        errorTextView.setText("Failed to get call the server, try again later");
                        setButtonLoadingText(false);
                        return;
                    }
                });
            }

            /*
             * On response the system print response data, and using UI thread navigateToHomePage()
             * method is called
             */
            String message = "";

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonResponseData = response.body().string();
                try {
                    JSONObject data = new JSONObject(jsonResponseData);
                    int success = data.getInt("success");
                    if (success == 1) {
                        //store user object
                        String userDataString = data.getString("user");
                        JSONObject userData = new JSONObject(userDataString);
                        User user = User.fromMap(userData);
                        if (user != null) {
                            AuthRepository.storeUser(context, user);
                            Navigator.handleUserBasedNavigation(context, user);
                        }
                    } else {
                        setButtonLoadingText(false);
                        message = data.getString("message");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    errorTextView.setText("Technical error, try again later.");

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        errorTextView.setText(message);
                    }
                });
            }
        });


    }


    // Navigation methods for moving between UI views
    public void navigateToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}