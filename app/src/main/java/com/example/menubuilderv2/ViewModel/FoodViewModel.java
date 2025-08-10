package com.example.menubuilderv2.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodViewModel extends ViewModel {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Food>> foodListLiveData = new MutableLiveData<>();

    public LiveData<List<Food>> getFoodListLiveData() {
        return foodListLiveData;
    }

    public void loadAllFoods() {
        firestore.collection("Food")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null) {
                        List<Food> tempList = new ArrayList<>();
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            Food food = doc.toObject(Food.class);
                            if (food != null) {
                                tempList.add(food);
                            }
                        }
                        foodListLiveData.setValue(tempList);
                    }
                });
    }

    public void saveFoodWithIngredients(@NonNull Food food, @NonNull List<UsedIngredients> usedList,
                                        Runnable onSuccess, Runnable onFailure) {

        // Tạo id cho Food trước
        DocumentReference foodRef = firestore.collection("Food").document();
        String foodId = foodRef.getId();
        food.setId(foodId);

        // Gán id cho tất cả UsedIngredients trước khi lưu Food
        for (UsedIngredients used : usedList) {
            DocumentReference usedRef = firestore.collection("UsedIngredients").document();
            String usedId = usedRef.getId();
            used.setId(usedId);
        }

        // Gán lại list vào Food (đảm bảo listUsedIngredients trong Firestore có id)
        food.setListUsedIngredients(usedList);
        WriteBatch batch = firestore.batch();
        // Lưu Food (đã có listUsedIngredients đầy đủ id)
        batch.set(foodRef, food);

        // Lưu từng UsedIngredients vào collection riêng
        for (UsedIngredients used : usedList) {
            DocumentReference usedRef = firestore.collection("UsedIngredients").document(used.getId());

            Map<String, Object> data = new HashMap<>();
            data.put("id", used.getId());
            data.put("quantity", used.getQuantity());
            data.put("Ingredientsid", used.getIngredient().getId());
            data.put("Foodid", foodId);

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
