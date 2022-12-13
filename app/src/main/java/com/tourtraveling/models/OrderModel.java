package com.tourtraveling.models;

import java.io.Serializable;
import java.util.Date;

public class OrderModel implements Serializable {
    private String product_name;
    private String product_sub_title;
    private String product_quantity;
    private String product_max_price;
    private String product_price;
    private String product_description;
    private String product_image_url;
    private String product_category;
    private String uid;
    private Date current_date;

    private String name;
    private String phone;
    private String full_address;
    private String zipcode;
    private String transaction_id;
    private String transaction_email;


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

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_email() {
        return transaction_email;
    }

    public void setTransaction_email(String transaction_email) {
        this.transaction_email = transaction_email;
    }
}
