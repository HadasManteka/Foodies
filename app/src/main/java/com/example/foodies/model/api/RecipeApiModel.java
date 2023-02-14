package com.example.foodies.model.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeApiModel {

    private final List<Recipe> recipesFromApi = new ArrayList<>();
    private static final RecipeApiModel _instance = new RecipeApiModel();
    Retrofit retrofit;
    IRecipeApi recipeApi;
    public static RecipeApiModel instance(){
        return _instance;
    }
    private RecipeApiModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RECIPE_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recipeApi = retrofit.create(IRecipeApi.class);
    }

    public MutableLiveData<List<Recipe>> getApiRecipes(){
        MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
        Call<RecipesApiResponse> call = recipeApi.getRecipes();
        call.enqueue(new Callback<RecipesApiResponse>() {
            @Override
            public void onResponse(Call<RecipesApiResponse> call, Response<RecipesApiResponse> response) {
                if (response.isSuccessful()){
                    List<Recipe> recipesFromApi = new ArrayList<>();

                    Arrays.stream(response.body().getResults()).forEach(recipe -> {
                        String ingredients = createIngredientsList(recipe);
                        String category = (recipe.getDishTypes().size() != 0) ? recipe.getDishTypes().get(0) : null;
                        category = RecipeCategoryEnum.getCategoryByText(category).getCategory();
                        String time = RecipeMadeTimeEnum.getTextByTime( recipe.getPreparationMinutes());
                        Recipe re = new Recipe(recipe.getTitle(), category, time,
                                ingredients, recipe.getSummary(), recipe.getImage(), "api");
                        recipesFromApi.add(re);
                    });
                    data.setValue(recipesFromApi);
                }else{
                    Log.d("TAG","----- getRecipesByCount response error");
                }
            }

            @Override
            public void onFailure(Call<RecipesApiResponse> call, Throwable t) {
                Log.d("TAG","----- getRecipesByCount fail");
            }
        });
        return data;
    }

    public List<Recipe> getRecipesFromApi() {
        return recipesFromApi;
    }

    private String createIngredientsList(RecipesApiResponse.RecipeResponse recipe) {
        StringBuilder ingredientsStr = new StringBuilder();

        recipe.getAnalyzedInstructions().forEach(inst ->
                inst.getSteps().forEach(step ->
                        step.getIngredients().forEach(ingredient ->
                                ingredientsStr.append(ingredient.getName()).append(", "))));
        return ingredientsStr.toString();
    }
}
