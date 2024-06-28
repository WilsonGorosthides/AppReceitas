package com.example.appreceitas.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appreceitas.R;
import com.example.appreceitas.entities.Receita;

public class RecipeDetailFragment extends Fragment {
    private static final String TAG = "RecipeDetailFragment";

    private TextView title, ingredients, steps, calories;

    public static RecipeDetailFragment newInstance(Receita receita) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("receita", receita);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        title = view.findViewById(R.id.recipe_title);
        ingredients = view.findViewById(R.id.recipe_ingredients);
        steps = view.findViewById(R.id.recipe_steps);
        calories = view.findViewById(R.id.recipe_calories);

        if (getArguments() != null) {
            Receita receita = getArguments().getParcelable("receita");
            if (receita != null) {
                Log.d(TAG, "Receita recebida: " + receita.getTitle());
                title.setText(receita.getTitle());
                ingredients.setText(receita.getIngredients());
                steps.setText(receita.getSteps());
                if (receita.getCalories() != null) {
                    calories.setText(String.valueOf(receita.getCalories()));
                } else {
                    calories.setText("N/A");
                }
            } else {
                Log.e(TAG, "Receita é nula");
            }
        } else {
            Log.e(TAG, "Argumentos são nulos");
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Configurar animação de saída
        Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        getView().startAnimation(slideOut);
    }
}
