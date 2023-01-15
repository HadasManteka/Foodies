package com.example.foodies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.Model;
import com.example.foodies.model.Recipe;
import com.example.foodies.util.FileActions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UpdateRecipeFragment extends BaseRecipeFragment {
    private Recipe recipe;

    public static UpdateRecipeFragment newInstance(Recipe recipe){
        UpdateRecipeFragment frag = new UpdateRecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe",recipe);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            recipe = (Recipe) bundle.getSerializable("recipe");
        }
    }

    @Override
    public void setRecipeViewField() {
        Recipe recipe = new Recipe("cookie", "bake", "5-10 min", "df", "sf", new byte[2]);
        baseBinding.recipeTitle.setText(recipe.title);
        baseBinding.recipeIngredients.setText(recipe.ingredients);
        baseBinding.recipeDescription.setText(recipe.description);
        baseBinding.imageView2.setImageBitmap(BitmapFactory.decodeByteArray(recipe.recipeImgBytes, 0, recipe.recipeImgBytes.length));

        List<RecipeCategoryEnum> lstCat = new ArrayList<>(Arrays.asList(RecipeCategoryEnum.values().clone()));
        lstCat.add(0, RecipeCategoryEnum.getCategoryByText(recipe.category));
        baseBinding.recipeCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, lstCat));

        List<RecipeMadeTimeEnum> lstTime = new ArrayList<>(Arrays.asList(RecipeMadeTimeEnum.values().clone()));
        lstTime.add(0, RecipeMadeTimeEnum.getEnumByValue(recipe.time));
        baseBinding.recipeTime.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, lstTime));

        baseBinding.recipeActionBtn.setText("SAVE");
    }

    @Override
    void onClickAction() {
        Bitmap chosenPhoto = ((BitmapDrawable)baseBinding.imageView2.getDrawable()).getBitmap();
        Recipe recipe = new Recipe(title, category, time, ingredients, description, chosenPhoto == null ? null : FileActions.getBitmapAsByteArray(chosenPhoto));
        Model.instance().updateRecipe(recipe, () -> {
            System.out.println("success");
            Model.instance().getAllRecipes((stList)->{
                System.out.println(stList);
//                binding.progressBar.setVisibility(View.GONE);
            });
        });
    }


}