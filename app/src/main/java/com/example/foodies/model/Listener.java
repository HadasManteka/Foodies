package com.example.foodies.model;

public interface Listener<T>{
    void onComplete(T data);
}