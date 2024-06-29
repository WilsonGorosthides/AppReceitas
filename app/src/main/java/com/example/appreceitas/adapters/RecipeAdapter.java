package com.example.appreceitas.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private OnDeleteClickListener deleteListener;
    private boolean isLoggedIn; // Variável para verificar se o usuário está logado

    public interface OnItemClickListener {
        void onItemClick(Receita receita);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Receita receita);
    }

    public RecipeAdapter(List<Receita> receitas, OnItemClickListener listener, OnDeleteClickListener deleteListener, boolean isLoggedIn) {
        this.receitas = receitas;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.isLoggedIn = isLoggedIn;
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
        holder.bind(receita, listener, deleteListener, isLoggedIn);
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView recipeImage;
        private ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        void bind(final Receita receita, final OnItemClickListener listener, final OnDeleteClickListener deleteListener, boolean isLoggedIn) {
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

            if (isLoggedIn) {
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteListener.onDeleteClick(receita);
                    }
                });
            } else {
                deleteButton.setVisibility(View.GONE);
            }
        }
    }
}
