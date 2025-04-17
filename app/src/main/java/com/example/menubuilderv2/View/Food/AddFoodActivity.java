package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.IngredientAdapterSelect;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientAdapterSelect adapter;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private IngredientViewModel ingredientViewModel;
    private EditText edtSearchIngredient;
    private TextView txtSelectedIngredientsSummary;
    private List<UsedIngredients> selectedUsedIngredients = new ArrayList<>();
    private UsedIngredients usedIngredients;
    private Button btnManageIngredients;

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

        adapter = new IngredientAdapterSelect(this, ingredientList, (ingredient, quantity) -> {
            // Tạo mới đối tượng UsedIngredients cho mỗi lựa chọn
            UsedIngredients newUsedIngredient = new UsedIngredients();

            if (quantity.isEmpty()) {
                // Nếu quantity trống, xóa nguyên liệu khỏi danh sách đã chọn
                selectedUsedIngredients.removeIf(usedIngredient -> usedIngredient.getIngredient().equals(ingredient));
            } else {
                // Cập nhật hoặc thêm nguyên liệu vào danh sách đã chọn
                newUsedIngredient.setIngredient(ingredient);
                newUsedIngredient.setQuantity(quantity);

                // Nếu nguyên liệu chưa có trong danh sách, thêm vào
                boolean exists = false;
                for (UsedIngredients existing : selectedUsedIngredients) {
                    if (existing.getIngredient().equals(ingredient)) {
                        // Nếu nguyên liệu đã có rồi, chỉ cần cập nhật lại quantity
                        existing.setQuantity(quantity);
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    selectedUsedIngredients.add(newUsedIngredient);
                }
            }

            // Cập nhật lại summary với tất cả nguyên liệu đã chọn
            updateSelectedIngredientsSummary();
        });


        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        edtSearchIngredient = findViewById(R.id.edtSearchIngredient);
        txtSelectedIngredientsSummary = findViewById(R.id.txtSelectedIngredientsSummary);
        btnManageIngredients = findViewById(R.id.btnManageIngredients);
        btnManageIngredients.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang ManageIngredientActivity
            Intent intent = new Intent(AddFoodActivity.this, ManageIngredientActivity.class);
            startActivity(intent);
        });


        // Observe the ingredient data from ViewModel
        ingredientViewModel.getIngredients().observe(this, ingredients -> {
            if (ingredients != null && !ingredients.isEmpty()) {
                ingredientList.clear();
                ingredientList.addAll(ingredients);
                adapter.updateList(ingredientList);
            } else {
                Toast.makeText(AddFoodActivity.this, "No ingredients found.", Toast.LENGTH_SHORT).show();
            }
        });

        ingredientViewModel.loadIngredients();


        setupSearchListener();
    }

    private void setupSearchListener() {
        edtSearchIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterIngredients(s.toString());
            }
        });
    }

    private void filterIngredients(String query) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ingredient);
            }
        }
        adapter.updateList(filteredList);  // Update the adapter with the filtered list
    }

    private void updateSelectedIngredientsSummary() {
        StringBuilder summary = new StringBuilder("Selected Ingredients: ");
        for (UsedIngredients usedIngredient : selectedUsedIngredients) {
            if (usedIngredient.getIngredient().getName() != null && !usedIngredient.getIngredient().getName().isEmpty()) {
                summary.append("\n").append(usedIngredient.getIngredient().getName())
                        .append(": ").append(usedIngredient.getQuantity());

            }
        }
        System.out.println(selectedUsedIngredients);
        txtSelectedIngredientsSummary.setText(summary.toString());
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
