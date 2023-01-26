package com.example.foodies;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Display the selected recipe details
 */
public class RecipeDetailsFragment extends BaseRecipeFragment {

    private Recipe recipe;

    public RecipeDetailsFragment() {
    }

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment frag = new RecipeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = (Recipe) bundle.getSerializable("recipe");
        }
    }

    @Override
    public void setRecipeViewField() {
        baseBinding.recipeTitle.setText(recipe.title);
        baseBinding.recipeIngredients.setText(recipe.ingredients);
        baseBinding.recipeDescription.setText(recipe.description);

        if (!Objects.equals(recipe.getImgUrl(), "")) {
            Picasso.get().load(recipe.getImgUrl()).placeholder(R.drawable.camera_img).into(baseBinding.recipeImg);
        }else{
            baseBinding.recipeImg.setImageResource(R.drawable.camera_img);
        }

        List<RecipeCategoryEnum> lstCat = new ArrayList<>(Arrays.asList(RecipeCategoryEnum.values().clone()));
        lstCat.add(0, RecipeCategoryEnum.getCategoryByText(recipe.category));
        baseBinding.recipeCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, lstCat));

        List<RecipeMadeTimeEnum> lstTime = new ArrayList<>(Arrays.asList(RecipeMadeTimeEnum.values().clone()));
        lstTime.add(0, RecipeMadeTimeEnum.getEnumByValue(recipe.time));
        baseBinding.recipeTime.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, lstTime));

        setAddImgBtInvisible();
        setEditMode(false);

        if (User.getUser().getId().equals(recipe.getUserID())) {
            baseBinding.recipeActionBtn.setText("Edit");
        } else {
            baseBinding.recipeActionBtn.setVisibility(View.INVISIBLE);
        }

        baseBinding.galleryButton.setVisibility(View.INVISIBLE);
        baseBinding.cameraButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClickAction() {
        RecipeDetailsFragmentDirections.ActionRecipeDetailsFragmentToUpdateRecipeFragment action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToUpdateRecipeFragment(recipe);
        Navigation.findNavController(baseBinding.getRoot()).navigate(action);
    }
}