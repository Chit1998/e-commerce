package com.tourtraveling.models;

import java.io.Serializable;
import java.util.Date;

public class AddProductModel implements Serializable {
    private String product_name;
    private String product_sub_title;
    private String product_quantity;
    private String product_max_price;
    private String product_price;
    private String product_description;
    private String product_image_url;
    private String product_category;
    private boolean banner_image;
    private String uid;
    private Date current_date;

    public AddProductModel(String product_name, String product_sub_title, String product_quantity,
                           String product_max_price, String product_price, String product_description,
                           String product_image_url, String product_category, boolean banner_image, Date current_date) {
        this.product_name = product_name;
        this.product_sub_title = product_sub_title;
        this.product_quantity = product_quantity;
        this.product_max_price = product_max_price;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_image_url = product_image_url;
        this.product_category = product_category;
        this.banner_image = banner_image;
        this.current_date = current_date;
    }

    public AddProductModel(String product_name, String product_sub_title, String product_quantity,
                           String product_max_price, String product_price, String product_description,
                           String product_image_url, String product_category, boolean banner_image,
                           String uid, Date current_date) {
        this.product_name = product_name;
        this.product_sub_title = product_sub_title;
        this.product_quantity = product_quantity;
        this.product_max_price = product_max_price;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_image_url = product_image_url;
        this.product_category = product_category;
        this.banner_image = banner_image;
        this.uid = uid;
        this.current_date = current_date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_sub_title() {
        return product_sub_title;
    }

    public void setProduct_sub_title(String product_sub_title) {
        this.product_sub_title = product_sub_title;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_max_price() {
        return product_max_price;
    }

    public void setProduct_max_price(String product_max_price) {
        this.product_max_price = product_max_price;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public boolean isBanner_image() {
        return banner_image;
    }

    public void setBanner_image(boolean banner_image) {
        this.banner_image = banner_image;
    }
}
