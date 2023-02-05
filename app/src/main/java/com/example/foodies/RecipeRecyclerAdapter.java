package com.example.foodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.model.recipe.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView titleTv;
    TextView timeTv;
    ImageView imgView;
    List<Recipe> data;

    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnItemClickListener listener, List<Recipe> data) {
        super(itemView);
        this.data = data;
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

        if (!Objects.equals(recipe.getImgUrl(), "")) {
            Picasso.get().load(recipe.getImgUrl()).placeholder(R.drawable.camera_img).into(imgView);
        } else {
            imgView.setImageResource(R.drawable.camera_img);
        }
    }
}

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Recipe> data;

    public void setData(List<Recipe> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Recipe> getData() {
        return data;
    }

    public RecipeRecyclerAdapter(LayoutInflater inflater, List<Recipe> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homepage_recipe_item_row, parent, false);
        return new RecipeViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = data.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}