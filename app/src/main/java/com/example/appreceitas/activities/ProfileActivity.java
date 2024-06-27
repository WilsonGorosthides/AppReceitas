package com.example.appreceitas.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Usuario;
import com.example.appreceitas.fragments.UserInfoFragment;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewNome;
    private TextView textViewEmail;
    private Button buttonEditUser;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewNome = findViewById(R.id.textViewNome);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonEditUser = findViewById(R.id.buttonEditUser);

        // Obter os detalhes do usuário logado
        userId = getIntent().getIntExtra("userId", -1);
        if (userId != -1) {
            loadUserProfile(userId);
        } else {
            Toast.makeText(this, "Erro ao carregar o perfil do usuário.", Toast.LENGTH_SHORT).show();
        }

        // Configurar listener para o botão de editar usuário
        buttonEditUser.setOnClickListener(v -> openUserInfoFragment());
    }

    private void loadUserProfile(int userId) {
        AppDatabase db = AppDatabase.getDatabase(this);
        Usuario usuario = db.usuarioDao().getUsuarioById(userId);

        if (usuario != null) {
            textViewNome.setText(usuario.getNome());
            textViewEmail.setText(usuario.getEmail());
        } else {
            Toast.makeText(this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openUserInfoFragment() {
        UserInfoFragment fragment = UserInfoFragment.newInstance(userId);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
