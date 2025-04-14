package com.example.menubuilderv2.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.menubuilderv2.Model.Ingredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewModel extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Ingredient>> ingredientsLiveData = new MutableLiveData<>();

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredientsLiveData;
    }

    // Load all ingredients from Firestore
    public void loadIngredients() {
        db.collection("Ingredient")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Ingredient> tempList = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Ingredient ingredient = doc.toObject(Ingredient.class);
                        if (ingredient != null) {
                            tempList.add(ingredient);
                        }
                    }

                    // Debug log to check if data is coming from Firestore
                    Log.d("IngredientViewModel", "Loaded ingredients: " + tempList.size());

                    ingredientsLiveData.setValue(tempList);
                })
                .addOnFailureListener(e -> {
                    // Handle failure to fetch data
                    Log.e("IngredientViewModel", "Error loading ingredients", e);
                });
    }


    // Save a new ingredient or update an existing one
    public void saveIngredient(Ingredient ingredient, OnCompleteListener<Void> listener) {
        db.collection("Ingredient")
                .document(ingredient.id)
                .set(ingredient)
                .addOnCompleteListener(listener);
    }

    // Delete an ingredient from Firestore
    public void deleteIngredient(Ingredient ingredient, OnCompleteListener<Void> listener) {
        db.collection("Ingredient")
                .document(ingredient.id)
                .delete()
                .addOnCompleteListener(listener);
    }

    // Update an ingredient in Firestore
    public void updateIngredient(Ingredient ingredient, OnCompleteListener<Void> listener) {
        db.collection("Ingredient")
                .document(ingredient.id)
                .set(ingredient)
                .addOnCompleteListener(listener);
    }
}
