package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreceitas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private boolean isLoggedIn;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check if the user is logged in
        Intent intent = getIntent();
        isLoggedIn = intent.getBooleanExtra("isLoggedIn", false);
        userId = intent.getIntExtra("userId", -1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Set click listeners for icons
        findViewById(R.id.icon_entradas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RecipesListEntradaActivity.class));
            }
        });

        findViewById(R.id.icon_principal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RecipesListPrincipalActivity.class));
            }
        });

        findViewById(R.id.icon_sobremesas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RecipesListSobremesaActivity.class));
            }
        });

        findViewById(R.id.icon_bebidas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RecipesListBebidasActivity.class));
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_create_recipe) {
                        if (isLoggedIn) {
                            Intent intent = new Intent(HomeActivity.this, CreateRecipeActivity.class);
                            startActivity(intent);
                        } else {
                            showLoginPrompt();
                            return false;
                        }
                    } else if (itemId == R.id.nav_profile) {
                        if (isLoggedIn) {
                            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        } else {
                            showLoginPrompt();
                            return false;
                        }
                    }

                    return true;
                }
            };

    private void showLoginPrompt() {
        Toast.makeText(this, "Por favor, faça login para acessar esta funcionalidade.", Toast.LENGTH_SHORT).show();
    }
}
