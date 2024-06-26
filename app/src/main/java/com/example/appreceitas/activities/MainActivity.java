package com.example.appreceitas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btn_register, btn_login, btn_offline;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegisterUserActivity();
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLoginUserActivity();
            }
        });

        btn_offline = findViewById(R.id.btn_offline);
        btn_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirHomeActivityOffline();
            }
        });
    }

    private void abrirHomeActivityOffline() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("isLoggedIn", false);
        startActivity(intent);
    }

    private void abrirLoginUserActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void abrirRegisterUserActivity() {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }
}
