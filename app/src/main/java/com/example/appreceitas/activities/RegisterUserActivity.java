package com.example.appreceitas.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Usuario;

import java.util.regex.Pattern;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        db = AppDatabase.getDatabase(this);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o nome", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Por favor, insira um email válido (formato: nome@domínio.com)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 4) {
            Toast.makeText(this, "A senha deve ter no mínimo 4 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario(name, email, password);
        db.usuarioDao().insert(usuario);
        Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();

        // Redireciona para a MainActivity após o registro
        Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isValidEmail(String email) {
        // Regex para validar o formato do email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(emailPattern, email);
    }

}
