package com.example.foodies.model.user;

import android.graphics.Bitmap;

import java.util.List;

public class UserModel {
    private static final UserModel _instance = new UserModel();
    private final FirebaseModel firebaseModel = new FirebaseModel();

    public static UserModel instance(){
        return _instance;
    }
    private UserModel(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public void getAllUsers(Listener<List<User>> callback) {
        firebaseModel.getAllUsers(callback);
    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user, listener);
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }
}
