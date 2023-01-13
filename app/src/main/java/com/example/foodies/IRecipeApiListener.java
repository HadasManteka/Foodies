package com.example.foodies;

import com.example.foodies.model.Recipe;

import java.util.List;

public interface IRecipeApiListener {
    void onSuccess(List<Recipe> response);
}
