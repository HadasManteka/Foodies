package com.example.foodies;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        setAddImgBtInvisible();
        setEditMode(false);
        baseBinding.recipeActionBtn.setText("Edit");
        baseBinding.galleryButton.setVisibility(View.INVISIBLE);
        baseBinding.cameraButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClickAction() {
        // Edit
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();
//
////        Recipe re = new Recipe("cookie", "bake", "df", "df", "sf", new byte[2]);
////        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
////        BaseRecipeFragment recipeFrag = BaseRecipeFragment.newInstance(re, false);
//////            recipeFrag.setEditMode(false);
//////            recipeFrag.setAddImgBtInvisible();
////        ft.replace(binding.fragmentBaseRecipe2.getId(), recipeFrag);
////        ft.commit();
//        return view;
//    }
}