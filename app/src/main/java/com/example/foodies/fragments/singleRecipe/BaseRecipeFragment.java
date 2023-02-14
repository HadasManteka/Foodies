package com.example.foodies.fragments.singleRecipe;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.foodies.MainActivity;
import com.example.foodies.R;
import com.example.foodies.databinding.FragmentBaseRecipeBinding;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.user.User;

abstract class BaseRecipeFragment extends Fragment {

    AlertDialog.Builder alertDialog;
    protected FragmentBaseRecipeBinding baseBinding;
    //    private ImageListener mListener;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                baseBinding.recipeImg.setImageBitmap(result);
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                baseBinding.recipeImg.setImageURI(result);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        activity.enableNavigationIcon(true);

        baseBinding = FragmentBaseRecipeBinding.inflate(inflater, container, false);
        View view = baseBinding.getRoot();

        deleteButVisibility(false);

        baseBinding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        baseBinding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });


        // Spinners
        baseBinding.recipeCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, RecipeCategoryEnum.values()));
        baseBinding.recipeTime.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, RecipeMadeTimeEnum.values()));

        baseBinding.recipeActionBtn.setOnClickListener(v -> onClickAction());

        setRecipeViewField();
        return view;
    }

    protected void setEditMode(boolean enabled) {
        baseBinding.recipeTitle.setFocusableInTouchMode(enabled);
        baseBinding.recipeTitle.clearFocus();
        baseBinding.recipeIngredients.setFocusableInTouchMode(enabled);
        baseBinding.recipeIngredients.clearFocus();
        baseBinding.recipeDescription.setFocusableInTouchMode(enabled);
        baseBinding.recipeDescription.clearFocus();

        baseBinding.recipeCategory.setEnabled(enabled);
        baseBinding.recipeTime.setEnabled(enabled);
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
        } else if (baseBinding.recipeImg.getDrawable() == null ||
                baseBinding.recipeImg.getDrawable().getConstantState().equals(ContextCompat.getDrawable(getContext(), R.drawable.camera_img).getConstantState())) {
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

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setTime(time);
        recipe.setCategory(category);
        recipe.setDescription(description);
        recipe.setIngredients(ingredients);
        recipe.setUserID(User.getUser().getId());
        return recipe;
    }

    protected void deleteButVisibility(boolean visible) {
        baseBinding.deleteBtn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    abstract void onClickAction();

    abstract void setRecipeViewField();
}