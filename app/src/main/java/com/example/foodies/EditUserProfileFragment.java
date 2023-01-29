package com.example.foodies;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.navigation.Navigation;

import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;
import com.example.foodies.model.user.UserModel;
import com.example.foodies.util.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditUserProfileFragment extends BaseUserProfileFragment {
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (!validateLinkForm()) {
            return;
        }

        ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));

        user = getUser();

        baseBinding.userImageInput.setDrawingCacheEnabled(true);
        baseBinding.userImageInput.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) baseBinding.userImageInput.getDrawable()).getBitmap();
        UserModel.instance().uploadProfileImage(user.getId(), bitmap, url -> {

            if (url != null) {
                user.setImgUrl(url);
            }

            UserModel.instance().updateUser(user, (unused) -> {
                alertDialog.setTitle("User '" + user.getNickName() + "'")
                        .setMessage("Updated successfully.").show();
                ProgressDialog.hideProgressDialog();
                backToUserProfile();
            });
        });
    }

    private boolean validateLinkForm() {
        if (TextUtils.isEmpty(baseBinding.userNameInput.getText().toString())) {
            baseBinding.userNameInput.setError("Required.");
            return false;
        } else if (Objects.equals(currentUser.getImgUrl(), "")) {
            alertDialog.setTitle("Hi chef,")
                    .setMessage("Must select an image!").show();
            return false;
        } else {
            return true;
        }
    }

    private User getUser() {
        String nickname = baseBinding.userNameInput.getText().toString();
        String imgUrl = baseBinding.userImageInput.getDrawable().toString();

        User user = new User();
        user.setNickName(nickname);
        user.setImgUrl(imgUrl);
        return user;
    }

    private void backToUserProfile() {
        Navigation.findNavController(baseBinding.getRoot()).popBackStack();
    }
}