package com.example.menubuilderv2.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodViewModel extends ViewModel {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void saveFoodWithIngredients(@NonNull Food food, @NonNull List<UsedIngredients> usedList,
                                        Runnable onSuccess, Runnable onFailure) {

        DocumentReference foodRef = firestore.collection("Food").document();
        String foodId = foodRef.getId();
        food.setId(foodId);

        WriteBatch batch = firestore.batch();

        batch.set(foodRef, food);

        for (UsedIngredients used : usedList) {
            Map<String, Object> data = new HashMap<>();
            data.put("quantity", used.getQuantity());
            data.put("Ingredientsid", used.getIngredient().getId());
            data.put("Foodid", foodId);

            DocumentReference usedRef = firestore.collection("UsedIngredients").document();
            batch.set(usedRef, data);
        }

        batch.commit()
                .addOnSuccessListener(unused -> {
                    if (onSuccess != null) onSuccess.run();
                })
                .addOnFailureListener(e -> {
                    if (onFailure != null) onFailure.run();
                });
    }
}
