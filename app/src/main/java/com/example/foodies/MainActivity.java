package com.example.foodies;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.foodies.model.user.User;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_hamburger_foreground));
        setSupportActionBar(toolbar);
    }

    @Override
    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu) {
        if(User.getUser() != null) {
            MenuInflater inflater = getMenuInflater();
            if (menu instanceof MenuBuilder) {
                MenuBuilder m = (MenuBuilder) menu;
                    m.setOptionalIconsVisible(true);
                }

            inflater.inflate(R.menu.menu, menu);
        }

        View view = findViewById(R.id.nav_host_fragment);
        NavController navController = Navigation.findNavController(view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(view1 -> navController.popBackStack());
        return true;
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.invalidateOptionsMenu();
        View view = findViewById(R.id.nav_host_fragment);
        NavController navController = Navigation.findNavController(view);
        int currId = (Objects.nonNull(navController.getCurrentDestination())) ? navController.getCurrentDestination().getId() : 0;

        switch (item.getItemId()) {
            case R.id.home_page_option:
                if (currId != R.id.homePageFragment) {
                    navController.navigate(R.id.homePageFragment);
                }
                break;
            case R.id.user_profile_option:
                if (currId != R.id.userProfileFragment) {
                    navController.navigate(R.id.userProfileFragment);
                }
                break;
            case R.id.logout_option:
                User.logout();
                navController.navigate(R.id.loginFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, whichButton) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create().show();
    }
}