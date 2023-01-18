package com.example.foodies;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.util.ProgressDialog;

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
            recipe = new Recipe("cookie", "bake", "5-10 min", "df", "sf", new byte[2]);

        }
    }

    @Override
    public void setRecipeViewField() {
        baseBinding.recipeTitle.setText(recipe.title);
        baseBinding.recipeIngredients.setText(recipe.ingredients);
        baseBinding.recipeDescription.setText(recipe.description);
        baseBinding.recipeImg.setImageBitmap(BitmapFactory.decodeByteArray(recipe.recipeImgBytes, 0, recipe.recipeImgBytes.length));

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

        if(!validateLinkForm()) {
            return;
        }

        ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));

        RecipeModel.instance().updateRecipe(getRecipe(), () -> {
            System.out.println("success");
//            RecipeModel.instance().getAllRecipes((stList)->{
//                ProgressDialog.hideProgressDialog();
//                System.out.println(stList);
////                binding.progressBar.setVisibility(View.GONE);
//            });
        });
    }


}