package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreceitas.R;
import com.example.appreceitas.adapters.RecipeAdapter;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Receita;
import com.example.appreceitas.fragments.RecipeDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public abstract class RecipesListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener, RecipeAdapter.OnDeleteClickListener {
    private static final String TAG = "RecipesListActivity";

    protected RecyclerView recyclerView;
    protected RecipeAdapter recipeAdapter;
    private boolean isLoggedIn; // Variável para verificar se o usuário está logado

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Verifique se o usuário está logado
        isLoggedIn = checkIfUserIsLoggedIn();

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Receita> receitas = db.receitaDao().getRecipesByType(getRecipeType());
        recipeAdapter = new RecipeAdapter(receitas, this, this, isLoggedIn);
        recyclerView.setAdapter(recipeAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    // Método para verificar se o usuário está logado
    private boolean checkIfUserIsLoggedIn() {
        // Implementar a lógica para verificar se o usuário está logado
        // Isso pode incluir verificar um token de autenticação, sessão, etc.
        return true; // Retornar verdadeiro ou falso com base no status de login do usuário
    }

    @Override
    public void onItemClick(Receita receita) {
        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(receita);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        // Tornar o FrameLayout visível
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
    }

    @Override
    public void onDeleteClick(Receita receita) {
        if (isLoggedIn) {
            AppDatabase db = AppDatabase.getDatabase(this);
            db.receitaDao().delete(receita);

            // Atualizar a lista de receitas
            List<Receita> receitas = db.receitaDao().getRecipesByType(getRecipeType());
            recipeAdapter = new RecipeAdapter(receitas, this, this, isLoggedIn);
            recyclerView.setAdapter(recipeAdapter);

            Toast.makeText(this, "Receita excluída", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Você precisa estar logado para excluir receitas.", Toast.LENGTH_SHORT).show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_create_recipe) {
                        startActivity(new Intent(RecipesListActivity.this, CreateRecipeActivity.class));
                    } else if (itemId == R.id.nav_profile) {
                        startActivity(new Intent(RecipesListActivity.this, ProfileActivity.class));
                    } else if (itemId == R.id.nav_home) {
                        startActivity(new Intent(RecipesListActivity.this, HomeActivity.class));
                    } else if (itemId == R.id.nav_sair_main) {
                        startActivity(new Intent(RecipesListActivity.this, MainActivity.class));
                    }

                    return true;
                }
            };

    protected abstract int getLayoutId();

    protected abstract String getRecipeType();
}
