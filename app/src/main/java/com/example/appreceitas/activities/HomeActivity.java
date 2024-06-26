package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appreceitas.R;
import com.example.appreceitas.fragments.CreateRecipeFragment;
import com.example.appreceitas.fragments.ProfileFragment;
import com.example.appreceitas.fragments.RecipesListFragmentEntrada;
import com.example.appreceitas.fragments.RecipesListFragmentPrincipal;
import com.example.appreceitas.fragments.RecipesListFragmentSobremesa;
import com.example.appreceitas.fragments.RecipesListFragmentBebidas;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipesListFragmentEntrada()).commit();
        }

        // Set click listeners for icons
        findViewById(R.id.icon_entradas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RecipesListFragmentEntrada());
            }
        });

        findViewById(R.id.icon_principal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RecipesListFragmentPrincipal());
            }
        });

        findViewById(R.id.icon_sobremesas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RecipesListFragmentSobremesa());
            }
        });

        findViewById(R.id.icon_bebidas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RecipesListFragmentBebidas());
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_create_recipe) {
                        if (isLoggedIn) {
                            selectedFragment = new CreateRecipeFragment();
                        } else {
                            showLoginPrompt();
                            return false;
                        }
                    } else if (itemId == R.id.nav_profile) {
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

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void showLoginPrompt() {
        Toast.makeText(this, "Por favor, faça login para acessar esta funcionalidade.", Toast.LENGTH_SHORT).show();
    }
}
