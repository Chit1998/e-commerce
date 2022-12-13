package com.tourtraveling.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourtraveling.R;
import com.tourtraveling.activities.CustomerMainActivity;
import com.tourtraveling.activities.CustomerOrderProductDetailsActivity;
import com.tourtraveling.activities.admin.AdminOrderProductDetailsActivity;
import com.tourtraveling.models.OrderModel;
import com.tourtraveling.preferences.UserPreferences;
import com.tourtraveling.utils.Constants;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {
    private Context context;
    private List<OrderModel> list;

    public OrderAdapter(Context context, List<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_oder_list_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderVH holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderVH extends RecyclerView.ViewHolder {
        ImageView image_admin_order_list;
        TextView text_order_list_view,text_content_view;
        public OrderVH(@NonNull View itemView) {
            super(itemView);
            image_admin_order_list = itemView.findViewById(R.id.image_admin_order_list);
            text_order_list_view = itemView.findViewById(R.id.text_order_list_view);
            text_content_view = itemView.findViewById(R.id.text_content_view);
        }

        void setData(OrderModel model){
            Glide.with(context)
                    .load(model.getProduct_image_url())
                    .centerCrop()
                    .into(image_admin_order_list);
            text_order_list_view.setText(model.getProduct_name());
            text_content_view.setText(model.getProduct_sub_title());

            itemView.setOnClickListener(v -> {
                if (new UserPreferences(context).getUserData().get(Constants.USER_TYPE).equals("customer")){
                    context.startActivity(new Intent(context.getApplicationContext(), CustomerOrderProductDetailsActivity.class)
                            .putExtra("order", model));
                }
                if (new UserPreferences(context).getUserData().get(Constants.USER_TYPE).equals("admin")){
                    context.startActivity(new Intent(context.getApplicationContext(), AdminOrderProductDetailsActivity.class)
                            .putExtra("order", model));
                }
            });
        }
    }
}
