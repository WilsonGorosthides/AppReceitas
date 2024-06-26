package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appreceitas.R;
import com.example.appreceitas.fragments.CreateRecipeFragment;
import com.example.appreceitas.fragments.ProfileFragment;
import com.example.appreceitas.fragments.RecipesListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check if the user is logged in
        Intent intent = getIntent();
        isLoggedIn = intent.getBooleanExtra("isLoggedIn", false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipesListFragment("entradas")).commit();
        }

        ImageView iconEntradas = findViewById(R.id.icon_entradas);
        ImageView iconPrincipal = findViewById(R.id.icon_principal);
        ImageView iconSobremesas = findViewById(R.id.icon_sobremesas);
        ImageView iconBebidas = findViewById(R.id.icon_bebidas);

        iconEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipesListFragment("entradas")).commit();
            }
        });

        iconPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipesListFragment("principal")).commit();
            }
        });

        iconSobremesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipesListFragment("sobremesas")).commit();
            }
        });

        iconBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipesListFragment("bebidas")).commit();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.nav_create_recipe) {
                        if (isLoggedIn) {
                            selectedFragment = new CreateRecipeFragment();
                        } else {
                            showLoginPrompt();
                            return false;
                        }
                    } else if (item.getItemId() == R.id.nav_profile) {
                        if (isLoggedIn) {
                            selectedFragment = new ProfileFragment();
                        } else {
                            showLoginPrompt();
                            return false;
                        }
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            };

    private void showLoginPrompt() {
        Toast.makeText(this, "Por favor, faça login para acessar esta funcionalidade.", Toast.LENGTH_SHORT).show();
    }
}
