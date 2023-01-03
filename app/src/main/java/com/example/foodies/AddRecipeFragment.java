package com.example.foodies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodies.databinding.FragmentAddRecipeBinding;
import com.example.foodies.model.Model;
import com.example.foodies.model.Recipe;


public class AddRecipeFragment extends BaseRecipeFragment {
    FragmentAddRecipeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.studentsListFragment,false));
        return view;
    }

    void doAction() {
//        if (recipeImgUrl.length() == 0) {
//            System.out.println("must upload photo");
//        } else {
        Recipe recipe = new Recipe(title, category, time, ingredients, description, recipeImgUrl);
        Model.instance().addRecipe(recipe, () -> {
            System.out.println("success");
//                Navigation.findNavController(view1).popBackStack();
        });
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recipeCreateBtn.setOnClickListener(view1 -> {
            this.clickButton();
            doAction();
        });
    }
}