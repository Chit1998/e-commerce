package com.tourtraveling.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.util.HashMap;
import java.util.Objects;


public class LoginScreensActivity extends AppCompatActivity {

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth auth;
    private TextView button_logIn, text_signUp;
    private EditText emailAddress, password;
    private UserPreferences preferences;
    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screens);
        Constants.permissions(LoginScreensActivity.this, LoginScreensActivity.this);
        init();
        auth = FirebaseAuth.getInstance();
        text_signUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginOptionsActivity.class)));
        validation();
        button_logIn.setOnClickListener(v -> {
            if (emailAddress.getText().toString().isEmpty()){
                emailAddress.setError(Constants.EMPTY);
            }else if (password.getText().toString().isEmpty()){
                password.setError(Constants.EMPTY);
            }else {
                loginUser(emailAddress.getText().toString().trim(), password.getText().toString());
            }
        });
    }

    private void validation() {
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(s.toString().matches(emailPattern))) {
                    emailAddress.setError(Constants.EMAIL_VALID);
                }else if (s.toString().isEmpty()){
                    emailAddress.setError(Constants.EMPTY);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8) {
                    password.setError(Constants.PASSWORD_LIMIT);
                }else if (s.toString().isEmpty()){
                    password.setError(Constants.EMPTY);
                }
            }
        });
    }

    private void init(){
        button_logIn = findViewById(R.id.button_logIn);
        text_signUp = findViewById(R.id.text_signUp);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);

        preferences = new UserPreferences(this);
        data = preferences.getUserData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_PERMISSIONS && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Constants.setMessage(Constants.PERMISSION_GRANTED,this);
            }else if (grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Constants.setMessage(Constants.PERMISSION_GRANTED,this);
            }else if (grantResults[2] == PackageManager.PERMISSION_GRANTED){
                Constants.setMessage(Constants.PERMISSION_GRANTED,this);
            }else if (grantResults[3] == PackageManager.PERMISSION_GRANTED){
                Constants.setMessage(Constants.PERMISSION_GRANTED,this);
            }else {
                Constants.setMessage(Constants.PERMISSION_DENIED,this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        userVerification(task.getResult().getUser());
                    }
                }).addOnFailureListener(e -> {
                    Log.d(Constants.TAG_LON_IN, "onFailure: %s"+e.getMessage());
                    Constants.setMessage(e.getMessage(),LoginScreensActivity.this);
                });
    }

    private void userVerification(FirebaseUser user) {

        if (user != null){
//            if (user.isEmailVerified()){
                FirebaseFirestore.getInstance().collection(Constants.USER_DB)
                        .document(Objects.requireNonNull(auth.getUid()))
                        .get()
                        .addOnCompleteListener(task -> {
                            if (Objects.equals(task.getResult().getString(Constants.USER_TYPE), "customer")){
                                preferences.setUserType(task.getResult().getString(Constants.USER_TYPE));
                                startActivity(new Intent(getApplicationContext(), CustomerMainActivity.class));
                                finish();
                            }else if (Objects.equals(task.getResult().getString(Constants.USER_TYPE), "admin")){
                                preferences.setUserType(task.getResult().getString(Constants.USER_TYPE));
                                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(e -> {
                            auth.signOut();
                            Log.d(Constants.TAG_LON_IN, "userVerification: %s"+e.getMessage());
                        });
//            }else {
//                user.sendEmailVerification()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()){
//                                auth.signOut();
//                                Constants.setMessage(Constants.VERIFICATION_MAIL,LoginScreensActivity.this);
//                            }
//                        })
//                        .addOnFailureListener(e -> {
//                            auth.signOut();
//                            Constants.setMessage(Constants.EMAIL_VALID, LoginScreensActivity.this);
//                        });
//            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            if (data.get(Constants.USER_TYPE).equals("customer")){
                startActivity(new Intent(getApplicationContext(), CustomerMainActivity.class));
                finish();
            }else if (data.get(Constants.USER_TYPE).equals("admin")){
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}