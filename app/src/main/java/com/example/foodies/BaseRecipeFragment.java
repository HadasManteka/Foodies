package com.example.foodies;

import static com.example.foodies.util.FileActions.getBitmapAsByteArray;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.foodies.databinding.FragmentBaseRecipeBinding;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;
import com.example.foodies.model.user.UserModel;
import com.example.foodies.util.ProgressDialog;

abstract class BaseRecipeFragment extends Fragment {

    protected FragmentBaseRecipeBinding baseBinding;
    String title;
    String category;
    String time;
    String ingredients;
    String description;
    String recipeImgUrl;
//    private ImageListener mListener;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    boolean isAvatarSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            title = bundle.getString("title");
        }

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                baseBinding.recipeImg.setImageBitmap(result);
                isAvatarSelected = true;
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null){
                baseBinding.recipeImg.setImageURI(result);
                isAvatarSelected = true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseBinding = FragmentBaseRecipeBinding.inflate(inflater, container, false);
        View view = baseBinding.getRoot();


        baseBinding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        baseBinding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });


        // Spinners
        baseBinding.recipeCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, RecipeCategoryEnum.values()));
        baseBinding.recipeTime.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, RecipeMadeTimeEnum.values()));

        baseBinding.recipeActionBtn.setOnClickListener(v -> {
            onClickAction();
        });

        setRecipeViewField();
        return view;
    }

//    public interface ImageListener {
//        void onSelectImage();
//    }
//
//    public void onImageSelected(Bitmap thumbnail) {
//        baseBinding.recipeImg.setImageBitmap(thumbnail);
//    }

    protected void setEditMode(boolean enabled) {
        baseBinding.recipeTitle.setEnabled(enabled);
        baseBinding.recipeCategory.setEnabled(enabled);
        baseBinding.recipeTime.setEnabled(enabled);
        baseBinding.recipeIngredients.setEnabled(enabled);
        baseBinding.recipeDescription.setEnabled(enabled);
//        baseBinding.recipeImg.setEnabled(enabled); // TODO: should disable button, not img
    }

    protected void setAddImgBtInvisible() {
//        baseBinding.recipeImg.setVisibility(View.INVISIBLE); // TODO: should disable button, not img
    }

    protected boolean validateLinkForm() {
        if (TextUtils.isEmpty(baseBinding.recipeTitle.getText().toString())) {
            baseBinding.recipeTitle.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(baseBinding.recipeIngredients.getText().toString())) {
            baseBinding.recipeIngredients.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(baseBinding.recipeDescription.getText().toString())) {
            baseBinding.recipeDescription.setError("Required.");
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

    protected Recipe getRecipe() {
        String title = baseBinding.recipeTitle.getText().toString();
        String time = baseBinding.recipeTime.toString();
        String category = baseBinding.recipeCategory.toString();
        String ingredients = baseBinding.recipeIngredients.toString();
        String desc = baseBinding.recipeDescription.toString();

        baseBinding.recipeImg.setDrawingCacheEnabled(true);
        baseBinding.recipeImg.buildDrawingCache();
        Bitmap chosenPhotoBm = ((BitmapDrawable)baseBinding.recipeImg.getDrawable()).getBitmap();
        // binding
        return new Recipe(title, category, time, ingredients, desc, getBitmapAsByteArray(chosenPhotoBm));
    }

    abstract void onClickAction();
    abstract void setRecipeViewField();

}