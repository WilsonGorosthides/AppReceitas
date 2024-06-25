package com.example.appreceitas.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.appreceitas.entities.Usuario;
import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario getUsuarioById(int id);

    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsuarios();

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha")
    Usuario login(String email, String senha);
}
