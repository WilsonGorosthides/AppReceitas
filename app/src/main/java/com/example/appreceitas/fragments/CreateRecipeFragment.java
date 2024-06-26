package com.example.appreceitas.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Receita;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class CreateRecipeFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTitle, editIngredients, editSteps, editType, editCalories;
    private ImageView imagePhoto;
    private Button buttonSelectPhoto, buttonSaveRecipe;

    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        editTitle = view.findViewById(R.id.edit_recipe_title);
        editIngredients = view.findViewById(R.id.edit_recipe_ingredients);
        editSteps = view.findViewById(R.id.edit_recipe_steps);
        editType = view.findViewById(R.id.edit_recipe_type);
        editCalories = view.findViewById(R.id.edit_recipe_calories);
        imagePhoto = view.findViewById(R.id.image_recipe_photo);
        buttonSelectPhoto = view.findViewById(R.id.button_select_photo);
        buttonSaveRecipe = view.findViewById(R.id.button_save_recipe);

        buttonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imagePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveRecipe() {
        String title = editTitle.getText().toString();
        String ingredients = editIngredients.getText().toString();
        String steps = editSteps.getText().toString();
        String type = editType.getText().toString();
        String caloriesStr = editCalories.getText().toString();
        Integer calories = caloriesStr.isEmpty() ? null : Integer.parseInt(caloriesStr);
        String photoPath = imageUri != null ? imageUri.toString() : null;

        if (title.isEmpty() || ingredients.isEmpty() || steps.isEmpty() || type.isEmpty()) {
            Toast.makeText(getActivity(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        Receita receita = new Receita(title, ingredients, steps, type, photoPath, calories);
        AppDatabase db = AppDatabase.getDatabase(getContext());
        db.receitaDao().insert(receita);

        Toast.makeText(getActivity(), "Receita salva com sucesso", Toast.LENGTH_SHORT).show();

        // Limpar os campos
        editTitle.setText("");
        editIngredients.setText("");
        editSteps.setText("");
        editType.setText("");
        editCalories.setText("");
        imagePhoto.setImageBitmap(null);
        imageUri = null;
    }
}
