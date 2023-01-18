package com.example.foodies;

import static com.example.foodies.util.FileActions.getBitmapAsByteArray;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;
import com.example.foodies.util.ProgressDialog;

import java.io.ByteArrayOutputStream;


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
        if(!validateLinkForm()) {
            return;
        }

        ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));


        RecipeModel.instance().addRecipe(getRecipe(), () -> {
            System.out.println("success");
            ProgressDialog.hideProgressDialog();
//            RecipeModel.instance().getAllRecipes((stList)->{
//                System.out.println(stList);
////                binding.progressBar.setVisibility(View.GONE);
//            });
        });
//              Navigation.findNavController(view1).popBackStack();
//        }
    }


}