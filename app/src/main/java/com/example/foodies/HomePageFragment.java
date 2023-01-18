package com.example.foodies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.databinding.HomePageBinding;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.request.ApiRecipeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageFragment extends Fragment {
    RecipeRecyclerAdapter adapter;
    HomePageBinding binding;

    public HomePageFragment() {
        super(R.layout.home_page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecipeRecyclerAdapter(getLayoutInflater(),new ArrayList<>());
    }

    private void initRecipeRecyclerView() {
        binding.homeRecipeView.setHasFixedSize(true);
        binding.homeRecipeView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeRecipeView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.home_page, container, false);
        binding = HomePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        mRecipeRecyclerView = view.findViewById(R.id.home_recipe_view);

        initRecipeRecyclerView();

        return view;
    }
}