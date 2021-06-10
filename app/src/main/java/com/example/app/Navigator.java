package com.example.app;

import android.content.Context;
import android.content.Intent;

import com.example.data_models.User;
import com.example.view.MainActivity;
import com.example.view.authentication.LoginActivity;
import com.example.view.home.CustomerHomeActivity;
import com.example.view.home.StaffHomeActivity;

public class Navigator {
    public static void handleUserBasedNavigation(Context context, User user) {
        Intent intent;
        if (user == null) {
            intent = new Intent(context, LoginActivity.class);
        } else if (user.userType.equals(User.customerUserType)) {
            intent = new Intent(context, CustomerHomeActivity.class);

        } else {
            intent = new Intent(context, StaffHomeActivity.class);
        }
        context.startActivity(intent);
    }
}
