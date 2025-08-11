package com.example.menubuilderv2.Model;

import java.io.Serializable;

public class UsedIngredients implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String quantity;
    private Ingredient ingredient;

    public UsedIngredients() {
    }

    public UsedIngredients(String id, String quantity, Ingredient ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "UsedIngredients{" +
                "id='" + id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", ingredient=" + ingredient +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
