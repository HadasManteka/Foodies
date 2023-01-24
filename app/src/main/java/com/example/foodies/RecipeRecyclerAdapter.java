package com.example.foodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodies.model.recipe.Recipe;

import java.util.List;


class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView titleTv;
    TextView timeTv;
    ImageView imgView;

    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.recipe_item_title);
        timeTv = itemView.findViewById(R.id.recipe_item_time);
        imgView = itemView.findViewById(R.id.recipe_item_image);

        itemView.setOnClickListener(view -> {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        });
    }

    public void bind(Recipe recipe) {
        titleTv.setText(recipe.title);
        timeTv.setText(recipe.time);

        imgView.setImageResource(R.drawable.camera_img);
        ImageRequest ir = new ImageRequest(recipe.imgUrl, response -> {
            imgView.setImageBitmap(response);
        },
                imgView.getMeasuredWidth(), imgView.getMeasuredHeight(), null, null);

        RequestQueue requestQueue = Volley.newRequestQueue(imgView.getContext());
        requestQueue.add(ir);
    }
}

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Recipe> data;
    List<Recipe> allRecipes;

    public void setData(List<Recipe> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipes() {
        return this.data;
    }

    public List<Recipe> getAllRecipes() {
        return this.allRecipes;
    }

    public void setAllRecipes(List<Recipe> allRecipes) {
        this.allRecipes = allRecipes;
    }

    public RecipeRecyclerAdapter(LayoutInflater inflater, List<Recipe> data) {
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homepage_recipe_item_row, parent, false);
        return new RecipeViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = data.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}