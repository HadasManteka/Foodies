package com.example.foodies.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String recipeImg ="";

    public Recipe(){
    }
    public Recipe( String id,String name, String avatarUrl) {
        this.name = name;
        this.id = id;
        this.recipeImg = avatarUrl;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipeImg(String avatarUrl) {
        this.recipeImg = avatarUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRecipeImg() {
        return recipeImg;
    }
}
