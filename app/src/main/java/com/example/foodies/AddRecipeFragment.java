package com.example.foodies;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.example.foodies.model.Model;
import com.example.foodies.model.Recipe;

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

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    void onClickAction() {
//        if (recipeImgUrl.length() == 0) {
//            System.out.println("must upload photo");
//        } else {
            Bitmap chosenPhoto = ((BitmapDrawable)baseBinding.imageView2.getDrawable()).getBitmap();
            Recipe recipe = new Recipe(title, category, time, ingredients, description, chosenPhoto == null ? null :getBitmapAsByteArray(chosenPhoto));
            Model.instance().addRecipe(recipe, () -> {
                System.out.println("success");
                Model.instance().getAllRecipes((stList)->{
                    System.out.println(stList);
//                binding.progressBar.setVisibility(View.GONE);
                });
            });
//                Navigation.findNavController(view1).popBackStack();
//        }
    }


}