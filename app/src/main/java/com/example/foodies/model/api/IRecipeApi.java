package com.example.foodies.model.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRecipeApi {
    @GET("/recipes/complexSearch?addRecipeInformation=true&number=50&apiKey=7f6223c950f248008bd0db576dec86ae")
    Call<RecipesApiResponse> getRecipes();
}
