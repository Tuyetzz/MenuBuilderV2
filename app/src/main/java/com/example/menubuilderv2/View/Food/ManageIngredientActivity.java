package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.IngredientAdapterManage;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;

import java.util.ArrayList;
import java.util.List;

public class ManageIngredientActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientAdapterManage adapter;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private IngredientViewModel viewModel;
    private ProgressBar progressBar;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_ingredient);

        // Setting the insets for system bars to avoid overlap
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        initViews();

        // Setup RecyclerView and its adapter
        setupRecyclerView();

        // Setup ViewModel
        setupViewModel();

        // Setup search functionality
        setupSearchListener();
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        edtSearch = findViewById(R.id.edtSearchIngredient);
        recyclerView = findViewById(R.id.recyclerIngredients);

        System.out.println("Day la ManageIngredientActivity");
        // Setup Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Navigate to AddIngredientActivity on clicking "Add Ingredient"
        findViewById(R.id.btnAddIngredient).setOnClickListener(v -> {
            startActivity(new Intent(this, AddIngredientActivity.class));
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IngredientAdapterManage(this, ingredientList, new IngredientAdapterManage.OnIngredientActionListener() {
            @Override
            public void onEdit(Ingredient ingredient) {
                Intent intent = new Intent(ManageIngredientActivity.this, EditIngredientActivity.class);
                System.out.println("Truyen ingredient day " + ingredient);
                intent.putExtra("ingredient", ingredient); // Passing the entire ingredient object for editing
                startActivity(intent);
            }

            @Override
            public void onDelete(Ingredient ingredient) {
                // Delete ingredient when user selects "Delete"
                deleteIngredient(ingredient);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void deleteIngredient(Ingredient ingredient) {
        // Call ViewModel to delete ingredient from the database
        viewModel.deleteIngredient(ingredient, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Ingredient deleted successfully!", Toast.LENGTH_SHORT).show();
                viewModel.loadIngredients(); // Reload ingredient list after deletion
            } else {
                Toast.makeText(this, "Failed to delete ingredient", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        progressBar.setVisibility(View.VISIBLE);

        // Observing the ingredient data from ViewModel
        viewModel.getIngredients().observe(this, ingredients -> {
            progressBar.setVisibility(View.GONE); // Hide progress bar after ingredients are loaded
            ingredientList.clear();
            ingredientList.addAll(ingredients);
            adapter.updateList(ingredientList); // Update RecyclerView with full list of ingredients
        });
    }

    private void setupSearchListener() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
        });
    }

    private void filterList(String keyword) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.name.toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(ingredient);
            }
        }
        adapter.updateList(filteredList); // Update the adapter with filtered list
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadIngredients(); // Reload ingredients when coming back to this activity
    }
}
