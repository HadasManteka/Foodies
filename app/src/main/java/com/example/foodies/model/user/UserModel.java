package com.example.foodies.model.user;

import android.graphics.Bitmap;

import com.example.foodies.enums.AuthenticationEnum;
import com.example.foodies.firebase.FireBaseAuth;
import com.example.foodies.firebase.FireBaseImageStorage;
import com.example.foodies.firebase.fireBaseDb.FireBaseUserDB;
import com.example.foodies.model.Listener;

public class UserModel {
    private static final UserModel _instance = new UserModel();

    private FireBaseUserDB firebaseUserDb = new FireBaseUserDB();
    private FireBaseImageStorage firebaseStorage = new FireBaseImageStorage();
    private FireBaseAuth fireBaseAuth = new FireBaseAuth();

    public static UserModel instance() {
        return _instance;
    }

    private UserModel() {
    }

    public void getUser(String email, Listener<User> listener) {
        firebaseUserDb.getUser(email, listener);
    }

    public void addUser(User user, Listener<Void> listener) {
        firebaseUserDb.addUser(user, (Void) -> {
            listener.onComplete(null);
        });
    }

    public void updateUser(User user, Listener<Void> listener) {
        addUser(user, listener);
    }

    public void uploadProfileImage(String id, Bitmap bitmap, Listener<String> listener) {
        firebaseStorage.uploadProfileImage(id, bitmap, listener);
    }

    public void register(String name, String password, Listener<Void> listener) {
        fireBaseAuth.fireBaseRegister(name, password, listener);
    }

    public void login(String name, String password, Listener<AuthenticationEnum> listener) {
        fireBaseAuth.fireBaseLogin(name, password, listener);
    }

    public void doesEmailExists(String email, Listener<Boolean> listener) {
        firebaseUserDb.doesEmailExists(email, listener);
    }
}
