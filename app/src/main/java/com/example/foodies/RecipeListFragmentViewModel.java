package com.example.foodies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.model.request.RecipeApiModel;

import java.util.List;
import java.util.Objects;

public class RecipeListFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> data = RecipeModel.instance().getAllRecipes();
    private MutableLiveData<List<Recipe>> apiRecipes = RecipeApiModel.instance().getApiRecipes();

    public MutableLiveData<List<Recipe>> getApiRecipes(){
        return apiRecipes;
    }

    public LiveData<List<Recipe>> getData(){
        return data;
    }
}
