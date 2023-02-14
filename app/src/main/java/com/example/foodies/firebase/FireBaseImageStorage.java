package com.example.foodies.firebase;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.foodies.model.Listener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FireBaseImageStorage {

    FirebaseStorage storage;

    public FireBaseImageStorage(){
        storage = FirebaseStorage.getInstance();
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
}
