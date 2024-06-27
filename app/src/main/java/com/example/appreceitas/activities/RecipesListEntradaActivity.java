package com.example.appreceitas.activities;

import com.example.appreceitas.R;

public class RecipesListEntradaActivity extends RecipesListActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipes_list_entrada;
    }

    @Override
    protected String getRecipeType() {
        return "Entrada";
    }
}
