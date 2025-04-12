package com.example.menubuilderv2.ViewModel;

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

    public void loadIngredients() {
        db.collection("Ingredient")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Ingredient> tempList = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Ingredient ingredient = doc.toObject(Ingredient.class);
                        if (ingredient != null) tempList.add(ingredient);
                    }
                    ingredientsLiveData.setValue(tempList);
                });
    }

    public void saveIngredient(Ingredient ingredient, OnCompleteListener<Void> listener) {
        db.collection("Ingredient")
                .document(ingredient.id)
                .set(ingredient)
                .addOnCompleteListener(listener);
    }
}
