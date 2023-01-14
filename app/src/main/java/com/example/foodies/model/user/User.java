package com.example.foodies.model.user;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public String id="";
    public String name;
    public String password;
    public String imgUrl;

    @Ignore
    public User(){
    }

    public User(String name, String password, String imgUrl) {
        this.name = name;
        this.password = password;
        this.imgUrl = imgUrl;
    }

    static final String NAME = "name";
    static final String PASSWORD = "password";
    static final String IMG = "img";
    static final String COLLECTION = "users";

    public static User fromJson(Map<String,Object> json){
        String name = (String)json.get(NAME);
        String img = (String)json.get(IMG);
        String password = (String) json.get(PASSWORD);
        return new User(name,password,img);
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NAME, getName());
        json.put(PASSWORD, getPassword());
        json.put(IMG, getImgUrl());
        return json;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
