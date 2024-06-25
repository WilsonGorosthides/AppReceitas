package com.example.appreceitas.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appreceitas.dao.ReceitaDao;
import com.example.appreceitas.dao.UsuarioDao;
import com.example.appreceitas.entities.Receita;
import com.example.appreceitas.entities.Usuario;

@Database(entities = {Usuario.class, Receita.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract ReceitaDao receitaDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppReceitas")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
