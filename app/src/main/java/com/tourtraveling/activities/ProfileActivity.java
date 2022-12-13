package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private TextView text_user_name_profile,text_email_profile,
            text_user_type_profile,text_password_profile;

    private FirebaseAuth auth;

    private UserPreferences preferences;
    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();

        FirebaseFirestore.getInstance().collection(Constants.USER_DB)
                .document(Objects.requireNonNull(auth.getUid()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String name = task.getResult().getString(Constants.NAME);
                        String userType = task.getResult().getString(Constants.USER_TYPE);
                        String email = task.getResult().getString(Constants.EMAIL_ADDRESS);

                        text_user_name_profile.setText(name);
                        text_email_profile.setText(email);
                        text_user_type_profile.setText(userType);
                    }
                });
    }

    private void init(){
        auth = FirebaseAuth.getInstance();
        text_user_name_profile = findViewById(R.id.text_user_name_profile);
        text_email_profile = findViewById(R.id.text_email_profile);
        text_user_type_profile = findViewById(R.id.text_user_type_profile);
        text_password_profile = findViewById(R.id.text_password_profile);
        preferences = new UserPreferences(this);
        data = preferences.getUserData();
    }
}