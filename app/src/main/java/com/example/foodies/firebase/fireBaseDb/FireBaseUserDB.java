package com.example.foodies.firebase.fireBaseDb;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.foodies.model.Listener;
import com.example.foodies.model.user.User;

import java.util.Objects;

public class FireBaseUserDB extends FirebaseDB{

    public void addUser(User user, Listener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJson())
            .addOnCompleteListener(task -> listener.onComplete(null));
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
