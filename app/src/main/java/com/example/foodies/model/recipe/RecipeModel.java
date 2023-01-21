package com.example.foodies.model.recipe;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodies.model.FirebaseModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecipeModel {
    private static final RecipeModel _instance = new RecipeModel();

    private Executor executor = Executors.newSingleThreadExecutor();
//    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static RecipeModel instance(){
        return _instance;
    }
    private RecipeModel(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }


//    public enum LoadingState{
//        LOADING,
//        NOT_LOADING
//    }
//    final public MutableLiveData<LoadingState> EventStudentsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    private LiveData<List<Recipe>> recipeList;
    public LiveData<List<Recipe>> getAllRecipes() {
        if(recipeList == null){
            recipeList = localDb.recipeDao().getAll();
            refreshAllRecipes();
        }
        return recipeList;
    }

    public void refreshAllRecipes(){
//        EventStudentsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Recipe.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllRecipesSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Recipe st:list){
                    // insert new records into ROOM
                    localDb.recipeDao().insertAll(st);
                    if (time < st.getLastUpdated()){
                        time = st.getLastUpdated();
                    }
                }
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                // update local last update
                Recipe.setLocalLastUpdate(time);
//                EventStudentsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
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

    public void uploadImage(String name, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }
}
