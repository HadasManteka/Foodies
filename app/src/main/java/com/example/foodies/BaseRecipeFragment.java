package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.foodies.databinding.FragmentAddRecipeBinding;
import com.example.foodies.databinding.FragmentBaseRecipeBinding;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;

public class BaseRecipeFragment extends Fragment{

    FragmentBaseRecipeBinding binding;
    String title;
    String category;
    String time;
    String ingredients;
    String description;
    String recipeImgUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBaseRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Spinner spinnerCategories = view.findViewById(R.id.recipe_category);
        spinnerCategories.setAdapter(new ArrayAdapter<RecipeCategoryEnum>(this.getContext(), android.R.layout.simple_spinner_item, RecipeCategoryEnum.values()));

//        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                Log.v("item", (String) parent.getItemAtPosition(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        Spinner spinnerMadeTime = view.findViewById(R.id.recipe_time);
        spinnerMadeTime.setAdapter(new ArrayAdapter<RecipeMadeTimeEnum>(this.getContext(), android.R.layout.simple_spinner_item, RecipeMadeTimeEnum.values()));

//        spinnerMadeTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                Log.v("item", (String) parent.getItemAtPosition(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        return view;
    }

    public void clickButton() {
        title = binding.recipeTitle.toString();
        category = binding.recipeCategory.getSelectedItem().toString();
        time = binding.recipeTime.getSelectedItem().toString();
        ingredients = binding.recipeIngredients.toString();
        description = binding.recipeDescription.toString();
    }
}