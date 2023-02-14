package com.example.foodies.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.foodies.MainActivity;
import com.example.foodies.databinding.FragmentAllRecipesBinding;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;

import java.util.Collections;
import java.util.List;

public class HomePageFragment extends AllRecipesFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public List<Recipe> getAllData() {
        return viewModel.getData().getValue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        activity.enableNavigationIcon(false);

        binding = FragmentAllRecipesBinding.inflate(inflater, container, false);
        initRecipeRecyclerView();

        View view = binding.getRoot();

        binding.titleLabel.setText("All Recipes");

        setFilters(view);

        adapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Row was clicked " + pos);
            Recipe recipe = adapter.getData().get(pos);

            HomePageFragmentDirections.ActionHomePageFragmentToRecipeDetailsFragment action = HomePageFragmentDirections.actionHomePageFragmentToRecipeDetailsFragment(recipe.id);
            Navigation.findNavController(view).navigate(action);
        });

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            Collections.sort(list, Collections.reverseOrder());
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