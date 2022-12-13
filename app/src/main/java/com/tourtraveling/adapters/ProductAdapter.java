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
import com.tourtraveling.interfaces.ProductListener;
import com.tourtraveling.models.AddProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {

    Context context;
    private final List<AddProductModel> list;
    private final ProductListener listener;

    public ProductAdapter(Context context, List<AddProductModel> list, ProductListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ProductAdapter.ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductVH(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.product_list_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductVH holder, int position) {
        if (list.size() > 0){
            holder.setData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductVH extends RecyclerView.ViewHolder {
        ImageView image_product;
        TextView text_product_title,text_product_subtitle, text_product_price, text_product_max_price;
        public ProductVH(@NonNull View itemView) {
            super(itemView);
            image_product = itemView.findViewById(R.id.image_product);
            text_product_title = itemView.findViewById(R.id.text_product_title);
            text_product_subtitle = itemView.findViewById(R.id.text_product_subtitle);
            text_product_price = itemView.findViewById(R.id.text_product_price_);
            text_product_max_price = itemView.findViewById(R.id.text_product_max_price_);
        }

        void setData(AddProductModel model){
            text_product_title.setText(model.getProduct_name());
            text_product_subtitle.setText(model.getProduct_sub_title());
            text_product_price.setText(model.getProduct_price());
            text_product_max_price.setText(model.getProduct_max_price());
            Glide
                    .with(context)
                    .load(model.getProduct_image_url())
                    .centerCrop()
                    .into(image_product);

            itemView.setOnClickListener(v -> listener.onClickListener(model));

            itemView.setOnLongClickListener(v -> {
                listener.onLongClickListener(model);
                return true;
            });
        }
    }
}
