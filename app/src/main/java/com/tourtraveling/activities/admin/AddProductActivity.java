package com.tourtraveling.activities.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.tourtraveling.R;
import com.tourtraveling.models.AddProductModel;
import com.tourtraveling.utils.Constants;

import java.util.Date;
import java.util.Objects;

public class AddProductActivity extends AppCompatActivity {

    private EditText eProductName,eProductSubTitle,eProductQuantity,eProductMaxPrice,
            eProductPrice,eProductDescription;
    private ImageView image_selected_view;
    private StorageReference imageRef;
    private CheckBox check_box_product_slider;
    private String url = null;
    private boolean checkBoxValue;
    private Spinner category_spinner;
    private FirebaseAuth auth;
    private String product_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
        validation();

        String[] reason_array = getResources().getStringArray(R.array.category);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AddProductActivity.this,R.layout.spinner_text_layout, R.id.text_spinner ,reason_array);
        category_spinner.setAdapter(adapter);

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    TextView text = view.findViewById(R.id.text_spinner);
                    text.setTextColor(Color.BLACK);
                    product_category = category_spinner.getItemAtPosition(position).toString();
                    category_spinner.setBackgroundResource(R.drawable.edit_background);
                }

                if (position == 0){
                    category_spinner.setBackgroundResource(R.drawable.edit_wrong_background);
                    product_category = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.image_save_add_product)
                .setOnClickListener(v -> {
                    try {
                        checkBoxValue = check_box_product_slider.isChecked();

                        if (url != null){
                            findViewById(R.id.text_image_selection).setBackgroundResource(R.drawable.edit_background);
                        }

                        if (url == null){
                            findViewById(R.id.text_image_selection).setBackgroundResource(R.drawable.edit_wrong_background);
                            Constants.setMessage("Image is not selected ",AddProductActivity.this);
                        }else if (eProductName.getText().toString().isEmpty()){
                            eProductName.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else if (product_category == null){
                            category_spinner.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else if (eProductSubTitle.getText().toString().isEmpty()){
                            eProductSubTitle.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else if (eProductQuantity.getText().toString().isEmpty()){
                            eProductQuantity.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else if (eProductMaxPrice.getText().toString().isEmpty()){
                            eProductMaxPrice.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else if (eProductPrice.getText().toString().isEmpty()){
                            eProductPrice.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else if (eProductDescription.getText().toString().isEmpty()){
                            eProductDescription.setBackgroundResource(R.drawable.edit_wrong_background);
                        }else{
                            saveProductData(
                                    eProductName.getText().toString(),
                                    eProductSubTitle.getText().toString(),
                                    eProductQuantity.getText().toString(),
                                    eProductMaxPrice.getText().toString(),
                                    eProductPrice.getText().toString(),
                                    eProductDescription.getText().toString(),
                                    url, checkBoxValue, product_category
                            );
                        }
                    }catch (Exception e){
                        Log.d(Constants.TAG_ADD_PRODUCT, "onCreate: "+e.getMessage());
                        Constants.setMessage("Image is not selected "+e.getMessage(),AddProductActivity.this);
                    }
                });
    }

    private void validation() {
        eProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    eProductName.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    eProductName.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        eProductSubTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    eProductSubTitle.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    eProductSubTitle.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        eProductQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    eProductQuantity.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    eProductQuantity.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        eProductMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    eProductMaxPrice.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    eProductMaxPrice.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        eProductPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    eProductPrice.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    eProductPrice.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        eProductDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    eProductDescription.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    eProductDescription.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });
    }

    private void init(){
        eProductName = findViewById(R.id.eProductName);
        eProductSubTitle = findViewById(R.id.eProductSubTitle);
        eProductQuantity = findViewById(R.id.eProductQuantity);
        eProductMaxPrice = findViewById(R.id.eProductMaxPrice);
        eProductPrice = findViewById(R.id.eProductPrice);
        eProductDescription = findViewById(R.id.eProductDescription);
        check_box_product_slider = findViewById(R.id.check_box_product_slider);
        image_selected_view = findViewById(R.id.image_selected_view);
        category_spinner = findViewById(R.id.category_spinner);
        auth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        imageRef = storage.getReference().child("image").child(Objects.requireNonNull(auth.getUid()));
        findViewById(R.id.text_image_selection)
                .setOnClickListener(v -> {
                    CropImage.activity()
                            .setAspectRatio(16,9).start(AddProductActivity.this);
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri image_uri = result.getUri();
                imageRef.child("IMG_"+System.currentTimeMillis())
                        .putFile(image_uri)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                task
                                        .getResult()
                                        .getStorage()
                                        .getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            Glide
                                                    .with(this)
                                                    .load(uri)
                                                    .centerCrop()
                                                    .into(image_selected_view);
                                            image_selected_view.setVisibility(View.VISIBLE);
                                            url = uri.toString();
                                        })
                                        .addOnFailureListener(e -> {
                                            Constants.setMessage(e.getMessage(),AddProductActivity.this);
                                            Log.d(Constants.TAG_ADD_PRODUCT, "onFailure: "+e.getMessage());
                                        });
                            }else {
                                Constants.setMessage(Constants.FAILED, AddProductActivity.this);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Constants.setMessage(e.getMessage(), AddProductActivity.this);
                        });
            }
        }
    }

    private void saveProductData(String product_name, String product_sub_title, String product_quantity,
                                 String product_max_price, String product_price, String product_description,
                                 String product_image_url, boolean banner_value, String category) {

        AddProductModel model = new AddProductModel(
                product_name, product_sub_title, product_quantity,
                product_max_price,product_price,product_description,
                product_image_url,category,banner_value, new Date(System.currentTimeMillis())
        );

        AddProductModel model2 = new AddProductModel(
                product_name, product_sub_title, product_quantity,
                product_max_price,product_price,product_description,
                product_image_url,category,banner_value,auth.getUid(),
        new Date(System.currentTimeMillis())
        );

        FirebaseFirestore.getInstance()
                .collection("products")
                .document()
                .set(model2)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseFirestore.getInstance()
                                .collection(Objects.requireNonNull(auth.getUid()))
                                .document()
                                .set(model)
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()){
                                        Constants.setMessage("successfully added", AddProductActivity.this);
                                        finish();
                                    }
                                });
                    }
                });


    }

}