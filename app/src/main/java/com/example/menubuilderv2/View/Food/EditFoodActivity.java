package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Adapter.EditFoodIngredientAdapter;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.example.menubuilderv2.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EditFoodActivity extends AppCompatActivity implements EditFoodIngredientAdapter.OnIngredientActionListener {

    private EditText edtFoodName, edtFoodCategory, edtFoodDesc, edtFoodGuide, edtFoodImage;
    private ImageView imgFoodPreview, btnBack;
    private Button btnSave, btnAddIngredient;
    private RecyclerView rvEditIngredients;
    private Food currentFood;
    private FirebaseFirestore firestore;
    private EditFoodIngredientAdapter ingredientAdapter;
    private List<UsedIngredients> currentIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firestore = FirebaseFirestore.getInstance();
        currentIngredients = new ArrayList<>();
        initViews();
        setupRecyclerView(); // Setup RecyclerView trước
        loadFoodData(); // Sau đó mới load data
        setupListeners();
    }

    private void initViews() {
        try {
            edtFoodName = findViewById(R.id.edtFoodName);
            edtFoodCategory = findViewById(R.id.edtFoodCategory);
            edtFoodDesc = findViewById(R.id.edtFoodDesc);
            edtFoodGuide = findViewById(R.id.edtFoodGuide);
            edtFoodImage = findViewById(R.id.edtFoodImage);
            imgFoodPreview = findViewById(R.id.imgFoodPreview);
            btnBack = findViewById(R.id.btnBack);
            btnSave = findViewById(R.id.btnSave);
            btnAddIngredient = findViewById(R.id.btnAddIngredient);
            rvEditIngredients = findViewById(R.id.rvEditIngredients);
            
            // Debug log
            android.util.Log.d("EditFoodActivity", "All views found successfully");
        } catch (Exception e) {
            android.util.Log.e("EditFoodActivity", "Error finding views: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupRecyclerView() {
        try {
            if (rvEditIngredients != null) {
                rvEditIngredients.setLayoutManager(new LinearLayoutManager(this));
                ingredientAdapter = new EditFoodIngredientAdapter(this, currentIngredients, this);
                rvEditIngredients.setAdapter(ingredientAdapter);
                android.util.Log.d("EditFoodActivity", "RecyclerView setup successfully");
            } else {
                android.util.Log.e("EditFoodActivity", "rvEditIngredients is null");
            }
        } catch (Exception e) {
            android.util.Log.e("EditFoodActivity", "Error setting up RecyclerView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadFoodData() {
        try {
            currentFood = (Food) getIntent().getSerializableExtra("food_data");
            android.util.Log.d("EditFoodActivity", "Food data loaded: " + (currentFood != null ? currentFood.getName() : "null"));
            
            if (currentFood != null) {
                if (edtFoodName != null) edtFoodName.setText(currentFood.getName());
                if (edtFoodCategory != null) edtFoodCategory.setText(currentFood.getCategory());
                if (edtFoodDesc != null) edtFoodDesc.setText(currentFood.getDesc());
                if (edtFoodGuide != null) edtFoodGuide.setText(currentFood.getGuide());
                if (edtFoodImage != null) edtFoodImage.setText(currentFood.getImage());

                if (currentFood.getListUsedIngredients() != null) {
                    currentIngredients.clear();
                    currentIngredients.addAll(currentFood.getListUsedIngredients());
                    android.util.Log.d("EditFoodActivity", "Loaded " + currentIngredients.size() + " ingredients");
                    if (ingredientAdapter != null) {
                        ingredientAdapter.notifyDataSetChanged();
                    }
                }

                if (currentFood.getImage() != null && !currentFood.getImage().isEmpty() && imgFoodPreview != null) {
                    Glide.with(this)
                            .load(currentFood.getImage())
                            .placeholder(R.drawable.ic_lunch)
                            .into(imgFoodPreview);
                }
            } else {
                android.util.Log.e("EditFoodActivity", "No food data received");
                Toast.makeText(this, "Không có dữ liệu món ăn", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            android.util.Log.e("EditFoodActivity", "Error loading food data: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupListeners() {
        try {
            if (btnBack != null) {
                btnBack.setOnClickListener(v -> finish());
            }

            if (btnSave != null) {
                btnSave.setOnClickListener(v -> saveFood());
            }

            if (btnAddIngredient != null) {
                btnAddIngredient.setOnClickListener(v -> {
                    // Navigate to ingredient selection activity
                    Intent intent = new Intent(this, AddFoodActivity.class);
                    intent.putExtra("is_editing", true);
                    intent.putExtra("current_ingredients", new ArrayList<>(currentIngredients));
                    startActivityForResult(intent, 1001);
                });
            }

            if (edtFoodImage != null) {
                edtFoodImage.addTextChangedListener(new android.text.TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(android.text.Editable s) {
                        String imageUrl = s.toString().trim();
                        if (!imageUrl.isEmpty() && imgFoodPreview != null) {
                            Glide.with(EditFoodActivity.this)
                                    .load(imageUrl)
                                    .placeholder(R.drawable.ic_lunch)
                                    .into(imgFoodPreview);
                        }
                    }
                });
            }
            
            android.util.Log.d("EditFoodActivity", "Listeners setup successfully");
        } catch (Exception e) {
            android.util.Log.e("EditFoodActivity", "Error setting up listeners: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onEditIngredient(UsedIngredients usedIngredient, int position) {
        // Show dialog to edit quantity
        showEditQuantityDialog(usedIngredient, position);
    }

    @Override
    public void onDeleteIngredient(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có muốn xóa nguyên liệu này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    currentIngredients.remove(position);
                    ingredientAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditQuantityDialog(UsedIngredients usedIngredient, int position) {
        EditText edtQuantity = new EditText(this);
        edtQuantity.setText(usedIngredient.getQuantity());
        edtQuantity.setHint("Nhập số lượng");

        new AlertDialog.Builder(this)
                .setTitle("Chỉnh sửa số lượng")
                .setView(edtQuantity)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newQuantity = edtQuantity.getText().toString().trim();
                    if (!newQuantity.isEmpty()) {
                        usedIngredient.setQuantity(newQuantity);
                        ingredientAdapter.notifyItemChanged(position);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void saveFood() {
        String name = edtFoodName.getText().toString().trim();
        String category = edtFoodCategory.getText().toString().trim();
        String desc = edtFoodDesc.getText().toString().trim();
        String guide = edtFoodGuide.getText().toString().trim();
        String imageUrl = edtFoodImage.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentIngredients.isEmpty()) {
            Toast.makeText(this, "Vui lòng thêm ít nhất một nguyên liệu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentFood != null && currentFood.getId() != null) {
            currentFood.setName(name);
            currentFood.setCategory(category);
            currentFood.setDesc(desc);
            currentFood.setGuide(guide);
            currentFood.setImage(imageUrl);
            currentFood.setListUsedIngredients(new ArrayList<>(currentIngredients));

            firestore.collection("Food").document(currentFood.getId())
                    .set(currentFood)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Đã cập nhật món ăn", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi cập nhật món ăn", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            List<UsedIngredients> newIngredients = (List<UsedIngredients>) data.getSerializableExtra("updated_ingredients");
            if (newIngredients != null) {
                currentIngredients.clear();
                currentIngredients.addAll(newIngredients);
                ingredientAdapter.notifyDataSetChanged();
            }
        }
    }
}