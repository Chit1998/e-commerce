package com.tourtraveling.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.activities.LoginScreensActivity;
import com.tourtraveling.activities.ProductViewActivity;
import com.tourtraveling.adapters.CategoryOneAdapter;
import com.tourtraveling.adapters.CategoryThreeAdapter;
import com.tourtraveling.adapters.CategoryTwoAdapter;
import com.tourtraveling.adapters.SlideAdapterView;
import com.tourtraveling.interfaces.ProductListener;
import com.tourtraveling.models.AddProductModel;
import com.tourtraveling.preferences.UserPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements ProductListener {

    public ViewPager2 slideViewPager2;
    private Handler slideHandler = new Handler();
    FirebaseAuth auth;
    private TextView text_category_item_one,text_category_item_view_one, text_category_item_two, text_category_item_view_two,
            text_category_item_three, text_category_item_view_three, text_category_item_four, text_category_item_view_four,
            text_category_item_five,text_category_item_view_five;

    private RecyclerView recycler_view_category_one, recycler_view_category_two, recycler_view_category_three,
            recycler_view_category_four, recycler_view_category_five;

    List<AddProductModel> listOne;
    List<AddProductModel> listTwo;
    List<AddProductModel> listThree;
    private CategoryOneAdapter oneAdapter;
    private CategoryTwoAdapter twoAdapter;
    private CategoryThreeAdapter threeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        init(v);
        slider();


        listOne = new ArrayList<>();
        listTwo = new ArrayList<>();
        listThree = new ArrayList<>();
        oneAdapter = new CategoryOneAdapter(requireContext(), listOne, this);
        recycler_view_category_one.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
        recycler_view_category_one.setAdapter(oneAdapter);

        twoAdapter = new CategoryTwoAdapter(requireContext(), listTwo);
        recycler_view_category_two.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
        recycler_view_category_two.setAdapter(twoAdapter);

        threeAdapter = new CategoryThreeAdapter(requireContext(), listThree);
        recycler_view_category_three.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
        recycler_view_category_three.setAdapter(threeAdapter);

        v.findViewById(R.id.image_logout)
                .setOnClickListener(v1 -> {
                    auth.signOut();
                    new UserPreferences(requireContext())
                            .dataClear();
                    startActivity(new Intent(requireContext(), LoginScreensActivity.class));
                    requireActivity().finish();
                });



        getOneCategory();
        getTwoCategory();
        getThreeCategory();
        return v;
    }

    private void getOneCategory() {
        FirebaseFirestore
                .getInstance()
                .collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot snapshot: task.getResult()){
                            if (Objects.equals(snapshot.getString("product_category"), "Item 1")){
                                text_category_item_one.setText(snapshot.getString("product_category"));
                                text_category_item_view_one.setVisibility(View.VISIBLE);
                                text_category_item_one.setVisibility(View.VISIBLE);

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
                                listOne.add(model);
                            }
                        }
                        recycler_view_category_one.setVisibility(View.VISIBLE);
                        oneAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getTwoCategory() {
        FirebaseFirestore
                .getInstance()
                .collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot snapshot: task.getResult()){
                            if (Objects.equals(snapshot.getString("product_category"), "Item 2")){
                                text_category_item_two.setText(snapshot.getString("product_category"));
                                text_category_item_view_two.setVisibility(View.VISIBLE);
                                text_category_item_two.setVisibility(View.VISIBLE);

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
                                listTwo.add(model);
                            }
                        }
                        recycler_view_category_two.setVisibility(View.VISIBLE);
                        twoAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getThreeCategory() {
        FirebaseFirestore
                .getInstance()
                .collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot snapshot: task.getResult()){
                            if (Objects.equals(snapshot.getString("product_category"), "Item 3")){
                                text_category_item_three.setText(snapshot.getString("product_category"));
                                text_category_item_view_three.setVisibility(View.VISIBLE);
                                text_category_item_three.setVisibility(View.VISIBLE);

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
                                listThree.add(model);
                            }
                        }
                        recycler_view_category_three.setVisibility(View.VISIBLE);
                        threeAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void init(View v) {
        auth = FirebaseAuth.getInstance();
        slideViewPager2 = v.findViewById(R.id.slideViewPager2);
        text_category_item_one = v.findViewById(R.id.text_category_item_one);
        text_category_item_view_one = v.findViewById(R.id.text_category_item_view_one);
        text_category_item_two = v.findViewById(R.id.text_category_item_two);
        text_category_item_view_two = v.findViewById(R.id.text_category_item_view_two);
        text_category_item_three = v.findViewById(R.id.text_category_item_three);
        text_category_item_view_three = v.findViewById(R.id.text_category_item_view_three);
        text_category_item_four = v.findViewById(R.id.text_category_item_four);
        text_category_item_view_four = v.findViewById(R.id.text_category_item_view_four);
        text_category_item_five = v.findViewById(R.id.text_category_item_five);
        text_category_item_view_five = v.findViewById(R.id.text_category_item_view_five);
        recycler_view_category_one = v.findViewById(R.id.recycler_view_category_one);
        recycler_view_category_two = v.findViewById(R.id.recycler_view_category_two);
        recycler_view_category_three = v.findViewById(R.id.recycler_view_category_three);
        recycler_view_category_four = v.findViewById(R.id.recycler_view_category_four);
        recycler_view_category_five = v.findViewById(R.id.recycler_view_category_five);
    }

    private void slider() {
        ArrayList<String> list = new ArrayList<>();
        FirebaseFirestore
                .getInstance()
                        .collection("products")
                                .get()
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()){
                                                list.clear();
                                                for (DocumentSnapshot snapshot : task.getResult()){
                                                    if (Objects.equals(snapshot.getBoolean("banner_image"), true)){
                                                        String url = snapshot.getString("product_image_url");
                                                        list.add(url);

                                                        slideViewPager2.setAdapter( new SlideAdapterView(getContext(),list,slideViewPager2));
                                                        slideViewPager2.setClipToPadding(false);
                                                        slideViewPager2.setClipChildren(false);
                                                        slideViewPager2.setOffscreenPageLimit(2);
                                                        slideViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                                                        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                                                        compositePageTransformer.addTransformer(new MarginPageTransformer(7));
                                                        compositePageTransformer.addTransformer((page, position) -> {
                                                            float r = 1 - Math.abs(position);
                                                            page.setScaleY(0.99f + r*0.01f);
                                                        });
                                                        slideViewPager2.setPageTransformer(compositePageTransformer);
                                                        slideViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                            @Override
                                                            public void onPageSelected(int position) {
                                                                super.onPageSelected(position);
                                                                slideHandler.removeCallbacks(slideRunnable);
                                                                slideHandler.postDelayed(slideRunnable,2000);
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });

    }

    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            slideViewPager2.setCurrentItem(slideViewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 2000);
    }

    @Override
    public void onClickListener(AddProductModel model) {
        startActivity(new Intent(
                requireContext(), ProductViewActivity.class
        )
                .putExtra("product", model));
    }

    @Override
    public void onLongClickListener(AddProductModel model) {

    }
}