package com.example.foodies;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodies.databinding.FragmentHomePageBinding;
import com.example.foodies.model.recipe.Recipe;
import com.example.foodies.model.recipe.RecipeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomePageFragment extends Fragment {
    RecipeRecyclerAdapter adapter = null;
    FragmentHomePageBinding binding;
    CardView currentTimeFilter;
    CardView currentCategoryFilter;
    String searchQuery = "";
    RecipeListFragmentViewModel viewModel;

    private void setFilters(View view) {
        List<CardView> timeCards = Arrays.asList(view.findViewById(R.id.fiveTenTime), view.findViewById(R.id.tenThirtyTime),
                view.findViewById(R.id.thirtySixtyTime), view.findViewById(R.id.sixtyPlusTime));
        timeCards.forEach(timeCard -> setCurrentFilter(timeCard, "time"));

        List<CardView> categoryCards = Arrays.asList(view.findViewById(R.id.bakingCard), view.findViewById(R.id.cookCard),
                view.findViewById(R.id.saladCard), view.findViewById(R.id.drinkCard));
        categoryCards.forEach(categoryCard -> setCurrentFilter(categoryCard, "category"));

        setSearchFilter(view);
    }

    private void setSearchFilter(View view) {
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchQuery = s;
                filterData(Objects.nonNull(currentTimeFilter) ? (String) currentTimeFilter.getContentDescription() : "",
                        Objects.nonNull(currentCategoryFilter) ? (String) currentCategoryFilter.getContentDescription() : "",
                        searchQuery);
                return true;
            }
        });
    }

    private void setCurrentFilter(CardView card, String type) {
        card.setOnClickListener(view1 -> {
            if (type.equals("time")) setTimeFilter(card);
            if (type.equals("category")) setCategoryFilter(card);

            filterData(Objects.nonNull(currentTimeFilter) ? (String) currentTimeFilter.getContentDescription() : "",
                    Objects.nonNull(currentCategoryFilter) ? (String) currentCategoryFilter.getContentDescription() : "",
                    searchQuery);
        });
    }

    private void setTimeFilter(CardView card) {
        if (Objects.nonNull(currentTimeFilter)) {
            currentTimeFilter.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.clock_background));
        }
        if (currentTimeFilter == card) {
            currentTimeFilter = null;
        } else {
            currentTimeFilter = card;
            card.setCardBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
        }
    }

    private void setCategoryFilter(CardView card) {
        if (Objects.nonNull(currentCategoryFilter)) {
            currentCategoryFilter.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        }
        if (currentCategoryFilter == card) {
            currentCategoryFilter = null;
        } else {
            currentCategoryFilter = card;
            card.setCardBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
        }
    }

    private void filterData(String timeFilter, String categoryFilter, String searchQuery) {
        List<Recipe> data = viewModel.getData().getValue();
        data.addAll(viewModel.getApiRecipes().getValue());
        List<Recipe> newData = new ArrayList<>();
        for (Recipe recipe : data) {
            if ((timeFilter.equals("") || recipe.time.equals(timeFilter)) &&
                    (categoryFilter.equals("") || recipe.category.equals(categoryFilter)) &&
                    (searchQuery.equals("") || (recipe.title.toLowerCase()).contains(searchQuery.toLowerCase()))) {
                newData.add(recipe);
            }
        }
        adapter.setData(newData);
    }

    private void initRecipeRecyclerView() {
        binding.homeRecyclerView.setHasFixedSize(true);
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RecipeRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.homeRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(RecipeListFragmentViewModel.class);
    }

    void reloadData(){
        RecipeModel.instance().refreshAllRecipes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        initRecipeRecyclerView();
        
        View view = binding.getRoot();

        setFilters(view);

        adapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Row was clicked " + pos);
            Recipe recipe = viewModel.getData().getValue().get(pos);

            HomePageFragmentDirections.ActionHomePageFragmentToRecipeDetailsFragment action = HomePageFragmentDirections.actionHomePageFragmentToRecipeDetailsFragment(recipe);
            Navigation.findNavController(view).navigate(action);
        });

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            if (viewModel.getApiRecipes().getValue() != null) {
                list.addAll(viewModel.getApiRecipes().getValue());
            }

            adapter.setData(list);
        });

        viewModel.getApiRecipes().observe(getViewLifecycleOwner(),list->{
            if (viewModel.getData().getValue() != null) {
                viewModel.getData().getValue().addAll(list);
            }

            adapter.setData(viewModel.getData().getValue());
        });

        RecipeModel.instance().EventRecipesListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.swipeRefresh.setRefreshing(status == RecipeModel.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(this::reloadData);

        return view;
    }
}