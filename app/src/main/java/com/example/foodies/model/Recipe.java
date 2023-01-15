package com.example.foodies.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Blob;

@Entity
public class Recipe implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer id;
    public String title;
    public String category;
    public String time;
    public String ingredients;
    public String description;
    @Ignore
    public String imgUrl;
    public byte[] recipeImgBytes;

    @Ignore
    public Recipe(){
    }
    @Ignore
    public Recipe(String title, String category, String time, String ingredients,
                  String description, String imgUrl) {
        this.title = title;
        this. category = category;
        this.time = time;
        this.ingredients = ingredients;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public Recipe(String title, String category, String time, String ingredients,
                  String description, byte[] recipeImgBytes) {
        this.title = title;
        this. category = category;
        this.time = time;
        this.ingredients = ingredients;
        this.description = description;
        this.recipeImgBytes = recipeImgBytes;
    }

    @NonNull
    public Integer getId() {
        return id;
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

    public byte[] getRecipeImgBytes() {
        return recipeImgBytes;
    }

    public void setRecipeImgBytes(byte[] recipeImgUrl) {
        this.recipeImgBytes = recipeImgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
