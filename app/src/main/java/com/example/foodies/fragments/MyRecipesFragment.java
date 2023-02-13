package com.example.foodies.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodies.MainActivity;
import com.example.foodies.databinding.FragmentAllRecipesBinding;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;
import com.example.foodies.model.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MyRecipesFragment extends AllRecipesFragment {
    private String userId;
    private String userName;
    List<Recipe> allUserData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = User.getUser().getId();
        userName = User.getUser().nickName;
    }

    @Override
    public List<Recipe> getAllData() {
        return allUserData;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        activity.enableNavigationIcon(true);

        binding = FragmentAllRecipesBinding.inflate(inflater, container, false);
        initRecipeRecyclerView();

        View view = binding.getRoot();

        binding.titleLabel.setText(userName + "'s Recipes");

        setFilters(view);

        adapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Row was clicked " + pos);
            Recipe recipe = adapter.getData().get(pos);

            NavHostFragment.findNavController(MyRecipesFragment.this).navigate(
                    MyRecipesFragmentDirections.actionMyRecipesFragmentToRecipeDetailsFragment(recipe));
        });

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            allUserData = list.stream().filter(recipe -> (Objects.nonNull(recipe.userId)) ? recipe.userId.equals(userId) : false).collect(Collectors.toList());
            Collections.sort(allUserData, Collections.reverseOrder());
            adapter.setData(allUserData);
        });

        RecipeModel.instance().EventRecipesListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefresh.setRefreshing(status == RecipeModel.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(this::reloadData);

        return view;
    }
}