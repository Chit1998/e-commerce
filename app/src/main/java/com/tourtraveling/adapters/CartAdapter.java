package com.tourtraveling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourtraveling.R;
import com.tourtraveling.models.AddProductModel;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    Context context;
    List<AddProductModel> list;

    public CartAdapter(Context context, List<AddProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartAdapter.CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_oder_list_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartVH holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartVH extends RecyclerView.ViewHolder {
        ImageView image_admin_order_list;
        TextView text_order_list_view,text_content_view;
        public CartVH(@NonNull View itemView) {
            super(itemView);

            image_admin_order_list = itemView.findViewById(R.id.image_admin_order_list);
            text_order_list_view = itemView.findViewById(R.id.text_order_list_view);
            text_content_view = itemView.findViewById(R.id.text_content_view);
        }

        void setData(AddProductModel model){
            Glide.with(context)
                    .load(model.getProduct_image_url())
                    .into(image_admin_order_list);
            text_order_list_view.setText(model.getProduct_name());
            text_content_view.setText(model.getProduct_sub_title());
        }
    }
}
