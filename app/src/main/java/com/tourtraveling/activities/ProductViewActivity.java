package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.tourtraveling.R;
import com.tourtraveling.interfaces.ProductListener;
import com.tourtraveling.models.AddProductModel;
import com.tourtraveling.utils.Constants;

public class ProductViewActivity extends AppCompatActivity {

    ImageView image_product_view_details;
    TextView text_title_product_view_details, text_subtitle_product_view_details, text_price_value_product_view_details,
            text_max_price_value_product_view_details, text_discount_value_product_view_details, text_add_to_card_product_view_details,
            text_buy_product_view_details,text_description_value_product_view_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        init();

        AddProductModel productModel = (AddProductModel) getIntent().getSerializableExtra("product");

        getProductData(productModel);

        text_buy_product_view_details.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Product_BillingActivity.class)
                    .putExtra("bill", productModel));
        });

        text_add_to_card_product_view_details.setOnClickListener(v -> {
            FirebaseFirestore.getInstance()
                    .collection("orders")
                    .document("carts")
                    .collection(FirebaseAuth.getInstance().getUid())
                    .document()
                    .set(productModel)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Constants.setMessage("successfully added", ProductViewActivity.this);
                        }
                    });

        });

    }

    private void init(){
        image_product_view_details = findViewById(R.id.image_product_view_details);
        text_title_product_view_details = findViewById(R.id.text_title_product_view_details);
        text_subtitle_product_view_details = findViewById(R.id.text_subtitle_product_view_details);
        text_price_value_product_view_details = findViewById(R.id.text_price_value_product_view_details);
        text_max_price_value_product_view_details = findViewById(R.id.text_max_price_value_product_view_details);
        text_discount_value_product_view_details = findViewById(R.id.text_discount_value_product_view_details);
        text_add_to_card_product_view_details = findViewById(R.id.text_add_to_card_product_view_details);
        text_buy_product_view_details = findViewById(R.id.text_buy_product_view_details);
        text_description_value_product_view_details = findViewById(R.id.text_description_value_product_view_details);
    }

    private void getProductData(AddProductModel model) {

        Glide.with(this)
                .load(model.getProduct_image_url())
                .into(image_product_view_details);

        int discount_price = Integer.parseInt(model.getProduct_max_price()) - Integer.parseInt(model.getProduct_price());
        text_title_product_view_details.setText(model.getProduct_name());
        text_subtitle_product_view_details.setText(model.getProduct_sub_title());
        text_price_value_product_view_details.setText("\u20B9 "+model.getProduct_price());
        text_max_price_value_product_view_details.setText("\u20B9 "+model.getProduct_max_price());
        text_discount_value_product_view_details.setText("\u20B9 "+discount_price);
        text_description_value_product_view_details.setText(model.getProduct_description());
    }

}