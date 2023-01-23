package com.example.foodies.model.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.util.Constants;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiRecipeModel {

    private final List<Recipe> recipesFromApi = new ArrayList<>();
    private static final ApiRecipeModel _instance = new ApiRecipeModel();

    public static ApiRecipeModel instance(){
        return _instance;
    }
    private ApiRecipeModel(){
    }

    public List<Recipe> getRecipesFromApi() {
        return recipesFromApi;
    }

    public JsonObjectRequest getJsonObjectRequest() {
        return new JsonObjectRequest (
            Request.Method.GET,
            Constants.RECIPE_API,
            null,
            response -> {
                Log.i("the res is:", String.valueOf(response));
                try {
                    RecipeResponse[] recipes = new Gson().fromJson(String.valueOf(response.get("results")), RecipeResponse[].class);

                    Arrays.stream(recipes).forEach(recipe -> {
                        String ingredients = createIngredientsList(recipe);
                        String category = (recipe.getDishTypes().size() != 0) ? recipe.getDishTypes().get(0) : null;
                        category = RecipeCategoryEnum.getCategoryByText(category).getCategory();
                        String time = RecipeMadeTimeEnum.getTextByTime( recipe.getPreparationMinutes());
                        Recipe re = new Recipe(recipe.getTitle(), category, time,
                                ingredients, recipe.getSummary(), recipe.getImage());
                        recipesFromApi.add(re);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("the recipes is:", recipesFromApi.toString());
            },
                error -> Log.i("the res is error:", error.toString())
        );
    }

    private String createIngredientsList(RecipeResponse recipe) {
        StringBuilder ingredientsStr = new StringBuilder();

        recipe.getAnalyzedInstructions().forEach(inst ->
                inst.getSteps().forEach(step ->
                        step.getIngredients().forEach(ingredient ->
                                ingredientsStr.append(ingredient.getName()).append(", "))));
        return ingredientsStr.toString();
    }
}
