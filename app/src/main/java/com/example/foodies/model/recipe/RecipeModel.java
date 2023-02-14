package com.example.foodies.model.recipe;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodies.model.FirebaseModel;
import com.example.foodies.model.Listener;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecipeModel {
    private static final RecipeModel _instance = new RecipeModel();

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static RecipeModel instance(){
        return _instance;
    }
    private RecipeModel(){
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventRecipesListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);


    private LiveData<List<Recipe>> recipeList;
    public LiveData<List<Recipe>> getAllRecipes() {
        if(recipeList == null){
            recipeList = localDb.recipeDao().getAll();
            refreshAllRecipes();
        }
        return recipeList;
    }

    public void refreshAllRecipes(){
        EventRecipesListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = Recipe.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllRecipesSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Recipe recipe:list){
                    // insert new records into ROOM
                    localDb.recipeDao().insertAll(recipe);
                    if (time < recipe.getLastUpdated()){
                        time = recipe.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Recipe.setLocalLastUpdate(time);
                EventRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void getRecipeById(String id, Listener<Recipe> callback) {
        firebaseModel.getRecipeById(id, callback);
    }

    public void addRecipe(Recipe re, Listener<Void> listener){
        firebaseModel.addRecipe(re,(Void)->{
            refreshAllRecipes();
            listener.onComplete(null);
        });
    }

    public void updateRecipe(Recipe re, Listener<Void> listener){
        addRecipe(re, listener);
    }

    public void deleteRecipe(Recipe re, Listener<Void> listener){
        firebaseModel.deleteRecipe(re, listener);
    }

    public void uploadImage(String id, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadRecipeImage(id,bitmap,listener);
    }

    public void deleteRecipeImage(String id, Listener<String> listener) {
        firebaseModel.deleteRecipeImage(id,listener);
    }
}
