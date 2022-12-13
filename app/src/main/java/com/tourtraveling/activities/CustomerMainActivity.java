package com.tourtraveling.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tourtraveling.R;
import com.tourtraveling.fragments.HomeFragment;
import com.tourtraveling.fragments.NotificationFragment;
import com.tourtraveling.fragments.ProfileFragment;
import com.tourtraveling.utils.Constants;

public class CustomerMainActivity extends AppCompatActivity {

    ImageView image_home, image_notification, image_profile;
    View viewBottomHomeToolbar, viewBottomNotificationToolbar, viewBottomProfileToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.permissions(CustomerMainActivity.this, CustomerMainActivity.this);
        init();

        viewBottomHomeToolbar.setOnClickListener(v -> {
            image_home.setImageResource(R.drawable.ic_home_white);
            viewBottomHomeToolbar.setBackgroundColor(getResources().getColor(R.color.purple_500));

//            image_notification.setImageResource(R.drawable.ic_notifications_blue);
//            viewBottomNotificationToolbar.setBackgroundColor(getResources().getColor(R.color.white));

            image_profile.setImageResource(R.drawable.ic_person_blue);
            viewBottomProfileToolbar.setBackgroundColor(getResources().getColor(R.color.white));

            setFragment(new HomeFragment());
        });

//        viewBottomNotificationToolbar.setOnClickListener(v -> {
//            image_notification.setImageResource(R.drawable.ic_notifications_white);
//            viewBottomNotificationToolbar.setBackgroundColor(getResources().getColor(R.color.purple_500));
//
//            image_home.setImageResource(R.drawable.ic_home_blue);
//            viewBottomHomeToolbar.setBackgroundColor(getResources().getColor(R.color.white));
//
//            image_profile.setImageResource(R.drawable.ic_person_blue);
//            viewBottomProfileToolbar.setBackgroundColor(getResources().getColor(R.color.white));
//            setFragment(new NotificationFragment());
//        });

        viewBottomProfileToolbar.setOnClickListener(v -> {
            image_profile.setImageResource(R.drawable.ic_person_white);
            viewBottomProfileToolbar.setBackgroundColor(getResources().getColor(R.color.purple_500));

//            image_notification.setImageResource(R.drawable.ic_notifications_blue);
//            viewBottomNotificationToolbar.setBackgroundColor(getResources().getColor(R.color.white));

            image_home.setImageResource(R.drawable.ic_home_blue);
            viewBottomHomeToolbar.setBackgroundColor(getResources().getColor(R.color.white));
            setFragment(new ProfileFragment());
        });

    }

    private void init(){
        image_home = findViewById(R.id.image_home);
//        image_notification = findViewById(R.id.image_notification);
        image_profile = findViewById(R.id.image_profile);
        viewBottomHomeToolbar = findViewById(R.id.viewBottomHomeToolbar);
//        viewBottomNotificationToolbar = findViewById(R.id.viewBottomNotificationToolbar);
        viewBottomProfileToolbar = findViewById(R.id.viewBottomProfileToolbar);

        image_home.setImageResource(R.drawable.ic_home_white);
        viewBottomHomeToolbar.setBackgroundColor(getResources().getColor(R.color.purple_500));

//        image_notification.setImageResource(R.drawable.ic_notifications_blue);
//        viewBottomNotificationToolbar.setBackgroundColor(getResources().getColor(R.color.white));

        image_profile.setImageResource(R.drawable.ic_person_blue);
        viewBottomProfileToolbar.setBackgroundColor(getResources().getColor(R.color.white));

        setFragment(new HomeFragment());
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

    private void setFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}