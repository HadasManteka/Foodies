package com.example.foodies.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public interface GetAllRecipeListener {
        void onComplete(List<Recipe> data);
    }
    public void getAllRecipes(GetAllRecipeListener callback){
        executor.execute(()->{
            List<Recipe> data = localDb.studentDao().getAll();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public interface AddRecipeListener {
        void onComplete();
    }
    public void addRecipe(Recipe recipe, AddRecipeListener listener){
        executor.execute(()->{
            localDb.studentDao().insertAll(recipe);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            mainHandler.post(listener::onComplete);
        });
    }


}
