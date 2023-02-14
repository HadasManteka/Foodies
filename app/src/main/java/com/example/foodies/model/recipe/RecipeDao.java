package com.example.foodies.model.recipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("select * from Recipe")
    LiveData<List<Recipe>> getAll();

    @Query("select * from Recipe where id = :recipeId")
    Recipe getRecipeById(String recipeId);

    @Query("DELETE FROM Recipe WHERE id = :recipeId")
    void deleteRecipeById(String recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... recipes);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}

