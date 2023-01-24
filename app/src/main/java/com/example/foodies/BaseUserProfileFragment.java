package com.example.foodies;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.foodies.databinding.FragmentBaseRecipeBinding;
import com.example.foodies.databinding.FragmentBaseUserProfileBinding;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;

abstract class BaseUserProfileFragment extends Fragment {

    AlertDialog.Builder alertDialog;
    String userName;
    ActivityResultLauncher<Void> cameraLauncher;
    protected FragmentBaseUserProfileBinding baseBinding;
    boolean isAvatarSelected;
    ActivityResultLauncher<String> galleryLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());
        Bundle bundle = getArguments();
        if (bundle != null){
            userName = bundle.getString("userName");
        }

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                baseBinding.userImageInput.setImageBitmap(result);
                isAvatarSelected = true;
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null){
                baseBinding.userImageInput.setImageURI(result);
                isAvatarSelected = true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseBinding = FragmentBaseUserProfileBinding.inflate(inflater, container, false);
        View view = baseBinding.getRoot();

        deleteButVisibility(false);

        baseBinding.cameraButtonUser.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        baseBinding.galleryButtonUser.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        baseBinding.buttonEditProfile.setOnClickListener(v -> {
            setEditMode(true);
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
        baseBinding.profileEditingTag.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cameraButtonUser.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cameraButtonUser.setClickable(enabled);
        baseBinding.galleryButtonUser.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.galleryButtonUser.setClickable(enabled);
        baseBinding.saveUserButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        baseBinding.cancelButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
    }

    protected boolean validateLinkForm() {
        if (TextUtils.isEmpty(baseBinding.userNameInput.getText().toString())) {
            baseBinding.userNameInput.setError("Required.");
            return false;
        } else if (!isAvatarSelected) {
            alertDialog.setTitle("Hi chef,")
                    .setMessage("Must select an image!").show();
            return false;
        } else {
            return true;
        }
    }

    protected User getUser() {
        String nickname = baseBinding.userNameInput.getText().toString();
        String imgUrl = baseBinding.userImageInput.getDrawable().toString();

        User user = new User();
        user.setNickName(nickname);
        user.setImgUrl(imgUrl);
        return user;
    }

    protected void deleteButVisibility(boolean visible) {
//        baseBinding.deleteBtn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    abstract void onClickAction();
    abstract void setUserViewField();
}