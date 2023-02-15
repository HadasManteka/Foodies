package com.example.foodies.firebase.fireBaseDb;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.foodies.model.Listener;
import com.example.foodies.model.recipe.Recipe;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FireBaseRecipeDB extends FirebaseDB{

    public void getAllRecipesSince(Long since, Listener<List<Recipe>> callback){
        db.collection(Recipe.COLLECTION)
                .whereGreaterThanOrEqualTo(Recipe.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Recipe> list = new LinkedList<>();
                    if (task.isSuccessful()){
                        QuerySnapshot jsonsList = task.getResult();
                        for (DocumentSnapshot json: jsonsList){
                            Recipe recipe = Recipe.fromJson(json.getData());
                            list.add(recipe);
                        }
                    }
                    callback.onComplete(list);
                 });

    }

    public void getAllDeletedSince(Long since, Listener<List<Recipe>> callback) {
        List<Recipe> list = new LinkedList<>();
        db.collection(Recipe.COLLECTION)
                .whereGreaterThanOrEqualTo(Recipe.LAST_UPDATED, new Timestamp(since,0))
                .addSnapshotListener((snapshots, e) -> {
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case REMOVED:
                                Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                System.out.println(Recipe.fromJson(dc.getDocument().getData()));
                                list.add(Recipe.fromJson(dc.getDocument().getData()));
                                break;
                        }
                    }

                    callback.onComplete(list);
                });
    }

        public void addRecipe(Recipe recipe, Listener<Void> listener) {
        db.collection(Recipe.COLLECTION).document(recipe.getId()).set(recipe.toJson())
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void deleteRecipe(Recipe recipe, Listener<Void> listener) {
        db.collection(Recipe.COLLECTION).document(recipe.getId()).delete()
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void getRecipeById(String id, Listener<Recipe> callback) {
        db.collection(Recipe.COLLECTION).whereEqualTo("id", id).get()
                .addOnSuccessListener(task -> {
                    Recipe recipe = Recipe.fromJson(Objects.requireNonNull(task.getDocuments().get(0).getData()));
                    Log.d(TAG, "recipe successfully selected");
                    callback.onComplete(recipe);
                });
    }
}
