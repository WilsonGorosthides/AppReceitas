package com.example.appreceitas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email é obrigatório");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Senha é obrigatória");
            return;
        }

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        Usuario usuario = db.usuarioDao().login(email, password);

        if (usuario != null) {
            Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("isLoggedIn", true);
            startActivity(intent);
            finish();  // Fechar LoginActivity
        } else {
            Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }
}
