package com.example.foodies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodies.model.Recipe;
import com.example.foodies.model.request.ApiRecipeModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_hamburger_foreground));
        setSupportActionBar(toolbar);


        displayFragment(homePageFragment);
    }

    private void displayFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        if (inDisplay != null) tran.remove(inDisplay);
        tran.add(R.id.main_frag_container, fragment);
        tran.addToBackStack(String.valueOf(fragment.getId()));
        tran.commit();
        inDisplay = fragment;
    }

    public void getApiRecipes() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(ApiRecipeModel.instance().getJsonObjectRequest(
                new IRecipeApiListener() {
                    @Override
                    public void onSuccess(List<Recipe> response) {
                        homePageFragment.adapter.setData(response);
                    }
                }
        ));
    }

    @Override
    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_page_option:
                displayFragment(homePageFragment);
                break;
            case R.id.user_profile_option:
                displayFragment(userProfileFragment);
                break;
            case R.id.logout_option:
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}