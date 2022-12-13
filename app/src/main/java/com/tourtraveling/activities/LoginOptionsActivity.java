package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.models.UserModel;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.util.Objects;

public class LoginOptionsActivity extends AppCompatActivity {

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseAuth auth;
    private RadioGroup radioGroup;
    private EditText name, emailAddress, password;
    private TextView button_signUp,text_signIn;
    private String radioStr = null;
    private UserPreferences preferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        auth = FirebaseAuth.getInstance();
        init();
        text_signIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginScreensActivity.class)));
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.radioCustomer:
                    radioStr = "customer";
                    break;
                case R.id.radioAdmin:
                    radioStr = "admin";
                    break;
            }
            Log.d(Constants.TAG_LON_OPTION, "onCreate: "+radioStr);
        });
        validation();
        button_signUp.setOnClickListener(v -> {
            try {
                if (emailAddress.getText().toString().isEmpty()){
                    emailAddress.setError(Constants.EMPTY);
                }else if (password.getText().toString().isEmpty()){
                    password.setError(Constants.EMPTY);
                }else if (name.getText().toString().isEmpty()){
                    password.setError(Constants.EMPTY);
                }else if (radioStr == null){
                    Constants.setMessage(radioStr,LoginOptionsActivity.this);
                }else {
                    registerUser(emailAddress.getText().toString().trim(), password.getText().toString());
                }
            }catch (Exception e){
                Constants.setMessage("Select radio button "+e.getMessage(),LoginOptionsActivity.this);
            }
        });


    }

    private void init(){
        text_signIn = findViewById(R.id.text_signIn);
        radioGroup = findViewById(R.id.radioGroup);
        name = findViewById(R.id.name);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        button_signUp = findViewById(R.id.button_signUp);
        preferences = new UserPreferences(this);
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

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    name.setError(Constants.EMPTY);
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

    private void registerUser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
//                        sendVerificationMail(Objects.requireNonNull(task.getResult().getUser()));
                        sendUserDataIntoDB();
                    }
                })
                .addOnFailureListener(e -> {
                    auth.signOut();
                    Log.d(Constants.TAG_LON_OPTION, "registerUser: "+e.getMessage());
                    Constants.setMessage(e.getMessage(), LoginOptionsActivity.this);
                });
    }

    private void sendVerificationMail(FirebaseUser user) {
//        if (user != null){
            user = auth.getCurrentUser();
            assert user != null;
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
//                            sendUserDataIntoDB();
                            Constants.setMessage("done "+task.getResult(), LoginOptionsActivity.this);
                            Log.d(Constants.TAG_LON_OPTION, "sendVerificationMail: " + task.getResult());
                            auth.signOut();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d(Constants.TAG_LON_OPTION, "sendVerificationMail: "+e.getMessage());
                        Constants.setMessage(e.getMessage(), LoginOptionsActivity.this);
                        auth.signOut();
                    });
//        }
    }

    private void sendUserDataIntoDB() {
        UserModel model = new UserModel(name.getText().toString(),emailAddress.getText().toString(), radioStr,auth.getUid());
        FirebaseFirestore.getInstance().collection(Constants.USER_DB)
                .document(Objects.requireNonNull(auth.getUid()))
                .set(model).addOnCompleteListener(task -> {
//                    preferences.setUserType(radioStr);
//                    preferences.setUserData(emailAddress.getText().toString(),auth.getUid(),name.getText().toString()); verification mail has been sent
                    Constants.setMessage("Successfully registered..!", LoginOptionsActivity.this);
                    auth.signOut();
                    preferences.dataClear();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.d(Constants.TAG_LON_OPTION, "sendUserDataIntoDB: "+e.getMessage());
                    Constants.setMessage(e.getMessage(), LoginOptionsActivity.this);
                });
    }




}