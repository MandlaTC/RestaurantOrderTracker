package com.example.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.model.AuthRepository;
import com.example.testrequests.R;

public class CustomerHomeActivity extends AppCompatActivity {

    TextView logOutButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home_layout);
        context = this;
        logOutButton = findViewById(R.id.customer_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRepository.logOutUser(context);
            }
        });
    }
}
