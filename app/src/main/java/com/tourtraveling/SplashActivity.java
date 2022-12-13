package com.tourtraveling;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tourtraveling.activities.AdminMainActivity;
import com.tourtraveling.activities.CustomerMainActivity;
import com.tourtraveling.activities.LoginScreensActivity;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private UserPreferences preferences;
    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        preferences = new UserPreferences(this);
        data = preferences.getUserData();

        new Handler().postDelayed(()->{
            if (auth.getCurrentUser() != null){
                if (data.get(Constants.USER_TYPE).equals("customer")){
                    startActivity(new Intent(getApplicationContext(), CustomerMainActivity.class));
                    finish();
                }else if (data.get(Constants.USER_TYPE).equals("admin")){
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                    finish();
                }
            }else {
                startActivity(new Intent(getApplicationContext(), LoginScreensActivity.class));
                finish();
            }
        },2000);
    }

}