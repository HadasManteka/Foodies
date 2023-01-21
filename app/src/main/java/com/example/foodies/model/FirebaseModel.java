package com.example.foodies.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel{
    FirebaseFirestore db;
    FirebaseStorage storage;

    public FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();

    }

    public void getAllRecipesSince(Long since, RecipeModel.Listener<List<Recipe>> callback){
        db.collection(Recipe.COLLECTION)
                .whereGreaterThanOrEqualTo(Recipe.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Recipe> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Recipe recipe = Recipe.fromJson(json.getData());
                                list.add(recipe);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void addRecipe(Recipe recipe, RecipeModel.Listener<Void> listener) {
        db.collection(Recipe.COLLECTION).document(recipe.getId()).set(recipe.toJson())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    listener.onComplete(null);
                }
        });
    }

    public void uploadImage(String name, Bitmap bitmap, RecipeModel.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });

    }
}
