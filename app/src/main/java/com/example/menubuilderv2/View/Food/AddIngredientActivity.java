package com.example.menubuilderv2.View.Food;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;

import java.util.UUID;

public class AddIngredientActivity extends AppCompatActivity {

    EditText edtName, edtCategory, edtDesc;
    ImageView imgIngredient;
    Button btnCheck, btnSave;
    EditText edtImageLink;

    IngredientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredient);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Init views
        edtName = findViewById(R.id.edtName);
        edtCategory = findViewById(R.id.edtCategory);
        edtDesc = findViewById(R.id.edtDesc);
        imgIngredient = findViewById(R.id.imgIngredient);
        btnSave = findViewById(R.id.btnSave);
        edtImageLink = findViewById(R.id.edtImageLink);
        btnCheck = findViewById(R.id.btnCheck);


        // Init ViewModel
        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        btnCheck.setOnClickListener(v -> {
            String url = edtImageLink.getText().toString().trim();
            if (!url.isEmpty()) {
                Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.ic_lunch)
                        .error(R.drawable.ic_error) // tuỳ bạn, ảnh nếu link lỗi
                        .into(imgIngredient);
            } else {
                Toast.makeText(this, "Please enter a valid image URL", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = edtName.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        String imageUrl = edtImageLink.getText().toString().trim();

        if (name.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Name and category are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = UUID.randomUUID().toString(); // ✅ Tạo ID trước
        Ingredient ingredient = new Ingredient(id, name, category, imageUrl, desc); // ✅ Dùng đúng id

        viewModel.saveIngredient(ingredient, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("Save nguyen lieu thanh cong");
                finish();
            } else {
                System.out.println("Save nguyen lieu that bai");
                Toast.makeText(this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadIngredients(); // gọi lại khi quay lại
    }

}
