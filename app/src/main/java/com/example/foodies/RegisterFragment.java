package com.example.foodies;

import static com.example.foodies.util.ProgressDialog.hideProgressDialog;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
//        FragmentActivity parentActivity = getActivity();
//        parentActivity.addMenuProvider(new MenuProvider() {
//            @Override
//            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menu.removeItem(R.id.addStudentFragment);
//            }
//
//            @Override
//            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
//                return false;
//            }
//        },this, Lifecycle.State.RESUMED);

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

            if(!validateLinkForm()) {
                return;
            }

            ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));

            String name = binding.nameEt.getText().toString();
            String password = binding.passwordEt.getText().toString();
            User user = new User(name,password,"");
            binding.avatarImg.setDrawingCacheEnabled(true);
            binding.avatarImg.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
            UserModel.instance().uploadImage(name, bitmap, url->{

                if (url != null) {
                    user.setImgUrl(url);
                }
                UserModel.instance().addUser(user, (unused) -> {
                    ProgressDialog.hideProgressDialog();
//                        Navigation.findNavController(view1).popBackStack();
                });
            });
        });

//        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.studentsListFragment,false));

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;
    }

    private boolean validateLinkForm() {
        if (TextUtils.isEmpty(binding.nameEt.getText().toString())) {
            binding.nameEt.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(binding.passwordEt.getText().toString())) {
            binding.passwordEt.setError("Required.");
            return false;
        } else if (!isAvatarSelected) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("Hi chef,");
            alertDialog.setMessage("Must select an image!");
            alertDialog.show();
            return false;
        } else {
            return true;
        }
    }
}