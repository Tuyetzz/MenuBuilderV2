package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.FoodAdapter;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.R;
import com.example.menubuilderv2.ViewModel.FoodViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageRecipesFragment extends Fragment {

    public ManageRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_recipes, container, false);

        Button btnMyRecipes = rootView.findViewById(R.id.btn_my_recipes);
        Button btnSuggestedRecipes = rootView.findViewById(R.id.btn_suggested_recipes);

        btnMyRecipes.setOnClickListener(v -> {
            btnMyRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorSelected));
            btnSuggestedRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorUnselected));
        });

        btnSuggestedRecipes.setOnClickListener(v -> {
            btnSuggestedRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorSelected));
            btnMyRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorUnselected));
        });

        EditText searchRecipeInput = rootView.findViewById(R.id.searchRecipeInput);
        EditText categoryInput = rootView.findViewById(R.id.categoryInput);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // List gốc và list hiển thị
        List<Food> allFoods = new ArrayList<>();
        List<Food> displayFoods = new ArrayList<>();

        FoodAdapter adapter = new FoodAdapter(getContext(), displayFoods, food -> {
            Intent intent = new Intent(getContext(), ViewFoodDetailActivity.class);
            intent.putExtra("food_id", food.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // ViewModel
        FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getFoodListLiveData().observe(getViewLifecycleOwner(), foods -> {
            Log.d("ManageRecipesFragment", "Số lượng món ăn load từ Firestore: " + foods.size());
            for (Food f : foods) {
                Log.d("ManageRecipesFragment", "Food: " + f.getId() + " - " + f.getName());
            }

            allFoods.clear();
            allFoods.addAll(foods);

            displayFoods.clear();
            displayFoods.addAll(foods);

            adapter.updateList(new ArrayList<>(foods));
        });
        foodViewModel.loadAllFoods();

        // Lắng nghe tìm kiếm
        TextWatcher searchWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(allFoods, searchRecipeInput.getText().toString(), categoryInput.getText().toString(), adapter);
            }
        };

        searchRecipeInput.addTextChangedListener(searchWatcher);
        categoryInput.addTextChangedListener(searchWatcher);

        FloatingActionButton fabAddRecipe = rootView.findViewById(R.id.fab_add_recipe);
        fabAddRecipe.setOnClickListener(v -> startActivity(new Intent(getContext(), AddFoodActivity.class)));

        return rootView;
    }

    private void filterList(List<Food> originalList, String nameKeyword, String categoryKeyword, FoodAdapter adapter) {
        // Nếu cả hai ô trống thì hiện toàn bộ
        if (nameKeyword.trim().isEmpty() && categoryKeyword.trim().isEmpty()) {
            adapter.updateList(originalList);
            return;
        }

        List<Food> filteredList = new ArrayList<>();
        for (Food food : originalList) {
            boolean matchesName = food.getName() != null &&
                    food.getName().toLowerCase().contains(nameKeyword.toLowerCase());
            boolean matchesCategory = food.getCategory() != null &&
                    food.getCategory().toLowerCase().contains(categoryKeyword.toLowerCase());

            if (matchesName && matchesCategory) {
                filteredList.add(food);
            }
        }
        adapter.updateList(filteredList);
    }
}
