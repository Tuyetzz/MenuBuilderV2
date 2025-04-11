package com.example.menubuilderv2.View;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.menubuilderv2.R;
import com.example.menubuilderv2.View.Food.ManageRecipesFragment;
import com.example.menubuilderv2.View.Plan.PlanMealsFragment;
import com.example.menubuilderv2.View.Shopping.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set window insets for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize BottomNavigationView and set listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_plan_meals:
                    selectedFragment = new PlanMealsFragment();
                    break;
                case R.id.nav_manage_recipes:
                    selectedFragment = new ManageRecipesFragment();
                    break;
                case R.id.nav_shopping_list:
                    selectedFragment = new ShoppingListFragment();
                    break;
            }

            // Replace the fragment container with the selected fragment
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_plan_meals);  // Set default fragment to Plan Meals
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);  // fragment_container is your FrameLayout ID
        fragmentTransaction.commit();
    }
}
