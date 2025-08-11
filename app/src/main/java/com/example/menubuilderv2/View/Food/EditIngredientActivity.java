package com.example.menubuilderv2.View.Food;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import android.util.Log;  // Import Log class

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;

public class EditIngredientActivity extends AppCompatActivity {

    private EditText edtName, edtCategory, edtDesc, edtImageLink;
    private ImageView imgIngredient;
    private Button btnSave, btnCheckImage;
    private Ingredient ingredient;
    private IngredientViewModel viewModel;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);

        // Init Views
        edtName = findViewById(R.id.edtName);
        edtCategory = findViewById(R.id.edtCategory);
        edtDesc = findViewById(R.id.edtDesc);
        edtImageLink = findViewById(R.id.edtImageLink);
        imgIngredient = findViewById(R.id.imgIngredient);
        btnSave = findViewById(R.id.btnSaveEditFood);
        btnCheckImage = findViewById(R.id.btnCheckImage);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        //check anh
        btnCheckImage.setOnClickListener(v -> {
            String url = edtImageLink.getText().toString().trim();

            // Kiểm tra nếu URL không trống
            if (!url.isEmpty()) {
                Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.ic_lunch) // Placeholder trong khi tải ảnh
                        .error(R.drawable.ic_error) // Ảnh hiển thị khi tải ảnh thất bại
                        .into(imgIngredient); // Tải ảnh vào ImageView
            } else {
                Toast.makeText(this, "Please enter a valid image URL", Toast.LENGTH_SHORT).show(); // Nếu URL trống
            }
        });


        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        // Get the ingredient object passed from the previous activity
        ingredient = (Ingredient) getIntent().getSerializableExtra("ingredient");

        if (ingredient != null) {
            populateFields(ingredient);
        } else {
            Log.d("EditIngredientActivity", "Ingredient is null");
        }

        // Button Save
        btnSave.setOnClickListener(v -> saveUpdatedIngredient());
    }


    private void populateFields(Ingredient ingredient) {
        edtName.setText(ingredient.getName());
        edtCategory.setText(ingredient.getCategory());
        edtDesc.setText(ingredient.getDesc());
        edtImageLink.setText(ingredient.getImage());

        // Load image using Glide
        Glide.with(this)
                .load(ingredient.getImage())
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
        ingredient.setName(name);
        ingredient.setCategory(category);
        ingredient.setDesc(desc);
        ingredient.setImage(imageUrl);

        // Save updated ingredient
        viewModel.updateIngredient(ingredient, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Ingredient updated successfully!", Toast.LENGTH_SHORT).show();
                Log.d("EditIngredientActivity", "Ingredient updated successfully");
                finish(); // Close activity
            } else {
                Toast.makeText(this, "Failed to update ingredient.", Toast.LENGTH_SHORT).show();
                Log.d("EditIngredientActivity", "Failed to update ingredient");
            }
        });
    }
}
