package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourtraveling.R;
import com.tourtraveling.models.OrderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CustomerOrderProductDetailsActivity extends AppCompatActivity {

    RoundedImageView image_product_customer;
    TextView text_product_title_customer,text_product_subtitle_customer,text_product_price_customer,
            text_product_date_customer,text_product_quantity_customer,text_product_transaction_email_customer,
            text_product_transaction_id_customer,text_full_address, text_full_zipcode, text_full_name,
            text_phone;

    @SuppressLint({"SimpleDateFormat", "WeekBasedYear"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_product_details);
        init();

        OrderModel model = (OrderModel) getIntent().getSerializableExtra("order");

        Glide.with(this)
                .load(model.getProduct_image_url())
                .centerCrop()
                .into(image_product_customer);

        text_product_title_customer.setText(model.getProduct_name());
        text_product_subtitle_customer.setText(model.getProduct_sub_title());
        text_product_transaction_email_customer.setText(model.getTransaction_email());
        text_product_transaction_id_customer.setText(model.getTransaction_id());
        text_full_address.setText(model.getFull_address());
        text_full_zipcode.setText(model.getZipcode());
        text_full_name.setText(model.getName());
        text_phone.setText(model.getPhone());
        text_product_quantity_customer.setText(model.getProduct_quantity());
        text_product_price_customer.setText("\u20B9 "+model.getProduct_price());
        text_product_date_customer.setText(new SimpleDateFormat("dd-MM-YYYY").format(model.getCurrent_date()));

    }

    private void init(){
        image_product_customer = findViewById(R.id.image_product_customer);
        text_product_title_customer = findViewById(R.id.text_product_title_customer);
        text_product_subtitle_customer = findViewById(R.id.text_product_subtitle_customer);
        text_product_price_customer = findViewById(R.id.text_product_price_customer_);
        text_product_date_customer = findViewById(R.id.text_product_date_customer_);
        text_product_quantity_customer = findViewById(R.id.text_product_quantity_customer_);
        text_product_transaction_email_customer = findViewById(R.id.text_product_transaction_email_customer_);
        text_product_transaction_id_customer = findViewById(R.id.text_product_transaction_id_customer_);
        text_full_address = findViewById(R.id.text_full_address);
        text_full_zipcode = findViewById(R.id.text_full_zipcode);
        text_full_name = findViewById(R.id.text_full_name);
        text_phone = findViewById(R.id.text_phone);
    }
}