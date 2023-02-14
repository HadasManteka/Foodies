package com.example.foodies.fragments.profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.foodies.MainActivity;
import com.example.foodies.databinding.FragmentBaseUserProfileBinding;
import com.example.foodies.model.user.User;
import com.example.foodies.model.user.UserModel;

abstract class BaseUserProfileFragment extends Fragment {

    AlertDialog.Builder alertDialog;
    protected FragmentBaseUserProfileBinding baseBinding;
    User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        UserModel.instance().getUser(User.getUser().getEmail(), (user) -> {
            currentUser = user;
            setUserViewField();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        activity.enableNavigationIcon(true);

        baseBinding = FragmentBaseUserProfileBinding.inflate(inflater, container, false);
        View view = baseBinding.getRoot();

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

        baseBinding.userNicknameInput.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.userNicknameInput.setEnabled(enabled);
        baseBinding.profileEditingTag.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cameraButtonUser.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cameraButtonUser.setClickable(enabled);
        baseBinding.galleryButtonUser.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.galleryButtonUser.setClickable(enabled);
        baseBinding.saveUserButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cancelButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
    }

    abstract void setUserViewField();
}