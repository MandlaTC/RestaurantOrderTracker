package com.example.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.model.AuthRepository;
import com.example.testrequests.R;

import org.w3c.dom.Text;

public class StaffHomeActivity extends AppCompatActivity {
    TextView logOutButton ;
    Context context;

 @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.staff_home_layout);
        context = this;
        logOutButton = findViewById(R.id.staff_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRepository.logOutUser(context);
            }
        });
    }
}

