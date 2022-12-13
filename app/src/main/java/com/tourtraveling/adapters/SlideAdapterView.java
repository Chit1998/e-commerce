package com.tourtraveling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourtraveling.R;

import java.util.ArrayList;

public class SlideAdapterView extends RecyclerView.Adapter<SlideAdapterView.SlideVH> {
    private Context ctx;
    private ArrayList<String> list;
    private ViewPager2 viewPager2;

    public SlideAdapterView(Context ctx, ArrayList<String> list, ViewPager2 viewPager2) {
        this.ctx = ctx;
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideAdapterView.SlideVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_slide_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideAdapterView.SlideVH holder, int position) {
        Glide.with(ctx.getApplicationContext())
                .load(list.get(position))
                .centerCrop()
                .into(holder.imgView);
        if (position == list.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public static class SlideVH extends RecyclerView.ViewHolder {
        RoundedImageView imgView;
        public SlideVH(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.roundSlideImages);
        }
    }
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };
}
