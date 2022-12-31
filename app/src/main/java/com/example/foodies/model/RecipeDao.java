package com.example.foodies.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("select * from Recipe")
    List<Recipe> getAll();

    @Query("select * from Recipe where id = :recipeId")
    Recipe getStudentById(String recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... students);

    @Delete
    void delete(String student);
}

