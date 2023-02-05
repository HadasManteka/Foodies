package com.example.foodies.model.recipe;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.foodies.MainActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Recipe implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public String category;
    public String time;
    public String ingredients;
    public String description;
    public String imgUrl;
    public Long lastUpdated;
    public String creatorName;
    public String userId;

    @Ignore
    public Recipe(){
        this.id = UUID.randomUUID().toString();
    }

    public Recipe(String title, String category, String time, String ingredients,
                  String description, String imgUrl, String userId) {
        this.title = title;
        this.category = category;
        this.time = time;
        this.ingredients = ingredients;
        this.description = description;
        this.imgUrl = imgUrl;
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
    }

    @Ignore
    public Recipe(String id, String title, String category, String time, String ingredients,
                  String description, String imgUrl, String userId) {
        this.id = id;
        this.title = title;
        this. category = category;
        this.time = time;
        this.ingredients = ingredients;
        this.description = description;
        this.imgUrl = imgUrl;
        this.userId = userId;
    }

    public static final String ID = "id";
    static final String TITLE = "title";
    static final String CATEGORY = "category";
    static final String TIME = "time";
    static final String IMG = "img";
    static final String INGREDIENTS = "ingredients";
    static final String DESCRIPTION = "description";
    public static final String COLLECTION = "recipes";
    public static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "students_local_last_update";
    static final String USER = "user";

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MainActivity.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MainActivity.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public static Recipe fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String title = (String) json.get(TITLE);
        String category = (String)json.get(CATEGORY);
        String madeTime = (String)json.get(TIME);
        String img = (String)json.get(IMG);
        String ingredients = (String) json.get(INGREDIENTS);
        String description = (String) json.get(DESCRIPTION);
        String userId = (String) json.get(USER);
        Recipe re = new Recipe(id, title, category, madeTime, ingredients, description, img, userId);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            re.setLastUpdated(time.getSeconds());
        }catch(Exception e){
        }
        return re;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(TITLE, getTitle());
        json.put(CATEGORY, getCategory());
        json.put(TIME, getTime());
        json.put(IMG, getImgUrl());
        json.put(INGREDIENTS, getIngredients());
        json.put(DESCRIPTION, getDescription());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(USER, getUserID());
        return json;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userId) {
        this.userId = userId;
    }
}
