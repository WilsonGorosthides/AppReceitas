package com.example.appreceitas.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreceitas.R;
import com.example.appreceitas.entities.Receita;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Receita> receitas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Receita receita);
    }

    public RecipeAdapter(List<Receita> receitas, OnItemClickListener listener) {
        this.receitas = receitas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receita receita = receitas.get(position);
        holder.bind(receita, listener);
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView recipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            recipeImage = itemView.findViewById(R.id.recipe_image);
        }

        void bind(final Receita receita, final OnItemClickListener listener) {
            title.setText(receita.getTitle());

            // Carregar a imagem da receita se disponível
            if (receita.getPhoto() != null && !receita.getPhoto().isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(receita.getPhoto());
                recipeImage.setImageBitmap(bitmap);
            } else {
                recipeImage.setImageResource(R.drawable.logoapp); // Logo do app como imagem padrão
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(receita);
                }
            });
        }
    }
}
