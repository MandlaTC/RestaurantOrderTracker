package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.app.Navigator;
import com.example.data_models.User;
import com.example.model.AuthRepository;
import com.example.testrequests.R;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 3000; //This is 3 seconds
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page_layout);

        //This is additional feature, used to run a progress bar
        splashProgress = findViewById(R.id.splashProgress);
        playProgress();
        context = this;
        //Code to start timer and take action after the timer ends
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                //TODO check persistent and user type here to determine whether to login or show dashboard
                Navigator.handleUserBasedNavigation(context, getUserObject());
                //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                finish();

            }
        }, SPLASH_TIME);
    }

    public User getUserObject() {
        User user = AuthRepository.getSavedUserFromPreference(context);
        if (user != null)
        System.out.println(user.toString());
        return  user;


    }

    //Method to run progress bar for 5 seconds
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(5000)
                .start();
    }
}
