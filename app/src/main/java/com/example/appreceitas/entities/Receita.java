package com.example.appreceitas.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "receitas")
public class Receita {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "titulo")
    private String title;

    @NonNull
    @ColumnInfo(name = "ingredientes")
    private String ingredients;

    @NonNull
    @ColumnInfo(name = "passos")
    private String steps;

    // Construtor padrão
    public Receita() {}

    // Construtor completo
    public Receita(@NonNull String title, @NonNull String ingredients, @NonNull String steps) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NonNull String ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    public String getSteps() {
        return steps;
    }

    public void setSteps(@NonNull String steps) {
        this.steps = steps;
    }
}
