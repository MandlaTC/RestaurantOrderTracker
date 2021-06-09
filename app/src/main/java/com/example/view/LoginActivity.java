package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.data_models.User;
import com.example.testrequests.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    Context context;
    View view;
    TextView loginButton;
    TextView errorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        view = new View(this);
        context = getApplicationContext();
        EditText usernameEditText = findViewById(R.id.username_edit_text);
        EditText passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.loginButton);
        errorTextView = findViewById(R.id.loginErrorTextView);
        errorTextView.setText("");
        setLoginButtonOnClick();
    }

    public void setLoginButtonOnClick() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest(view, context);
            }
        });
    }

    public void setButtonLoadingText(boolean isBusy) {
        if (isBusy) {
            loginButton.setText("Loading");
            loginButton.setOnClickListener(null);
        } else {
            loginButton.setText("Login");
            setLoginButtonOnClick();
        }
    }    /*
     * Method to handle login requests.
     * Request sent to lamp.ms.wits.ac.za/home/s2303145/index.php
     * On success perform navigateToHomePage(View view)
     **/

    public void loginRequest(View view, Context context) {
        try {
            setButtonLoadingText(true);
            errorTextView.setTextColor(Color.RED);
            // Creating OkHttpClient
            OkHttpClient client = new OkHttpClient();
            // Retrieve username and password
            TextInputEditText t = (TextInputEditText) findViewById(R.id.username_edit_text);
            String username = t.getText().toString();
            t = (TextInputEditText) findViewById(R.id.password_edit_text);
            String password = t.getText().toString();
            if (!validateInputs(username, password)) {
                errorTextView.setText("Invalid username and password entered");
                setButtonLoadingText(false);
                return;

            }
            String url = "https://lamp.ms.wits.ac.za/home/s2303145/index.php?username=" + username + "&password=" + password;
            // Request object with username and password passed through into url
            Request request = new Request.Builder().url(url)
                    .build();

            // Client object method newCall() called assyncronously
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
                                System.out.println(user.toString());
                                User.storeUser(context, user);
                                navigateToHomePage(view);
                            }
                        } else {
                            setButtonLoadingText(false);
                            errorTextView.setText(data.getString("message"));
                            return;
                        }

                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                        errorTextView.setText("Technical error, try again later.");

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //navigateToHomePage(view);
                        }
                    });
                }
            });
        } catch (Exception e) {
        }

    }

    public boolean validateInputs(String username, String password) {

        if (username.isEmpty()) {
            return false;
        }
        if (password.isEmpty()) {
            return false;
        }
        return true;
    }

    // Navigation methods for moving between UI views
    public void navigateToHomePage(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    // Navigation methods for moving between UI views
    public void navigateToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}