package com.example.menubuilderv2.View.Food;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private List<Ingredient> selectedIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_food);

        // Adjust padding for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerIngredientSelect);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up IngredientAdapterSelect
        adapter = new IngredientAdapterSelect(this, ingredientList, (ingredient, quantity) -> {
            if (quantity.isEmpty()) {
                selectedIngredients.remove(ingredient);  // Remove ingredient if quantity is empty
            } else {
                ingredient.setDesc(quantity);  // Set the quantity as the ingredient's description temporarily
                if (!selectedIngredients.contains(ingredient)) {
                    selectedIngredients.add(ingredient);  // Add ingredient if it's not already in the selected list
                }
            }
            updateSelectedIngredientsSummary();  // Update summary text
        });
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        edtSearchIngredient = findViewById(R.id.edtSearchIngredient);
        txtSelectedIngredientsSummary = findViewById(R.id.txtSelectedIngredientsSummary);

        // Observe the ingredient data from ViewModel
        ingredientViewModel.getIngredients().observe(this, ingredients -> {
            if (ingredients != null && !ingredients.isEmpty()) {
                ingredientList.clear();
                ingredientList.addAll(ingredients);
                adapter.updateList(ingredientList);  // Update RecyclerView with data from DB
                Log.d("AddFoodActivity", "Ingredients loaded: " + ingredientList.size());
            } else {
                Toast.makeText(AddFoodActivity.this, "No ingredients found.", Toast.LENGTH_SHORT).show();
                Log.d("AddFoodActivity", "No ingredients found");
            }
        });

        // Load ingredients from the database
        ingredientViewModel.loadIngredients();

        // Setup search functionality
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
        for (Ingredient ingredient : selectedIngredients) {
            if (ingredient.getDesc() != null && !ingredient.getDesc().isEmpty()) {
                summary.append("\n").append(ingredient.getName()).append(": ").append(ingredient.getDesc());
            }
        }
        txtSelectedIngredientsSummary.setText(summary.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AddFoodActivity", "Ingredients onResume: " + ingredientList.size());
    }
}
