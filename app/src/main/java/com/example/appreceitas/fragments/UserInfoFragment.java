package com.example.appreceitas.fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Usuario;

public class UserInfoFragment extends Fragment {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private int userId;

    public static UserInfoFragment newInstance(int userId) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextNome = view.findViewById(R.id.editTextNome);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextSenha = view.findViewById(R.id.editTextSenha);

        view.findViewById(R.id.buttonSave).setOnClickListener(v -> saveUserInfo());
        view.findViewById(R.id.buttonDeleteAccount).setOnClickListener(v -> confirmDeleteAccount());

        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);
            if (userId != -1) {
                loadUserDetails(userId);
            }
        }
    }

    private void loadUserDetails(int userId) {
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        Usuario usuario = db.usuarioDao().getUsuarioById(userId);

        if (usuario != null) {
            editTextNome.setText(usuario.getNome());
            editTextEmail.setText(usuario.getEmail());
            editTextSenha.setText(usuario.getSenha());
        } else {
            Toast.makeText(getActivity(), "Erro ao carregar os dados do usuário.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserInfo() {
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(getActivity(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = AppDatabase.getDatabase(getActivity());
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        AsyncTask.execute(() -> {
            db.usuarioDao().update(usuario);
            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Informações atualizadas com sucesso.", Toast.LENGTH_SHORT).show());
        });
    }

    private void confirmDeleteAccount() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Excluir Conta")
                .setMessage("Você tem certeza que deseja excluir sua conta? Esta ação não pode ser desfeita.")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void deleteAccount() {
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        Usuario usuario = db.usuarioDao().getUsuarioById(userId);

        if (usuario != null) {
            AsyncTask.execute(() -> {
                db.usuarioDao().delete(usuario);
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getActivity(), "Conta excluída com sucesso.", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                });
            });
        } else {
            Toast.makeText(getActivity(), "Erro ao excluir a conta.", Toast.LENGTH_SHORT).show();
        }
    }
}
