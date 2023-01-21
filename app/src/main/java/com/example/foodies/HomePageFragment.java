package com.example.foodies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodies.databinding.HomePageBinding;
import com.example.foodies.model.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    RecipeRecyclerAdapter adapter;
    HomePageBinding binding;
    CardView currentTimeFilter;

    public HomePageFragment() {
        super(R.layout.home_page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecipeRecyclerAdapter(getLayoutInflater(), new ArrayList<>());
    }

    private void setTimeFilters(View view) {
        setCurrentTimeFilter(view, R.id.fiveTenTime, "5-10 min");
        setCurrentTimeFilter(view, R.id.tenThirtyTime, "10-30 min");
        setCurrentTimeFilter(view, R.id.thirtySixtyTime, "30-60 min");
        setCurrentTimeFilter(view, R.id.sixtyPlusTime, "60+ min");
    }

    private void setCurrentTimeFilter(View view, int id, String timeFilter) {
        CardView card = view.findViewById(id);
        card.setOnClickListener(view1 -> {
            if (currentTimeFilter != null) {
                currentTimeFilter.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.clock_background));
            }
            if (currentTimeFilter == card) {
                adapter.setData(adapter.getAllRecipes());
                currentTimeFilter = null;
            } else {
                filterDataByTime(timeFilter);
                card.setCardBackgroundColor(ContextCompat.getColor(getContext(), com.google.android.material.R.color.cardview_dark_background));
                currentTimeFilter = card;
            }
        });
    }

    private void filterDataByTime(String timeFilter) {
        List<Recipe> data = adapter.getAllRecipes();
        List<Recipe> newData = new ArrayList<>();
        for (Recipe recipe : data) {
            if (recipe.time == timeFilter) {
                newData.add(recipe);
            }
        }
        adapter.setData(newData);
    }

    private void initRecipeRecyclerView() {
        binding.homeRecipeView.setHasFixedSize(true);
        binding.homeRecipeView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeRecipeView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomePageBinding.inflate(inflater, container, false);
        initRecipeRecyclerView();

        View view = binding.getRoot();

        setTimeFilters(view);

        adapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Row was clicked " + pos);
            Recipe recipe = adapter.getRecipes().get(pos);
            List<Recipe> data = adapter.getRecipes();

            adapter.setData(data.subList(pos, pos + 1));
//            HomePageFragmentDirections.ActionHomePageFragmentToRecipeDetailsFragment action = HomePageFragmentDirections.actionHomePageFragmentToRecipeDetailsFragment(recipe);
//            HomePageFragmentDirections.ActionHomePageFragmentToUserProfileFragment action = HomePageFragmentDirections.actionHomePageFragmentToUserProfileFragment();
//            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}