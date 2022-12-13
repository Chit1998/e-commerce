package com.tourtraveling.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.adapters.AdminOrderAdapter;
import com.tourtraveling.adapters.OrderAdapter;
import com.tourtraveling.models.OrderModel;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private TextView text_order_page;
    private HashMap<String, String> userData;
    private RecyclerView recyclerView_orders;
    private AdminOrderAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private List<OrderModel> list;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        init();

        if (userData.get(Constants.USER_TYPE).equals("admin")){
            text_order_page.setText("My Order List");
        }else {
            text_order_page.setText("My Order");
        }

        list = new ArrayList<>();
        adapter = new AdminOrderAdapter(this, list);
        recyclerView_orders.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_orders.setAdapter(adapter);


        try {
            getOrderData();
        }catch (Exception e){
            Constants.setMessage(e.getMessage(), OrderListActivity.this);
        }

    }
    private void init(){
        firebaseAuth = FirebaseAuth.getInstance();
        userData = new UserPreferences(this).getUserData();
        text_order_page = findViewById(R.id.text_order_page);
        recyclerView_orders = findViewById(R.id.recyclerView_orders);
    }

    private void getOrderData() {
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document("order")
                .collection(firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                   for (DocumentSnapshot snapshot : task.getResult()){
                       OrderModel model = new OrderModel();
                       model.setName(snapshot.getString("name"));
                       model.setFull_address(snapshot.getString("full_address"));
                       model.setPhone(snapshot.getString("phone"));
                       model.setProduct_category(snapshot.getString("product_category"));
                       model.setProduct_description(snapshot.getString("product_description"));
                       model.setProduct_image_url(snapshot.getString("product_image_url"));
                       model.setProduct_max_price(snapshot.getString("product_max_price"));
                       model.setProduct_name(snapshot.getString("product_name"));
                       model.setProduct_price(snapshot.getString("product_price"));
                       model.setProduct_quantity(snapshot.getString("product_quantity"));
                       model.setProduct_sub_title(snapshot.getString("product_sub_title"));
                       model.setTransaction_email(snapshot.getString("transaction_email"));
                       model.setTransaction_id(snapshot.getString("transaction_id"));
                       model.setZipcode(snapshot.getString("zipcode"));
                       model.setUid(snapshot.getString("uid"));
                       model.setCurrent_date(snapshot.getDate("current_date"));
                       list.add(model);
                   }
                   adapter.notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    Log.d(Constants.TAG_ADD_PRODUCT, "getOrderData: "+e.getMessage());
                    Constants.setMessage(e.getMessage(), OrderListActivity.this);
                });

    }

}