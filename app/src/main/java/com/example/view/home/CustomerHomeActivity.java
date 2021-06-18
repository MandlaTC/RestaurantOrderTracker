package com.example.view.home;


import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.customer_fragments.PastOrdersFragment;
import com.example.customer_fragments.UpcomingOrdersFragment;
import com.example.data_models.User;
import com.example.model.AuthRepository;
import com.example.orderTracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerHomeActivity extends FragmentActivity {
    TextView logOutButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_bottom_navigation_view);
        context = this;
        getUser();
        initBottomNavBar();

    }

    public void setLogOutButton() {
        logOutButton = findViewById(R.id.customer_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRepository.logOutUser(context);
            }
        });
    }


    public void initBottomNavBar() {

        BottomNavigationView bottomNav = findViewById(R.id.customer_bottom_nav);

        openFragment(new PastOrdersFragment());

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.customer_navigation_past_orders:
                        openFragment(new PastOrdersFragment());
                        return true;

                    case R.id.customer_navigation_upcoming_orders:
                        openFragment(new UpcomingOrdersFragment());
                        return true;

                }

                return false;
            }
        });


    }

    public void getUser() {
        User user = AuthRepository.getSavedUserFromPreference(context);
        if (user != null) {
            System.out.println(user.toString());
        }
    }

    void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.customer_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}

