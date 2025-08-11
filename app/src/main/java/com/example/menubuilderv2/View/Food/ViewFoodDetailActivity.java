package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.menubuilderv2.Adapter.FoodDetailIngredientAdapter;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewFoodDetailActivity extends AppCompatActivity {

    private Food currentFood;
    private String currentFoodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_food_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtTitle = findViewById(R.id.txtFoodTitle);
        TextView txtCategory = findViewById(R.id.txtFoodCategory);
        TextView txtDesc = findViewById(R.id.txtFoodDesc);
        TextView txtGuide = findViewById(R.id.txtFoodGuide);
        ImageView imgFood = findViewById(R.id.imgFoodDetail);
        RecyclerView rvIngredients = findViewById(R.id.rvIngredients);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnEdit = findViewById(R.id.btnEdit);
        ImageView btnDelete = findViewById(R.id.btnDelete);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnEdit.setOnClickListener(v -> {
            if (currentFood != null) {
                Intent intent = new Intent(this, EditFoodActivity.class);
                intent.putExtra("food_data", currentFood);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(v -> showDeleteConfirmationDialog());

        rvIngredients.setLayoutManager(new LinearLayoutManager(this));

        Food food = (Food) getIntent().getSerializableExtra("food_data");
        if (food != null) {
            currentFood = food;
            bindFoodToUi(food, txtTitle, txtCategory, txtDesc, txtGuide, imgFood, rvIngredients);
            return;
        }

        String foodId = getIntent().getStringExtra("food_id");
        if (foodId != null && !foodId.isEmpty()) {
            currentFoodId = foodId;
            FirebaseFirestore.getInstance().collection("Food").document(foodId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Food fetched = documentSnapshot.toObject(Food.class);
                        if (fetched != null) {
                            currentFood = fetched;
                            bindFoodToUi(fetched, txtTitle, txtCategory, txtDesc, txtGuide, imgFood, rvIngredients);
                        } else {
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> finish());
        } else {
            finish();
        }
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa món ăn này?")
                .setPositiveButton("Xóa", (dialog, which) -> deleteFood())
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteFood() {
        if (currentFoodId != null) {
            FirebaseFirestore.getInstance().collection("Food").document(currentFoodId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi xóa món ăn", Toast.LENGTH_SHORT).show();
                    });
        } else if (currentFood != null && currentFood.getId() != null) {
            FirebaseFirestore.getInstance().collection("Food").document(currentFood.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi xóa món ăn", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void bindFoodToUi(Food food, TextView txtTitle, TextView txtCategory, TextView txtDesc, TextView txtGuide,
                               ImageView imgFood, RecyclerView rvIngredients) {
        txtTitle.setText(food.getName());
        txtCategory.setText(food.getCategory());
        txtDesc.setText(food.getDesc());
        txtGuide.setText(food.getGuide());

        Glide.with(this)
                .load(food.getImage())
                .placeholder(R.drawable.ic_lunch)
                .into(imgFood);

        rvIngredients.setAdapter(new FoodDetailIngredientAdapter(this, food.getListUsedIngredients()));
    }
}