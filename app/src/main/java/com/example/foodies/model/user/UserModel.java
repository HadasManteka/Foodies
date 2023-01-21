package com.example.foodies.model.user;

import android.graphics.Bitmap;

import com.example.foodies.model.FirebaseModel;
import com.example.foodies.model.Listener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserModel {
    private static final UserModel _instance = new UserModel();

    private Executor executor = Executors.newSingleThreadExecutor();
//    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
//    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static UserModel instance(){
        return _instance;
    }
    private UserModel(){
    }

//    public void getUser(Recipe re, Listener<Void> listener){
//        firebaseModel.getUser(re,(Void)->{
//            listener.onComplete(null);
//        });
//    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user, listener);
    }

    public void uploadProfileImage(String id, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadProfileImage(id,bitmap,listener);
    }

    public void register(String name, String password, Listener<Void> listener) {
        firebaseModel.fireBaseRegister(name, password, listener);
    }
}
