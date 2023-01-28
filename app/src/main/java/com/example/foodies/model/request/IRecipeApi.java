package com.example.foodies.model.request;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRecipeApi {
    @GET("/recipes/complexSearch?addRecipeInformation=true&number=3&apiKey=9f6e3c6b334048ae99e030047c371c28")
    Call<RecipesApiResponse> getRecipes();
}
