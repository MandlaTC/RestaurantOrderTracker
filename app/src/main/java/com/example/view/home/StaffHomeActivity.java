package com.example.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.data_models.User;
import com.example.model.ApiCall;
import com.example.model.AuthRepository;
import com.example.testrequests.R;
import com.example.view.staff_fragments.AddOrderFragment;
import com.example.view.staff_fragments.ViewOrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class StaffHomeActivity extends FragmentActivity {
    TextView logOutButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_bottom_navigation_view);
        context = this;
        getUser();
        initBottomNavBar();

    }

    public void setLogOutButton() {
        logOutButton = findViewById(R.id.staff_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRepository.logOutUser(context);
            }
        });
    }



    public void initBottomNavBar() {

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        openFragment(new AddOrderFragment());


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_viewOrders:
                        openFragment(new ViewOrdersFragment());
                        return true;

                    case R.id.navigation_addOrder:
                        openFragment(new AddOrderFragment());
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
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}

