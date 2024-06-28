package com.example.appreceitas.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appreceitas.R;
import com.example.appreceitas.database.AppDatabase;
import com.example.appreceitas.entities.Receita;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateRecipeActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private EditText editTextTitle;
    private EditText editTextIngredients;
    private EditText editTextSteps;
    private Spinner spinnerRecipeType;
    private EditText editTextCalories;
    private Button buttonSubmit;
    private Button buttonAddPhoto;
    private ImageView imageViewPhoto;
    private AppDatabase database;
    private Bitmap selectedImageBitmap;
    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextSteps = findViewById(R.id.editTextSteps);
        spinnerRecipeType = findViewById(R.id.spinnerRecipeType);
        editTextCalories = findViewById(R.id.editTextCalories);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);

        // Configurar o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecipeType.setAdapter(adapter);

        // Inicializar o banco de dados
        database = AppDatabase.getDatabase(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipe();
            }
        });

        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        // Verificar permissões
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            openImageIntent();
        }
    }

    private void openImageIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(pickPhotoIntent, "Escolha uma opção");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePictureIntent});

        startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                selectedImageBitmap = (Bitmap) extras.get("data");
                imageViewPhoto.setImageBitmap(selectedImageBitmap);
                saveImageToInternalStorage(selectedImageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    imageViewPhoto.setImageBitmap(selectedImageBitmap);
                    saveImageToInternalStorage(selectedImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveImageToInternalStorage(Bitmap bitmap) {
        File directory = getDir("imageDir", MODE_PRIVATE);
        File myPath = new File(directory, System.currentTimeMillis() + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(myPath)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            imagePath = myPath.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void submitRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String steps = editTextSteps.getText().toString().trim();
        String recipeType = spinnerRecipeType.getSelectedItem().toString();
        String caloriesText = editTextCalories.getText().toString().trim();
        Integer calories = TextUtils.isEmpty(caloriesText) ? null : Integer.valueOf(caloriesText);

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Por favor, insira o título", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ingredients)) {
            Toast.makeText(this, "Por favor, insira os ingredientes", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(steps)) {
            Toast.makeText(this, "Por favor, insira o passo a passo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recipeType.equals("Tipo de Receita")) {
            Toast.makeText(this, "Por favor, selecione um tipo de receita", Toast.LENGTH_SHORT).show();
            return;
        }

        // Salvar a receita no banco de dados
        Receita receita = new Receita(title, ingredients, steps, recipeType, imagePath, calories);

        database.receitaDao().insert(receita);

        Toast.makeText(this, "Receita cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

        // Redirecionar para a Home após cadastrar a receita
        Intent intent = new Intent(CreateRecipeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Terminar a atividade atual para não retornar a ela com o botão de voltar
    }
}