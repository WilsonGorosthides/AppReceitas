package com.example.appreceitas.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreceitas.R;
import com.example.appreceitas.adapters.RecipeAdapter;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Receita;
import com.example.appreceitas.fragments.RecipeDetailFragment;

import java.util.List;

public abstract class RecipesListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {
    private static final String TAG = "RecipesListActivity";

    protected RecyclerView recyclerView;
    protected RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Receita> receitas = db.receitaDao().getRecipesByType(getRecipeType());
        recipeAdapter = new RecipeAdapter(receitas, this);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onItemClick(Receita receita) {
        Log.d(TAG, "Receita clicada: " + receita.getTitle());
        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(receita);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        // Tornar o FrameLayout visível
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
    }

    protected abstract int getLayoutId();

    protected abstract String getRecipeType();
}
