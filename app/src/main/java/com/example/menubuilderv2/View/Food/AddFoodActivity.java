package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.IngredientAdapterSelect;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.FoodViewModel;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientAdapterSelect adapter;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private List<Ingredient> originalIngredientList = new ArrayList<>();
    private IngredientViewModel ingredientViewModel;
    private EditText edtSearchIngredient, edtCheckImage, edtFoodName, edtFoodDesc, edtFoodCategory, edtFoodGuide;
    private TextView txtSelectedIngredientsSummary;
    private Map<String, UsedIngredients> selectedMap = new HashMap<>();
    private Button btnManageIngredients, btnCheckImage, btnSave;
    private ImageButton btnBack;
    private ImageView imgSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_food);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerIngredientSelect);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        edtSearchIngredient = findViewById(R.id.edtSearchIngredient);
        txtSelectedIngredientsSummary = findViewById(R.id.txtSelectedIngredientsSummary);
        btnManageIngredients = findViewById(R.id.btnManageIngredients);
        edtFoodName = findViewById(R.id.edtFoodName);
        edtFoodDesc = findViewById(R.id.edtFoodDesc);
        edtFoodCategory = findViewById(R.id.edtFoodCategory);
        edtFoodGuide = findViewById(R.id.edtFoodGuide);
        btnSave = findViewById(R.id.btnSaveNewFood);

        imgSelected = findViewById(R.id.imgSelected);
        edtCheckImage = findViewById(R.id.edtCheckImage);
        btnCheckImage = findViewById(R.id.btnCheckImage);
        btnCheckImage.setOnClickListener(v -> {
            String imageUrl = edtCheckImage.getText().toString().trim();
            if (!imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_lunch)
                        .error(R.drawable.ic_error) // bạn có thể thêm ảnh lỗi riêng nếu muốn
                        .into(imgSelected);
            } else {
                Toast.makeText(this, "Vui lòng nhập link ảnh", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack = findViewById(R.id.btnBackAddItem);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new IngredientAdapterSelect(
                this,
                ingredientList,
                (ingredient, quantity) -> {
                    String id = ingredient.getId();
                    if (quantity.isEmpty()) {
                        selectedMap.remove(id);
                    } else {
                        UsedIngredients used = new UsedIngredients();
                        used.setIngredient(ingredient);
                        used.setQuantity(quantity);
                        selectedMap.put(id, used);
                    }
                    updateSelectedIngredientsSummary();
                },
                selectedMap
        );

        recyclerView.setAdapter(adapter);

        btnManageIngredients.setOnClickListener(v -> {
            Intent intent = new Intent(AddFoodActivity.this, ManageIngredientActivity.class);
            startActivity(intent);
        });

        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        observeIngredientData();

        setupSearchListener();

        btnSave.setOnClickListener(v -> {
            String name = edtFoodName.getText().toString().trim();
            String desc = edtFoodDesc.getText().toString().trim();
            String category = edtFoodCategory.getText().toString().trim();
            String guide = edtFoodGuide.getText().toString().trim();
            String imageUrl = edtCheckImage.getText().toString().trim();

            List<UsedIngredients> listUsedIngredients = new ArrayList<>(selectedMap.values());

            if (name.isEmpty() || listUsedIngredients.isEmpty()) {
                Toast.makeText(this, "Tên món ăn và nguyên liệu là bắt buộc!", Toast.LENGTH_SHORT).show();
                return;
            }

            Food food = new Food();
            food.setName(name);
            food.setDesc(desc);
            food.setCategory(category);
            food.setGuide(guide);
            food.setImage(imageUrl);
            food.setListUsedIngredients(listUsedIngredients);

            FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
            foodViewModel.saveFoodWithIngredients(
                    food,
                    listUsedIngredients,
                    () -> {
                        Toast.makeText(this, "Add successful!", Toast.LENGTH_SHORT).show();
                        Log.d("CreatedFood", "Food saved into Firestore: " + food.toString());
                        finish();
                    },
                    () -> {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                        Log.e("CreatedFood", "Fail.");
                    }
            );
        });

    }

    private void observeIngredientData() {
        ingredientViewModel.getIngredients().observe(this, ingredients -> {
            if (ingredients != null && !ingredients.isEmpty()) {
                originalIngredientList.clear();
                originalIngredientList.addAll(ingredients);

                ingredientList.clear();
                ingredientList.addAll(ingredients);

                adapter.updateList(ingredientList);
            } else {
                Toast.makeText(this, "No ingredients found.", Toast.LENGTH_SHORT).show();
            }
        });

        ingredientViewModel.loadIngredients();
    }

    private void setupSearchListener() {
        edtSearchIngredient.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterIngredients(s.toString());
            }
        });
    }

    private void filterIngredients(String query) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : originalIngredientList) {
            if (ingredient.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ingredient);
            }
        }
        adapter.updateList(filteredList);
    }

    private void updateSelectedIngredientsSummary() {
        StringBuilder summary = new StringBuilder("Selected Ingredients:");
        for (UsedIngredients used : selectedMap.values()) {
            summary.append("\n")
                    .append(used.getIngredient().getName())
                    .append(": ")
                    .append(used.getQuantity());
        }
        txtSelectedIngredientsSummary.setText(summary.toString());
    }
}
