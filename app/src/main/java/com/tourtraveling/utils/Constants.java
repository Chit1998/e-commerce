package com.tourtraveling.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class Constants {
    public static final String TAG_MAIN = "MainActivity";
    public static final String TAG_LON_IN = "loginActivity";
    public static final String TAG_LON_OPTION = "loginOptionActivity";
    public static final String TAG_ADD_PRODUCT = "addProductActivity";
    public static final String PREFERENCES = "tour";
    public static final int REQUEST_PERMISSIONS = 100;
    public static final String PERMISSION_GRANTED = "Permission granted";
    public static final String PERMISSION_DENIED = "Permission denied";
    public static final String EMAIL_ADDRESS = "email";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PASSWORD_LIMIT = "Required password length is 8";
    public static final String EMAIL_VALID = "Enter a valid email";
    public static final String  EMPTY = "Empty";
    public static final String  USER_TYPE = "userType";
    public static final String VERIFICATION_MAIL = "Verification Mail has been send..! check your email id.";
    public static final String USER_DB = "users";
    public static final String USER_ID = "uid";
    public static final String FAILED = "Failed";


    public static void permissions(Context context, Activity activity){
        if (
                ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    REQUEST_PERMISSIONS
            );
        }
    }

    public static void setMessage(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
