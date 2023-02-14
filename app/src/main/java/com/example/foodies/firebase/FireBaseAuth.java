package com.example.foodies.firebase;

import com.example.foodies.enums.AuthenticationEnum;
import com.example.foodies.model.Listener;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseAuth {

    FirebaseAuth firebaseAuth;

    public FireBaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void fireBaseRegister(String email, String password, Listener<Void> listener) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    listener.onComplete(null);
                }
            });
    }

    public void fireBaseLogin(String email, String password, Listener<AuthenticationEnum> listener) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    listener.onComplete(AuthenticationEnum.AUTHORIZED);
                }
                else {
                    listener.onComplete(AuthenticationEnum.UNAUTHORIZED);
                }
            });
    }
}
