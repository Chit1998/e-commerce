package com.tourtraveling.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String name;
    private String email;
    private String userType;
    private String uid;

    public UserModel(String name, String email, String userType, String uid) {
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
