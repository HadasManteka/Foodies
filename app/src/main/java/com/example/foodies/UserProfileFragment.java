package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserProfileFragment extends BaseUserProfileFragment {

    public UserProfileFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserViewField() {
        if(Objects.nonNull(currentUser)) {
            baseBinding.userNameView.setText(currentUser.nickName);

            if (!Objects.equals(currentUser.getImgUrl(), "")) {
                Picasso.get().load(currentUser.getImgUrl()).placeholder(R.drawable.camera_img).into(baseBinding.userImageInput);
            } else {
                baseBinding.userImageInput.setImageResource(R.drawable.camera_img);
            }
        }

        setEditMode(false);

        baseBinding.newRecipeButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(UserProfileFragment.this).navigate(
                    UserProfileFragmentDirections.actionUserProfileFragmentToAddRecipeFragment());
        });
    }

    @Override
    public void onClickAction() {
        setEditMode(true);
    }
}