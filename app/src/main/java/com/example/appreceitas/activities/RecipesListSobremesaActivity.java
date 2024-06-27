package com.example.appreceitas.activities;

import com.example.appreceitas.R;

public class RecipesListSobremesaActivity extends RecipesListActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipes_list_sobremesa;
    }

    @Override
    protected String getRecipeType() {
        return "Sobremesa";
    }
}
