package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Receita;

public class CreateRecipeActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextIngredients;
    private EditText editTextSteps;
    private Spinner spinnerRecipeType;
    private EditText editTextCalories;
    private Button buttonSubmit;
    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextSteps = findViewById(R.id.editTextSteps);
        spinnerRecipeType = findViewById(R.id.spinnerRecipeType);
        editTextCalories = findViewById(R.id.editTextCalories);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Configurar o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecipeType.setAdapter(adapter);

        // Inicializar o banco de dados
        database = AppDatabase.getDatabase(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipe();
            }
        });
    }

    private void submitRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String steps = editTextSteps.getText().toString().trim();
        String recipeType = spinnerRecipeType.getSelectedItem().toString();
        String caloriesText = editTextCalories.getText().toString().trim();
        Integer calories = TextUtils.isEmpty(caloriesText) ? null : Integer.valueOf(caloriesText);

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Por favor, insira o título", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ingredients)) {
            Toast.makeText(this, "Por favor, insira os ingredientes", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(steps)) {
            Toast.makeText(this, "Por favor, insira o passo a passo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recipeType.equals("Tipo de Receita")) {
            Toast.makeText(this, "Por favor, selecione um tipo de receita", Toast.LENGTH_SHORT).show();
            return;
        }

        // Salvar a receita no banco de dados
        Receita receita = new Receita(title, ingredients, steps, recipeType, null, calories);
        database.receitaDao().insert(receita);

        Toast.makeText(this, "Receita cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

        // Redirecionar para a Home após cadastrar a receita
        Intent intent = new Intent(CreateRecipeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Terminar a atividade atual para não retornar a ela com o botão de voltar
    }
}
