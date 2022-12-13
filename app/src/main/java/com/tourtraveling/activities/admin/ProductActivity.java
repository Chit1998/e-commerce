package com.tourtraveling.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.adapters.ProductAdapter;
import com.tourtraveling.interfaces.ProductListener;
import com.tourtraveling.models.AddProductModel;
import com.tourtraveling.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity implements ProductListener {

    private List<AddProductModel> list;
    ProductAdapter adapter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        auth = FirebaseAuth.getInstance();
        RecyclerView recyclerView_product = findViewById(R.id.recyclerView_product);

        list = new ArrayList<>();
        adapter = new ProductAdapter(this, list,this);
        recyclerView_product.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_product.setAdapter(adapter);

        findViewById(R.id.image_save_product)
                .setOnClickListener(v -> startActivity(new Intent(
                        getApplicationContext(), AddProductActivity.class
                )));

        getProduct();

    }

    private void getProduct() {
        FirebaseFirestore.getInstance()
                .collection(Objects.requireNonNull(auth.getUid()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            String product_name = snapshot.getString("product_name");
                            boolean banner_image = Boolean.TRUE.equals(snapshot.getBoolean("banner_image"));
                            String product_category = snapshot.getString("product_category");
                            String product_description = snapshot.getString("product_description");
                            String product_image_url = snapshot.getString("product_image_url");
                            String product_max_price = snapshot.getString("product_max_price");
                            String product_price = snapshot.getString("product_price");
                            String product_quantity = snapshot.getString("product_quantity");
                            String product_sub_title = snapshot.getString("product_sub_title");
                            Date product_date = snapshot.getDate("current_date");

                            AddProductModel model = new AddProductModel(product_name,product_sub_title,
                                    product_quantity,product_max_price,product_price,product_description,
                                    product_image_url,product_category,banner_image, product_date);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Constants.setMessage(e.getMessage(), ProductActivity.this);
                });
    }

    @Override
    public void onClickListener(AddProductModel model) {
        Constants.setMessage(model.isBanner_image()+"", ProductActivity.this);
    }

    @Override
    public void onLongClickListener(AddProductModel model) {
        Constants.setMessage(model.getProduct_description(), ProductActivity.this);
    }
}