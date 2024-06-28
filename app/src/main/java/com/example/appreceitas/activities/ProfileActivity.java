package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Usuario;
import com.example.appreceitas.fragments.UserInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private TextView textViewNome;
    private TextView textViewEmail;
    private Button buttonEditUser;
    private int userId;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewNome = findViewById(R.id.textViewNome);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonEditUser = findViewById(R.id.buttonEditUser);

        // Obter os detalhes do usuário logado
        userId = getIntent().getIntExtra("userId", -1);
        isLoggedIn = getIntent().getBooleanExtra("isLoggedIn", false);
        Log.d(TAG, "Received userId: " + userId);
        if (userId != -1) {
            loadUserProfile(userId);
        } else {
            Toast.makeText(this, "Erro ao carregar o perfil do usuário.", Toast.LENGTH_SHORT).show();
        }

        // Configurar listener para o botão de editar usuário
        buttonEditUser.setOnClickListener(v -> openUserInfoFragment());

        // Configurar BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void loadUserProfile(int userId) {
        AppDatabase db = AppDatabase.getDatabase(this);
        Usuario usuario = db.usuarioDao().getUsuarioById(userId);

        if (usuario != null) {
            Log.d(TAG, "User found: " + usuario.getNome());
            textViewNome.setText(usuario.getNome());
            textViewEmail.setText(usuario.getEmail());
        } else {
            Log.e(TAG, "No user found with userId: " + userId);
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_create_recipe) {
                        if (isLoggedIn) {
                            Intent intent = new Intent(ProfileActivity.this, CreateRecipeActivity.class);
                            startActivity(intent);
                        } else {
                            showLoginPrompt();
                            return false;
                        }
                    } else if (itemId == R.id.nav_profile) {
                        // Refresh the ProfileActivity
                        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                    } else if (itemId == R.id.nav_home) {
                        // Navigate to HomeActivity
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        intent.putExtra("isLoggedIn", isLoggedIn);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    } else if (itemId == R.id.nav_sair_main) {
                        // Navigate to MainActivity
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    }

                    return true;
                }
            };

    private void showLoginPrompt() {
        Toast.makeText(this, "Por favor, faça login para acessar esta funcionalidade.", Toast.LENGTH_SHORT).show();
    }
}
