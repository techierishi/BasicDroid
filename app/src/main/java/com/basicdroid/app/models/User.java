package com.basicdroid.app.models;

import java.io.Serializable;


public class User implements Serializable {

// 0 = Female; 1 = Male

    private String user_id;
    private int gender;
    private int height;
    private int weight;
    private int dob;
    private  String user_category;
    private  String username;
    private String user_category_id;

    // {"user_category":"nurse","username":"test","user_category_id":"2","user_id":"3"}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }


    public String getUser_category() {
        return user_category;
    }

    public void setUser_category(String user_category) {
        this.user_category = user_category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_category_id() {
        return user_category_id;
    }

    public void setUser_category_id(String user_category_id) {
        this.user_category_id = user_category_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                ", dob=" + dob +
                '}';
    }
}