package com.example.foodies.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    @PrimaryKey
    @NonNull
    public String id="";
    public String title;
    public String category;
    public String time;
    public String ingredients;
    public String description;
    public String recipeImgUrl;
    @Ignore
    public Recipe(){
    }
    public Recipe(String title, String category, String time,
                  String ingredients, String description, String recipeImgUrl) {
        this.title = title;
        this. category = category;
        this.time = time;
        this.ingredients = ingredients;
        this.description = description;
        this.recipeImgUrl = recipeImgUrl;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
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

    public String getRecipeImgUrl() {
        return recipeImgUrl;
    }

    public void setRecipeImgUrl(String recipeImgUrl) {
        this.recipeImgUrl = recipeImgUrl;
    }
}
