package com.example.foodies.model.user;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    public String password;
    public String imgUrl;

    @Ignore
    public User(){
        this.id = UUID.randomUUID().toString();
    }

    public User(String nickName, String email, String password, String imgUrl) {
        this.id = UUID.randomUUID().toString();
        this.nickName = nickName;
        this.email = email;
        this.password = password;
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
        String password = (String) json.get(PASSWORD);
        return new User(nickName, name,password,img);
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NAME, getNickName());
        json.put(EMAIL, getEmail());
        json.put(PASSWORD, getPassword());
        json.put(IMG, getImgUrl());
        return json;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
