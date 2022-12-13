package com.tourtraveling.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.adapters.CartAdapter;
import com.tourtraveling.models.AddProductModel;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView_cart;
    private CartAdapter adapter;
    private List<AddProductModel> list;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        auth = FirebaseAuth.getInstance();
        recyclerView_cart = findViewById(R.id.recyclerView_cart);

        list =  new ArrayList<>();
        adapter = new CartAdapter(this, list);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_cart.setAdapter(adapter);

        getCartData();
    }

    private void getCartData(){
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document("carts")
                .collection(auth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    for (DocumentSnapshot snapshot: task.getResult()){
                        AddProductModel model = new AddProductModel(
                                snapshot.getString("product_name"),
                                snapshot.getString("product_sub_title"),
                                snapshot.getString("product_quantity"),
                                snapshot.getString("product_max_price"),
                                snapshot.getString("product_price"),
                                snapshot.getString("product_description"),
                                snapshot.getString("product_image_url"),
                                snapshot.getString("product_category"),
                                Boolean.TRUE.equals(snapshot.getBoolean("product_banner_image")),
                                snapshot.getString("uid"),
                                snapshot.getDate("current_date"));
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}