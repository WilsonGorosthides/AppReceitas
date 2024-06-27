package com.example.appreceitas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appreceitas.entities.Receita;

import java.util.List;

@Dao
public interface ReceitaDao {

    // Inserir uma nova receita
    @Insert
    void insert(Receita receita);

    // Atualizar uma receita existente
    @Update
    void update(Receita receita);

    // Deletar uma receita
    @Delete
    void delete(Receita receita);

    // Deletar todas as receitas
    @Query("DELETE FROM receitas")
    void deleteAllReceitas();
    // Consultar receitas por tipo
    @Query("SELECT * FROM receitas WHERE tipo = :type")
    List<Receita> getRecipesByType(String type);
    // Consultar todas as receitas
    @Query("SELECT * FROM receitas")
    List<Receita> getAllReceitas();

    // Consultar receitas por título
    @Query("SELECT * FROM receitas WHERE titulo LIKE :titulo")
    List<Receita> getReceitasByTitle(String titulo);
}
