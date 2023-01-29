package com.example.foodies;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.example.foodies.databinding.FragmentBaseUserProfileBinding;
import com.example.foodies.model.user.User;

abstract class BaseUserProfileFragment extends Fragment {

    AlertDialog.Builder alertDialog;
    ActivityResultLauncher<Void> cameraLauncher;
    protected FragmentBaseUserProfileBinding baseBinding;
    boolean isAvatarSelected;
    ActivityResultLauncher<String> galleryLauncher;
    User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());

        currentUser = User.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseBinding = FragmentBaseUserProfileBinding.inflate(inflater, container, false);
        View view = baseBinding.getRoot();

        deleteButVisibility(false);

        baseBinding.cameraButtonUser.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        baseBinding.galleryButtonUser.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });

        setUserViewField();
        return view;
    }

    protected void setEditMode(boolean enabled) {
        baseBinding.buttonEditProfile.setClickable(!enabled);
        baseBinding.buttonEditProfile.setVisibility((enabled) ? View.GONE : View.VISIBLE);
        baseBinding.userNameView.setVisibility((enabled) ? View.GONE : View.VISIBLE);
        baseBinding.myRecipesButton.setVisibility((enabled) ? View.GONE : View.VISIBLE);
        baseBinding.newRecipeButton.setVisibility((enabled) ? View.GONE : View.VISIBLE);
        baseBinding.logoutButton.setVisibility((enabled) ? View.GONE : View.VISIBLE);

        baseBinding.userNameInput.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.userNameInput.setEnabled(enabled);
        baseBinding.profileEditingTag.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cameraButtonUser.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cameraButtonUser.setClickable(enabled);
        baseBinding.galleryButtonUser.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.galleryButtonUser.setClickable(enabled);
        baseBinding.saveUserButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cancelButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
    }


    protected void deleteButVisibility(boolean visible) {
//        baseBinding.deleteBtn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

//    abstract void onClickAction();

    abstract void setUserViewField();
}