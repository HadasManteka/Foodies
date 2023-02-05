package com.example.foodies.fragments.singleRecipe;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.navigation.Navigation;

import com.example.foodies.R;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.util.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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
        deleteButVisibility(true);
        baseBinding.recipeTitle.setText(recipe.title);
        baseBinding.recipeIngredients.setText(recipe.ingredients);
        baseBinding.recipeDescription.setText(recipe.description);

        if (!Objects.equals(recipe.getImgUrl(), "")) {
            Picasso.get().load(recipe.getImgUrl()).placeholder(R.drawable.camera_img).into(baseBinding.recipeImg);
        }else{
            baseBinding.recipeImg.setImageResource(R.drawable.camera_img);
        }

        List<RecipeCategoryEnum> lstCat = new ArrayList<>(Arrays.asList(RecipeCategoryEnum.values().clone()));
        RecipeCategoryEnum category = RecipeCategoryEnum.getCategoryByText(recipe.category);
        lstCat.remove(category);
        lstCat.add(0, category);
        baseBinding.recipeCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, lstCat));

        List<RecipeMadeTimeEnum> lstTime = new ArrayList<>(Arrays.asList(RecipeMadeTimeEnum.values().clone()));
        RecipeMadeTimeEnum madeTime = RecipeMadeTimeEnum.getEnumByValue(recipe.time);
        lstTime.remove(madeTime);
        lstTime.add(0, madeTime);
        baseBinding.recipeTime.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, lstTime));


        baseBinding.deleteBtn.setOnClickListener(v -> {
            ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));
            RecipeModel.instance().deleteRecipe(recipe, (unused) ->
                RecipeModel.instance().deleteRecipeImage(recipe.getId(), (uu) -> {
                    alertDialog.setTitle("Recipe '" + recipe.getTitle() + "'")
                            .setMessage("Deleted successfully.").show();
                    ProgressDialog.hideProgressDialog();
                    navigateBackToDetails();
            }));
        });

        baseBinding.recipeActionBtn.setText("SAVE");
    }

    @Override
    void onClickAction() {

        if(!validateLinkForm()) {
            return;
        }

        ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));

        String savedPrevId = recipe.getId();
        recipe = getRecipe();
        recipe.setId(savedPrevId);

        baseBinding.recipeImg.setDrawingCacheEnabled(true);
        baseBinding.recipeImg.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) baseBinding.recipeImg.getDrawable()).getBitmap();
        RecipeModel.instance().uploadImage(recipe.getId(), bitmap, url-> {

            if (url != null) {
                recipe.setImgUrl(url);
            }

            RecipeModel.instance().updateRecipe(recipe, (unused) -> {
                alertDialog.setTitle("Recipe '" + recipe.getTitle() + "'")
                        .setMessage("Updated successfully.").show();
                ProgressDialog.hideProgressDialog();
                navigateBackToDetails();
            });
        });
    }

    private void navigateBackToDetails() {
        Navigation.findNavController(baseBinding.getRoot()).popBackStack();
//        UpdateRecipeFragmentDirections.ActionRecipeUpdateFragmentToRecipeDetailsFragment action = UpdateRecipeFragmentDirections.actionRecipeUpdateFragmentToRecipeDetailsFragment(recipe);
//        Navigation.findNavController(baseBinding.getRoot()).navigate(action);
    }


}