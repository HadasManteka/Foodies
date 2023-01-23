package com.example.foodies;

import android.app.AlertDialog;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.foodies.databinding.FragmentBaseRecipeBinding;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;

abstract class BaseRecipeFragment extends Fragment {

    AlertDialog.Builder alertDialog;
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
        alertDialog = new AlertDialog.Builder(getContext());
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

        deleteButVisibility(false);

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

    protected void setEditMode(boolean enabled) {
        baseBinding.recipeTitle.setEnabled(enabled);
        baseBinding.recipeCategory.setEnabled(enabled);
        baseBinding.recipeTime.setEnabled(enabled);
        baseBinding.recipeIngredients.setEnabled(enabled);
        baseBinding.recipeDescription.setEnabled(enabled);
        baseBinding.recipeImg.setEnabled(enabled);
    }

    protected void setAddImgBtInvisible() {
        baseBinding.galleryButton.setVisibility(View.INVISIBLE);
        baseBinding.cameraButton.setVisibility(View.INVISIBLE);
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
            alertDialog.setTitle("Hi chef,")
                    .setMessage("Must select an image!").show();
            return false;
        } else {
            return true;
        }
    }

    protected Recipe getRecipe() {
        String title = baseBinding.recipeTitle.getText().toString();
        String time = baseBinding.recipeTime.getSelectedItem().toString();
        String category = baseBinding.recipeCategory.getSelectedItem().toString();
        String ingredients = baseBinding.recipeIngredients.getText().toString();
        String description = baseBinding.recipeDescription.getText().toString();

//        baseBinding.recipeImg.setDrawingCacheEnabled(true);
//        baseBinding.recipeImg.buildDrawingCache();
//        Bitmap chosenPhotoBm = ((BitmapDrawable)baseBinding.recipeImg.getDrawable()).getBitmap();

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setTime(time);
        recipe.setCategory(category);
        recipe.setDescription(description);
        recipe.setIngredients(ingredients);
        return recipe;
    }

    protected void deleteButVisibility(boolean visible) {
        baseBinding.deleteBtn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    abstract void onClickAction();
    abstract void setRecipeViewField();

}