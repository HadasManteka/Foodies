package com.example.foodies.model.user;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.foodies.MyApplication;
import com.google.gson.Gson;

import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    @PrimaryKey
    @NonNull
    public String id="";
    public String nickName;
    public String email;
    public String imgUrl;

    @Ignore
    public User(){
        this.id = UUID.randomUUID().toString();
    }

    public User(String nickName, String email, String imgUrl) {
        this.id = UUID.randomUUID().toString();
        this.nickName = nickName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public User(String id, String nickName, String email, String imgUrl) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    static final String NAME = "nickname";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";
    static final String IMG = "img";
    public static final String COLLECTION = "users";

    public static User fromJson(Map<String,Object> json){
        String nickName = (String)json.get(NAME);
        String name = (String)json.get(EMAIL);
        String img = (String)json.get(IMG);
        return new User(nickName, name,img);
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NAME, getNickName());
        json.put(EMAIL, getEmail());
        json.put(IMG, getImgUrl());
        return json;
    }

    public static User getUser() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("user", "");
        return gson.fromJson(json, User.class);
    }

    public static void setUser(User user) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString("user", gson.toJson(user));
        editor.commit();
    }

    public static void logout() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("user");
        editor.commit();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void SetNickName(String nickName) {
        this.nickName = nickName;
    }

}
