package com.tourtraveling.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.tourtraveling.utils.Constants;

import java.util.HashMap;

public class UserPreferences {
    private SharedPreferences preferences;
    private Context context;
    private SharedPreferences.Editor editor;

    public UserPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setUserType(String userType){
        editor.putString(Constants.USER_TYPE, userType);
        editor.commit();
    }

    public void setUserData(String email, String uid, String name){
        editor.putString(Constants.EMAIL_ADDRESS, email);
        editor.putString(Constants.NAME, name);
        editor.putString(Constants.USER_ID, uid);
        editor.commit();
    }

    public HashMap<String, String> getUserData(){
        HashMap<String, String> data = new HashMap<>();

        data.put(Constants.EMAIL_ADDRESS, preferences.getString(Constants.EMAIL_ADDRESS,null));
        data.put(Constants.NAME, preferences.getString(Constants.NAME,null));
        data.put(Constants.PHONE_NUMBER, preferences.getString(Constants.PHONE_NUMBER,null));
        data.put(Constants.USER_ID, preferences.getString(Constants.USER_ID,null));
        data.put(Constants.USER_TYPE, preferences.getString(Constants.USER_TYPE,null));

        return data;
    }

    public void dataClear(){
        editor.clear();
        editor.commit();
    }
}
