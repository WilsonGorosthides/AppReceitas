package com.example.appreceitas.activities;

import com.example.appreceitas.R;

public class RecipesListBebidasActivity extends RecipesListActivity {

    @Override
    protected int getLayoutId() {

        return R.layout.activity_recipes_list_bebidas;
    }

    @Override
    protected String getRecipeType() {
        return "Bebida";
    }
}
