package com.example.testrequests;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        View view = new View(this);
    }

    /*
    * Method to handle login requests.
    * Request sent to lamp.ms.wits.ac.za/home/s2303145/index.php
    * On success perform navigateToHomePage(View view)
    **/

    public void loginRequest(View view){
        // Creating OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Retrieve username and password
        TextInputEditText t = (TextInputEditText)findViewById(R.id.username_edit_text);
        String username = t.getText().toString();
        t = (TextInputEditText)findViewById(R.id.password_edit_text);
        String password = t.getText().toString();

        // Request object with username and password passed through into url
        Request request = new Request.Builder().url("https://lamp.ms.wits.ac.za/home/s2303145/index.php?username="+username+"&password="+password).build();

        // Client object method newCall() called assyncronously
        client.newCall(request).enqueue(new Callback() {

            // On failure print error to the signUpText Text view
            // TODO: comment out contents of onFailure method
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView)findViewById(R.id.signUpText);
                        tv.setText(tv.getText().toString()+" "+e);
                    }
                });
            }

            /*
             * On response the system print response data, and using UI thread navigateToHomePage()
             * method is called
             */
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String responseData = response.toString();
                System.out.println("Response: "+responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigateToHomePage(view);
                    }
                });
            }
        });


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