package com.example.foodies.fragments.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodies.R;
import com.example.foodies.model.user.UserModel;
import com.example.foodies.util.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditUserProfileFragment extends BaseUserProfileFragment {
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    private boolean isImageSelected;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isImageSelected = false;

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                baseBinding.userImageInput.setImageBitmap(result);
                isImageSelected = true;
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                baseBinding.userImageInput.setImageURI(result);
                isImageSelected = true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        baseBinding.cameraButtonUser.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        baseBinding.galleryButtonUser.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }

    @Override
    public void setUserViewField() {
        if (Objects.nonNull(currentUser)) {
            baseBinding.userNicknameInput.setText(currentUser.nickName);

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

        if (isImageSelected) {
            baseBinding.userImageInput.setDrawingCacheEnabled(true);
            baseBinding.userImageInput.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) baseBinding.userImageInput.getDrawable()).getBitmap();
            UserModel.instance().uploadProfileImage(currentUser.getId(), bitmap, url -> {
                if (url != null) {
                    currentUser.setImgUrl(url);
                }
            });
        }
        currentUser.setNickName(baseBinding.userNicknameInput.getText().toString());

        UserModel.instance().updateUser(currentUser, (unused) -> {
            alertDialog.setTitle("User '" + currentUser.getNickName() + "'")
                    .setMessage("Updated successfully.").show();
            ProgressDialog.hideProgressDialog();
            backToUserProfile();
        });

    }

    private boolean validateLinkForm() {
        if (TextUtils.isEmpty(baseBinding.userNicknameInput.getText().toString())) {
            baseBinding.userNicknameInput.setError("Required.");
            return false;
        } else if (Objects.equals(currentUser.getImgUrl(), "")) {
            alertDialog.setTitle("Hi chef,")
                    .setMessage("Must select an image!").show();
            return false;
        } else {
            return true;
        }
    }

    private void backToUserProfile() {
        NavHostFragment.findNavController(EditUserProfileFragment.this).navigate(
                EditUserProfileFragmentDirections.actionEditUserProfileFragmentToUserProfileFragment());
    }
}