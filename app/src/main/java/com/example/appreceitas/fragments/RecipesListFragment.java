package com.example.appreceitas.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appreceitas.R;
import com.example.appreceitas.adapters.ReceitaAdapter;
import com.example.appreceitas.entities.Receita;

import java.util.ArrayList;
import java.util.List;

public class RecipesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReceitaAdapter adapter;
    private List<Receita> recipeList;
    private String recipeType;

    public RecipesListFragment(String recipeType) {
        this.recipeType = recipeType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for demonstration
        recipeList = new ArrayList<>();
        recipeList.add(new Receita("Receita 1", "Ingredientes 1", "Passos 1", recipeType, null, null));
        recipeList.add(new Receita("Receita 2", "Ingredientes 2", "Passos 2", recipeType, null, null));

        adapter = new ReceitaAdapter(recipeList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
