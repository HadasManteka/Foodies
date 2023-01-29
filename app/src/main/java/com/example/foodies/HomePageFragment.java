package com.example.foodies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.foodies.databinding.FragmentAllRecipesBinding;
import com.example.foodies.model.FirebaseModel;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends AllRecipesFragment {

    private List<Recipe> allData = new ArrayList<>();
    private FirebaseModel firebaseModel = new FirebaseModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllRecipesBinding.inflate(inflater, container, false);
        initRecipeRecyclerView();

        View view = binding.getRoot();

        setFilters(view);

        adapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Row was clicked " + pos);
            Recipe recipe = viewModel.getData().getValue().get(pos);

            HomePageFragmentDirections.ActionHomePageFragmentToRecipeDetailsFragment action = HomePageFragmentDirections.actionHomePageFragmentToRecipeDetailsFragment(recipe);
            Navigation.findNavController(view).navigate(action);
        });

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            if (viewModel.getApiRecipes().getValue() != null) {
                list.addAll(viewModel.getApiRecipes().getValue());
            }

            adapter.setData(list);
        });

        viewModel.getApiRecipes().observe(getViewLifecycleOwner(), list -> {
            if (viewModel.getData().getValue() != null) {
                viewModel.getData().getValue().addAll(list);
            }

            adapter.setData(viewModel.getData().getValue());
        });

        RecipeModel.instance().EventRecipesListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefresh.setRefreshing(status == RecipeModel.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(this::reloadData);

        return view;
    }
}