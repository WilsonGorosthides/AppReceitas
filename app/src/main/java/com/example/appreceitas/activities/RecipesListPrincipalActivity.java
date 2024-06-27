package com.example.appreceitas.activities;

import com.example.appreceitas.R;

public class RecipesListPrincipalActivity extends RecipesListActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipes_list_principal;
    }

    @Override
    protected String getRecipeType() {
        return "Prato Principal";
    }
}
