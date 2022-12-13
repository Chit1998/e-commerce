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

public class CategoryOneAdapter extends RecyclerView.Adapter<CategoryOneAdapter.CategoryOneVH> {

    private Context context;
    private final List<AddProductModel> list;
    private final ProductListener listener;

    public CategoryOneAdapter(Context context, List<AddProductModel> list, ProductListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryOneAdapter.CategoryOneVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryOneVH(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_category_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryOneAdapter.CategoryOneVH holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), 4);
    }

    public class CategoryOneVH extends RecyclerView.ViewHolder {

        ImageView image_product_item;
        TextView text_product_title;
        TextView text_product_subtitle;
        TextView text_product_price, text_product_max_price;

        public CategoryOneVH(@NonNull View itemView) {
            super(itemView);
            image_product_item = itemView.findViewById(R.id.image_product_item);
            text_product_title = itemView.findViewById(R.id.text_product_title);
            text_product_subtitle = itemView.findViewById(R.id.text_product_subtitle);
            text_product_price = itemView.findViewById(R.id.text_product_price_);
            text_product_max_price = itemView.findViewById(R.id.text_product_max_price_);
        }

        void setData(AddProductModel model){
            Glide.with(context)
                    .load(model.getProduct_image_url())
                    .centerCrop()
                    .into(image_product_item);

            text_product_title.setText(model.getProduct_name());
            text_product_subtitle.setText(model.getProduct_sub_title());
            text_product_price.setText("\u20B9 "+model.getProduct_price());
            text_product_max_price.setText("\u20B9 "+model.getProduct_max_price());

            itemView.setOnClickListener(v -> listener.onClickListener(model));
        }
    }
}
