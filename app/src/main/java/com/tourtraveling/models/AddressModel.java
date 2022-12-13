package com.tourtraveling.models;

import java.io.Serializable;
import java.util.Date;

public class AddressModel implements Serializable {

    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String phone;
    private String full_name;
    private boolean stateSelected;
    private Date current_date;

    public AddressModel(String address, String city, String state, String zipcode, String country,
                        String phone, String full_name, boolean stateSelected, Date current_date) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.phone = phone;
        this.full_name = full_name;
        this.stateSelected = stateSelected;
        this.current_date = current_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isStateSelected() {
        return stateSelected;
    }

    public void setStateSelected(boolean stateSelected) {
        this.stateSelected = stateSelected;
    }

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }
}
