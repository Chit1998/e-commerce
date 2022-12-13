package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.tourtraveling.R;
import com.tourtraveling.models.AddProductModel;
import com.tourtraveling.models.OrderModel;
import com.tourtraveling.utils.Constants;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class Product_BillingActivity extends AppCompatActivity implements PaymentResultListener {

    ImageView image_product;
    TextView text_product_title, text_product_subtitle, text_subtraction, text_product_item_value,
            text_addition, text_product_amt, text_total_amt, text_user_full_name, text_full_address,
            text_full_address_pin, text_phoneNumber,text_buy_product_billing;
    int i = 1;
    int amt;

    private FirebaseAuth auth;
    private AddProductModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_billing);
        init();

        model = (AddProductModel) getIntent().getSerializableExtra("bill");

        Glide.with(this)
                .load(model.getProduct_image_url())
                .into(image_product);
        text_product_title.setText(model.getProduct_name());
        text_product_subtitle.setText(model.getProduct_sub_title());
        text_product_amt.setText(model.getProduct_price());

        text_total_amt.setText(model.getProduct_price());
        text_subtraction.setOnClickListener(v -> {
            if (i > 1){
                i--;
                text_product_item_value.setText(String.valueOf(i));
                amt = amt - Integer.parseInt(model.getProduct_price()) ;
                text_total_amt.setText(String.valueOf(amt));
            }
        });
        amt = Integer.parseInt(model.getProduct_price());
        text_addition.setOnClickListener(v -> {
            if(i < Integer.parseInt(model.getProduct_quantity())){
                i++;
                text_product_item_value.setText(String.valueOf(i));
                amt = Integer.parseInt(model.getProduct_price()) * i;
                text_total_amt.setText(String.valueOf(amt));
            }

            if (i == Integer.parseInt(model.getProduct_quantity())){
                text_addition.setEnabled(false);
            }
        });


        text_buy_product_billing.setOnClickListener(v -> paymentProduct());

        getAddressData();
    }

    private void init(){
        auth = FirebaseAuth.getInstance();
        image_product = findViewById(R.id.image_product);
        text_product_title = findViewById(R.id.text_product_title);
        text_product_subtitle = findViewById(R.id.text_product_subtitle);
        text_subtraction = findViewById(R.id.text_subtraction);
        text_product_item_value = findViewById(R.id.text_product_item_value);
        text_addition = findViewById(R.id.text_addition);
        text_product_amt = findViewById(R.id.text_product_amt);
        text_total_amt = findViewById(R.id.text_total_amt);
        text_user_full_name = findViewById(R.id.text_user_full_name);
        text_full_address = findViewById(R.id.text_full_address);
        text_full_address_pin = findViewById(R.id.text_full_address_pin);
        text_phoneNumber = findViewById(R.id.text_phoneNumber);
        text_buy_product_billing = findViewById(R.id.text_buy_product_billing);
    }

    @SuppressLint("SetTextI18n")
    private void getAddressData() {
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document("address")
                .collection(auth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    for (DocumentSnapshot snapshot : task.getResult()){
                        String address = snapshot.getString("address");
                        String city = snapshot.getString("city");
                        String country = snapshot.getString("country");
                        String name = snapshot.getString("full_name");
                        String phone = snapshot.getString("phone");
                        String state = snapshot.getString("state");
                        boolean stateSelected = snapshot.getBoolean("stateSelected");
                        String zipcode = snapshot.getString("zipcode");

                        if (stateSelected == true) {
                            text_full_address.setText(address+", "+city
                            +", "+state+", "+country);
                            text_full_address_pin.setText(zipcode);
                            text_user_full_name.setText(name);
                            text_phoneNumber.setText(phone);
                        }
                    }
                });
    }

    private void paymentProduct(){
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.logo);
        try {
            JSONObject object = new JSONObject();
            object.put("name",text_user_full_name.getText().toString());
            object.put("description",text_product_subtitle.getText().toString());
            object.put("send_sms_hash",true);
            object.put("allow_rotation",false);
            object.put("currency","INR");
            object.put("amount",String.valueOf(Integer.parseInt(text_total_amt.getText().toString())*100));

            JSONObject prefill = new JSONObject();
            prefill.put("email", auth.getCurrentUser().getEmail());
            prefill.put("contact", text_phoneNumber.getText().toString());
            object.put("prefill", prefill);

            checkout.open(Product_BillingActivity.this, object);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        OrderModel orderModel = new OrderModel();
        orderModel.setCurrent_date(new Date(System.currentTimeMillis()));
        orderModel.setFull_address(text_full_address.getText().toString());
        orderModel.setName(text_user_full_name.getText().toString());
        orderModel.setPhone(text_phoneNumber.getText().toString());
        orderModel.setProduct_category(model.getProduct_category());
        orderModel.setProduct_price(text_total_amt.getText().toString());
        orderModel.setProduct_max_price(model.getProduct_max_price());
        orderModel.setProduct_quantity(text_product_item_value.getText().toString());
        orderModel.setProduct_name(model.getProduct_name());
        orderModel.setProduct_sub_title(model.getProduct_sub_title());
        orderModel.setProduct_description(model.getProduct_description());
        orderModel.setProduct_image_url(model.getProduct_image_url());
        orderModel.setUid(model.getUid());
        orderModel.setZipcode(text_full_address_pin.getText().toString());
        orderModel.setTransaction_id(s);
        orderModel.setTransaction_email(auth.getCurrentUser().getEmail());

        FirebaseFirestore.getInstance()
                        .collection("orders")
                                .document("order")
                                        .collection(auth.getUid())
                                                .document()
                                                        .set(orderModel);
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document("order")
                .collection(model.getUid())
                .document()
                .set(orderModel)
                .addOnCompleteListener( task2 ->{
                    Constants.setMessage("success", Product_BillingActivity.this);
                    startActivity(new Intent(getApplicationContext(),CustomerMainActivity.class));
                    finish();
                });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "failed "+s, Toast.LENGTH_SHORT).show();
    }
}