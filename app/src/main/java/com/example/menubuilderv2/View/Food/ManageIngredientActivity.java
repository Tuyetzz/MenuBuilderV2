package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.IngredientAdapter;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.IngredientViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ManageIngredientActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientAdapter adapter;
    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_ingredient);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Go back
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Add new ingredient
        findViewById(R.id.btnAddIngredient).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddIngredientActivity.class);
            startActivity(intent);
        });

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientList = new ArrayList<>();
        adapter = new IngredientAdapter(this, ingredientList);
        recyclerView.setAdapter(adapter);

        IngredientViewModel viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        viewModel.getIngredients().observe(this, ingredients -> {
            ingredientList.clear();
            ingredientList.addAll(ingredients);
            adapter.notifyDataSetChanged();
        });

        viewModel.loadIngredients(); // gọi load 1 lần

    }

}
