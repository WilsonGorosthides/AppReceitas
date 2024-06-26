package com.example.appreceitas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreceitas.R;
import com.example.appreceitas.entities.Receita;

import java.util.List;

public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ReceitaViewHolder> {

    private List<Receita> receitas;

    public ReceitaAdapter(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @NonNull
    @Override
    public ReceitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new ReceitaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceitaViewHolder holder, int position) {
        Receita receita = receitas.get(position);
        holder.nome.setText(receita.getTitle());
        holder.ingredientes.setText(receita.getIngredients());
        holder.passos.setText(receita.getSteps());
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

    static class ReceitaViewHolder extends RecyclerView.ViewHolder {
        TextView nome, ingredientes, passos;

        ReceitaViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.recipe_title);
            ingredientes = itemView.findViewById(R.id.recipe_ingredients);
            passos = itemView.findViewById(R.id.recipe_steps);
        }
    }
}
