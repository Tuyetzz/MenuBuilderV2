package com.example.menubuilderv2.View.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.FoodAdapter;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageRecipesFragment extends Fragment {

    public ManageRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manage_recipes, container, false);

        Button btnMyRecipes = rootView.findViewById(R.id.btn_my_recipes);
        Button btnSuggestedRecipes = rootView.findViewById(R.id.btn_suggested_recipes);

        System.out.println("Day la ManageRecipesFragment");
        btnMyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đổi màu nền khi nhấn nút "My Recipes"
                btnMyRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorSelected));
                btnSuggestedRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorUnselected));
            }
        });

        btnSuggestedRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đổi màu nền khi nhấn nút "Suggested Recipes"
                btnSuggestedRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorSelected));
                btnMyRecipes.setBackgroundTintList(getResources().getColorStateList(R.color.colorUnselected));
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // List ảo
        List<Food> dummyList = new ArrayList<>();
        dummyList.add(new Food("1", "Cơm chiên", "Ngon, nhanh gọn", "Món chính", "1. Nấu cơm\n2. Chiên", "https://i.imgur.com/abcd.jpg", new ArrayList<>()));
        dummyList.add(new Food("2", "Gỏi cuốn", "Món ăn truyền thống", "Khai vị", "1. Cuốn\n2. Chấm", "https://i.imgur.com/efgh.jpg", new ArrayList<>()));
        dummyList.add(new Food("3", "Phở bò", "Đặc sản Việt", "Món chính", "1. Luộc xương\n2. Thêm bánh phở", "https://i.imgur.com/ijkl.jpg", new ArrayList<>()));

        FoodAdapter adapter = new FoodAdapter(getContext(), dummyList, food -> {
            Intent intent = new Intent(getContext(), ViewFoodDetailActivity.class);
            intent.putExtra("food_data", food);
            startActivity(intent);
        });


        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAddRecipe = rootView.findViewById(R.id.fab_add_recipe);
        fabAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddFoodActivity.class);
            startActivity(intent);
        });


        return rootView;
    }
}
