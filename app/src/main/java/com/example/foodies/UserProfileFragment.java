package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

    private User user;

    public UserProfileFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }
    }

    @Override
    public void setUserViewField() {
        if(Objects.nonNull(user)) {
            baseBinding.userNameView.setText(user.nickName);

            if (!Objects.equals(user.getImgUrl(), "")) {
                Picasso.get().load(user.getImgUrl()).placeholder(R.drawable.camera_img).into(baseBinding.userImageInput);
            } else {
                baseBinding.userImageInput.setImageResource(R.drawable.camera_img);
            }
        }

        setEditMode(false);
    }

    @Override
    public void onClickAction() {
        setEditMode(true);
    }
}