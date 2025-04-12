package com.example.menubuilderv2.View.Food;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.R;

public class ViewFoodDetailActivity extends AppCompatActivity {

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

        Food food = (Food) getIntent().getSerializableExtra("food_data");

        if (food != null) {
            TextView txtTitle = findViewById(R.id.txtFoodTitle);
            TextView txtGuide = findViewById(R.id.txtFoodGuide);
            txtTitle.setText(food.getName());
            txtGuide.setText(food.getGuide());

            System.out.println("Thuc an chuyen sang la " + food);

            ImageView imgFood = findViewById(R.id.imgFoodDetail);
            Glide.with(this)
                    .load(food.getImage())
                    .placeholder(R.drawable.ic_lunch)
                    .into(imgFood);
        }

    }
}