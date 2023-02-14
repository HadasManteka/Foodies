package com.example.foodies.model;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.foodies.enums.AuthenticationEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Objects;

public class FirebaseModel{
    FirebaseFirestore db;
    FirebaseStorage storage;
    FirebaseAuth firebaseAuth;

    public FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

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

    public void getRecipeById(String id, Listener<Recipe> callback) {
        db.collection(Recipe.COLLECTION).whereEqualTo("id", id).get()
                .addOnSuccessListener(task -> {
                    Recipe recipe = Recipe.fromJson(Objects.requireNonNull(task.getDocuments().get(0).getData()));
                    Log.d(TAG, "recipe successfully selected");
                    callback.onComplete(recipe);
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

    public void addUser(User user, Listener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJson())
            .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void uploadProfileImage(String id, Bitmap bitmap, Listener<String> listener) {
        String ProfileImagePath = "profile/" + id;
        uploadImage(ProfileImagePath, bitmap, listener);
    }

    public void uploadRecipeImage(String id, Bitmap bitmap, Listener<String> listener) {
        String recipeImagePath = "recipe/" + id;
        uploadImage(recipeImagePath, bitmap, listener);
    }

    public void deleteRecipeImage(String id, Listener<String> listener) {
        String recipeImagePath = "recipe/" + id;
        deleteImage(recipeImagePath, listener);
    }

    private void deleteImage(String id, Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + id + ".jpg");
        imagesRef.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onComplete(null);
            }
        });
    }

    private void uploadImage(String id, Bitmap bitmap, Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + id + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null)).addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                listener.onComplete(uri.toString());
            }
        }));
    }

    public void fireBaseRegister(String email, String password, Listener<Void> listener) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    listener.onComplete(null);
                }
            });
    }

    public void fireBaseLogin(String email, String password, Listener<AuthenticationEnum> listener) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    listener.onComplete(AuthenticationEnum.AUTHORIZED);
                }
                else {
                   listener.onComplete(AuthenticationEnum.UNAUTHORIZED);
                }
            });
    }

    public void getUser(String email, Listener<User> listener) {
        db.collection(User.COLLECTION).whereEqualTo("email", email).get()
            .addOnSuccessListener(task -> {
                User user = User.fromJson(Objects.requireNonNull(task.getDocuments().get(0).getData()));
                user.id = task.getDocuments().get(0).getId();
                Log.d(TAG, "user successfully selected");
                listener.onComplete(user);
            });
    }

    public void doesEmailExists(String email, Listener<Boolean> listener) {
        db.collection(User.COLLECTION).whereEqualTo("email", email).get()
                .addOnSuccessListener(task -> {
                    boolean isExists = task.getDocuments().size() > 0;
                    Log.d(TAG, "email " + email + isExists);
                    listener.onComplete(isExists);
                });
    }
}
