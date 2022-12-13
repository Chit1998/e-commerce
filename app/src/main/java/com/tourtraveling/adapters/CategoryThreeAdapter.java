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

public class CategoryThreeAdapter extends RecyclerView.Adapter<CategoryThreeAdapter.CategoryThreeVH> {

    private Context context;
    private final List<AddProductModel> list;

    public CategoryThreeAdapter(Context context, List<AddProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryThreeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryThreeVH(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_category_layout, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryThreeVH holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), 4);
    }

    public class CategoryThreeVH extends RecyclerView.ViewHolder {
        ImageView image_product_item;
        TextView text_product_title;
        TextView text_product_subtitle;
        TextView text_product_price, text_product_max_price;
        public CategoryThreeVH(@NonNull View itemView) {
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
        }
    }
}
