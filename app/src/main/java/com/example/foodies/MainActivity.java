package com.example.foodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodies.model.request.ApiRecipeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    UserProfileFragment userProfileFragment;
    HomePageFragment homePageFragment;
    Fragment inDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApiRecipes();

        userProfileFragment = new UserProfileFragment();
        homePageFragment = new HomePageFragment();

        Button user_btn = findViewById(R.id.user_btn);
        Button home_btn = findViewById(R.id.home_btn);

        user_btn.setOnClickListener((view) -> {
            displayFragment(userProfileFragment);
        });

        home_btn.setOnClickListener((view) -> {
            displayFragment(homePageFragment);
        });

        displayFragment(homePageFragment);
    }

    private void displayFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.main_frag_container, fragment);
        if(inDisplay != null) tran.remove(inDisplay);
        tran.commit();
        inDisplay = fragment;
    }

    public void getApiRecipes() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(ApiRecipeModel.instance().getJsonObjectRequest());
    }
}