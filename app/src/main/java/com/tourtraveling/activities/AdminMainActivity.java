package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.activities.admin.AboutActivity;
import com.tourtraveling.activities.admin.AddProductActivity;
import com.tourtraveling.activities.admin.AdminFeedbackActivity;
import com.tourtraveling.activities.admin.CompletedActivity;
import com.tourtraveling.activities.admin.OrderListActivity;
import com.tourtraveling.activities.admin.ProductActivity;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.util.Objects;


public class AdminMainActivity extends AppCompatActivity {

    private TextView text_username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init();

        text_username = findViewById(R.id.text_username);

        FirebaseFirestore.getInstance()
                        .collection(Constants.USER_DB)
                                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .get()
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()){
                                                        String username = task.getResult().getString("name");
                                                        text_username.setText(username);
                                                    }
                                                });

        findViewById(R.id.person_profile_view)
                .setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfileActivity.class)));

        findViewById(R.id.person_add_product_view)
                .setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProductActivity.class)));

        findViewById(R.id.person_orders_list_view)
                .setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), OrderListActivity.class)));

        findViewById(R.id.person_orders_complete_view)
                .setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CompletedActivity.class)));

        findViewById(R.id.image_exit)
                .setOnClickListener(v -> {
                    FirebaseAuth.getInstance().signOut();
                    new UserPreferences(this).dataClear();
                    startActivity(new Intent(getApplicationContext(), LoginScreensActivity.class));
                    finish();
                });

        findViewById(R.id.person_feedback_view)
                .setOnClickListener(v ->
                        startActivity(new Intent(getApplicationContext(), AdminFeedbackActivity.class)));

        findViewById(R.id.person_About_view)
                .setOnClickListener(v ->
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class)));


    }

    private void init(){
    }
}