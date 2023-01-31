package com.example.foodies;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodies.databinding.FragmentRegisterBinding;
import com.example.foodies.model.user.User;
import com.example.foodies.model.user.UserModel;
import com.example.foodies.util.ProgressDialog;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                binding.avatarImg.setImageBitmap(result);
                isAvatarSelected = true;
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null){
                binding.avatarImg.setImageURI(result);
                isAvatarSelected = true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.signUpBtn.setOnClickListener(view1 -> {
            if (!validateLinkForm()) {
                return;
            }

            ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));
            binding.avatarImg.setDrawingCacheEnabled(true);
            binding.avatarImg.buildDrawingCache();

            String name = binding.nameEt.getText().toString();
            String email = binding.emailEt.getText().toString();
            String password = binding.passwordEt.getText().toString();
            User user = new User(name, email, "");

            Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();

            UserModel.instance().doesEmailExists(email, (isExists) -> {
                if (isExists) {
                    ProgressDialog.hideProgressDialog();
                    new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Email " + email + " already exists").show();
                } else {
                    UserModel.instance().register(email, password, (Void) -> {
                        UserModel.instance().uploadProfileImage(email, bitmap, url -> {
                            if (url != null) {
                                user.setImgUrl(url);
                            }
                            UserModel.instance().addUser(user, (unused) -> {
                                ProgressDialog.hideProgressDialog();
                                Navigation.findNavController(binding.getRoot()).navigate(
                                        RegisterFragmentDirections.actionRegisterFragmentToLoginFragment());

                                //                        Navigation.findNavController(view1).popBackStack();
                            });
                        });
                    });
                }
            });
        });

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;
    }

    private boolean validateLinkForm() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        if (TextUtils.isEmpty(binding.nameEt.getText().toString())) {
            binding.nameEt.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(binding.emailEt.getText().toString())) {
            binding.emailEt.setError("Required.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.getText().toString()).matches()) {
            binding.emailEt.setError("Required an email.");
            return false;
        } else if (TextUtils.isEmpty(binding.passwordEt.getText().toString())) {
            binding.passwordEt.setError("Required.");
            return false;
        }else if (binding.passwordEt.getText().toString().length() < 6){
            binding.passwordEt.setError("Password must be >= 6 Characters");
            return false;
        } else if (!isAvatarSelected) {
            alertDialog.setTitle("Hi chef,");
            alertDialog.setMessage("Must select an image!");
            alertDialog.show();
            return false;
        } else {
            return true;
        }
    }
}