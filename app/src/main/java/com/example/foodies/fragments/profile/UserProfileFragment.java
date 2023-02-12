package com.example.foodies.fragments.profile;

import android.os.Bundle;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodies.R;
import com.example.foodies.model.user.User;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserProfileFragment extends BaseUserProfileFragment {

    public UserProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserViewField() {
        if (Objects.nonNull(currentUser)) {
            baseBinding.userNameView.setText(currentUser.nickName);

            if (!Objects.equals(currentUser.getImgUrl(), "")) {
                Picasso.get().load(currentUser.getImgUrl()).placeholder(R.drawable.camera_img).into(baseBinding.userImageInput);
            } else {
                baseBinding.userImageInput.setImageResource(R.drawable.camera_img);
            }
        }

        setEditMode(false);

        baseBinding.myRecipesButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(UserProfileFragment.this).navigate(
                    UserProfileFragmentDirections.actionUserProfileFragmentToMyRecipesFragment());
        });

        baseBinding.newRecipeButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(UserProfileFragment.this).navigate(
                    UserProfileFragmentDirections.actionUserProfileFragmentToAddRecipeFragment());
        });

        baseBinding.logoutButton.setOnClickListener(view -> {
            User.logout();
            NavHostFragment.findNavController(UserProfileFragment.this).navigate(
                    UserProfileFragmentDirections.actionUserProfileFragmentToLoginFragment());
        });

        baseBinding.buttonEditProfile.setOnClickListener(v -> {
            Navigation.findNavController(this.getView()).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToEditUserProfileFragment());
        });
    }
}