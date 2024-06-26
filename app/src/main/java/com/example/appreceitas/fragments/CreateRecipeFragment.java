package com.example.appreceitas.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appreceitas.R;

public class CreateRecipeFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextIngredients;
    private EditText editTextSteps;
    private Spinner spinnerRecipeType;
    private EditText editTextCalories;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextIngredients = view.findViewById(R.id.editTextIngredients);
        editTextSteps = view.findViewById(R.id.editTextSteps);
        spinnerRecipeType = view.findViewById(R.id.spinnerRecipeType);
        editTextCalories = view.findViewById(R.id.editTextCalories);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipe();
            }
        });

        return view;
    }

    private void submitRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String steps = editTextSteps.getText().toString().trim();
        String recipeType = spinnerRecipeType.getSelectedItem().toString();
        String calories = editTextCalories.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getContext(), "Por favor, insira o título", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ingredients)) {
            Toast.makeText(getContext(), "Por favor, insira os ingredientes", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(steps)) {
            Toast.makeText(getContext(), "Por favor, insira o passo a passo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aqui você pode adicionar a lógica para salvar a receita no banco de dados
        // e fazer o upload da foto, se necessário.

        Toast.makeText(getContext(), "Receita cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        // Limpar os campos após o cadastro
        clearFields();
    }

    private void clearFields() {
        editTextTitle.setText("");
        editTextIngredients.setText("");
        editTextSteps.setText("");
        editTextCalories.setText("");
        spinnerRecipeType.setSelection(0);
    }
}
