package com.example.menubuilderv2.View.Food;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;

public class EditIngredientActivity extends AppCompatActivity {

    private EditText edtName, edtCategory, edtDesc, edtImageLink;
    private ImageView imgIngredient;
    private Button btnSave;
    private Ingredient ingredient;
    private IngredientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_ingredient);

        // Init Views
        edtName = findViewById(R.id.edtName);
        edtCategory = findViewById(R.id.edtCategory);
        edtDesc = findViewById(R.id.edtDesc);
        edtImageLink = findViewById(R.id.edtImageLink);
        imgIngredient = findViewById(R.id.imgIngredient);
        btnSave = findViewById(R.id.btnSave);

        // Init ViewModel
        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        // Get ingredient ID from Intent
        String ingredientId = getIntent().getStringExtra("ingredient_id");

        // Fetch ingredient from ViewModel
        viewModel.getIngredients().observe(this, ingredients -> {
            for (Ingredient ing : ingredients) {
                if (ing.id.equals(ingredientId)) {
                    ingredient = ing;
                    break;
                }
            }

            if (ingredient != null) {
                populateFields(ingredient);
            }
        });

        // Button Save
        btnSave.setOnClickListener(v -> saveUpdatedIngredient());

        // Handle Back button click
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void populateFields(Ingredient ingredient) {
        edtName.setText(ingredient.name);
        edtCategory.setText(ingredient.category);
        edtDesc.setText(ingredient.desc);
        edtImageLink.setText(ingredient.image);

        // Load image using Glide
        Glide.with(this)
                .load(ingredient.image)
                .placeholder(R.drawable.ic_lunch)
                .into(imgIngredient);
    }

    private void saveUpdatedIngredient() {
        String name = edtName.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        String imageUrl = edtImageLink.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(category)) {
            Toast.makeText(this, "Name and category are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update ingredient object
        ingredient.name = name;
        ingredient.category = category;
        ingredient.desc = desc;
        ingredient.image = imageUrl;

        // Save updated ingredient
        viewModel.updateIngredient(ingredient, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Ingredient updated successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close activity
            } else {
                Toast.makeText(this, "Failed to update ingredient.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
