package com.example.foodies.fragments.singleRecipe;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.navigation.fragment.NavHostFragment;

import com.example.foodies.R;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.util.ProgressDialog;


public class AddRecipeFragment extends BaseRecipeFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setRecipeViewField() {
        baseBinding.recipeActionBtn.setText("Create");

    }

    @Override
    void onClickAction() {
        if (!validateLinkForm()) {
            return;
        }

        ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));

        Recipe recipe = getRecipe();
        baseBinding.recipeImg.setDrawingCacheEnabled(true);
        baseBinding.recipeImg.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) baseBinding.recipeImg.getDrawable()).getBitmap();
        RecipeModel.instance().uploadImage(recipe.getId(), bitmap, url -> {

            if (url != null) {
                recipe.setImgUrl(url);
            }

            RecipeModel.instance().addRecipe(recipe, (unused) -> {
                alertDialog.setTitle("Recipe '" + recipe.getTitle() + "'")
                        .setMessage("Created successfully.").show();
                ProgressDialog.hideProgressDialog();
            });
        });

        navigateBackToHomePage();
    }

    private void navigateBackToHomePage() {
        NavHostFragment.findNavController(AddRecipeFragment.this).navigate(
                AddRecipeFragmentDirections.actionAddRecipeFragmentToHomePageFragment());
    }
}