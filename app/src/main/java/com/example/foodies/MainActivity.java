package com.example.foodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodies.model.request.ApiRecipeModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApiRecipes();
    }

    public void getApiRecipes() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(ApiRecipeModel.instance().getJsonObjectRequest());
    }
}