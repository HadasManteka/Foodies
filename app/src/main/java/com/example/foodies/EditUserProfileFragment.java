package com.example.foodies;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.model.user.User;
import com.example.foodies.util.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EditUserProfileFragment extends BaseUserProfileFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            currentUser = (User) bundle.getSerializable("user");
//        }
    }

    @Override
    public void setUserViewField() {
        deleteButVisibility(true);
        if (Objects.nonNull(currentUser)) {
            baseBinding.userNameInput.setText(currentUser.nickName);

            if (!Objects.equals(currentUser.getImgUrl(), "")) {
                Picasso.get().load(currentUser.getImgUrl()).placeholder(R.drawable.camera_img).into(baseBinding.userImageInput);
            } else {
                baseBinding.userImageInput.setImageResource(R.drawable.camera_img);
            }
        }

        setEditMode(true);

        baseBinding.saveUserButton.setOnClickListener(view -> {
            updateUser();
        });

        baseBinding.cancelButton.setOnClickListener(view -> {
            Navigation.findNavController(baseBinding.getRoot()).popBackStack();
        });
    }

    private void updateUser() {
//TODO
    }
}