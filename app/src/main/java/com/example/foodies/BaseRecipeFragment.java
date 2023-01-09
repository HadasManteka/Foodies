package com.example.foodies;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.foodies.databinding.FragmentBaseRecipeBinding;
import com.example.foodies.enums.RecipeCategoryEnum;
import com.example.foodies.enums.RecipeMadeTimeEnum;

abstract class BaseRecipeFragment extends Fragment {

    protected FragmentBaseRecipeBinding baseBinding;
    String title;
    String category;
    String time;
    String ingredients;
    String description;
    String recipeImgUrl;
    private ImageListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            title = bundle.getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseBinding = FragmentBaseRecipeBinding.inflate(inflater, container, false);
        View view = baseBinding.getRoot();

        // Spinners
        baseBinding.recipeCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, RecipeCategoryEnum.values()));
        baseBinding.recipeTime.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, RecipeMadeTimeEnum.values()));

        baseBinding.recipeAddPicBt.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onSelectImage();
        });

        baseBinding.recipeActionBtn.setOnClickListener(v -> {
            onClickAction();
        });

        setRecipeViewField();
        return view;
    }

    public interface ImageListener {
        void onSelectImage();
    }

    public void onImageSelected(Bitmap thumbnail) {
        baseBinding.imageView2.setImageBitmap(thumbnail);
    }

    @Override
        public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ImageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement ImageListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setEditMode(boolean enabled) {
        baseBinding.recipeTitle.setEnabled(enabled);
        baseBinding.recipeCategory.setEnabled(enabled);
        baseBinding.recipeTime.setEnabled(enabled);
        baseBinding.recipeIngredients.setEnabled(enabled);
        baseBinding.recipeDescription.setEnabled(enabled);
        baseBinding.imageView2.setEnabled(enabled);
    }

    public void setAddImgBtInvisible() {
        baseBinding.recipeAddPicBt.setVisibility(View.INVISIBLE);
    }

    abstract void onClickAction();
    abstract void setRecipeViewField();

}