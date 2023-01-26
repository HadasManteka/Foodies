package com.example.foodies;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodies.model.FirebaseModel;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.request.ApiRecipeModel;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends AllRecipesFragment {

    private List<Recipe> allData = new ArrayList<>();
    private FirebaseModel firebaseModel = new FirebaseModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getApiRecipes() {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(ApiRecipeModel.instance().getJsonObjectRequest(
                response -> {
                    allData.addAll(response);
                    setData(allData);
                }
        ));
    }

    public void getUsersRecipes() {

    }

    @Override
    public void setPageDataField() {
        getApiRecipes();
        firebaseModel.getAllRecipes((recipesList) -> {
            allData.addAll(recipesList);
            setData(allData);
        });
    }
}