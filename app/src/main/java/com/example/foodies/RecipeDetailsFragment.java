package com.example.foodies;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
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

    public static RecipeDetailsFragment newInstance(Recipe recipe){
        RecipeDetailsFragment frag = new RecipeDetailsFragment();
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
        recipe = new Recipe("cookie", "bake", "5-10 min", "df", "sf","https://firebasestorage.googleapis.com/v0/b/my-firebase-42f34.appspot.com/o/images%2Fandroidx.appcompat.widget.AppCompatEditText%7B5097822%20VFED..CL.%20........%2021%2C546-571%2C664%20%237f080174%20app%3Aid%2Frecipe_title%20aid%3D1073741827%7D.jpg?alt=media&token=862f29d5-2f4f-4160-b836-7fa8597e1f5d");

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
        baseBinding.recipeActionBtn.setText("Edit");
        baseBinding.galleryButton.setVisibility(View.INVISIBLE);
        baseBinding.cameraButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClickAction() {
        // Edit
    }
}