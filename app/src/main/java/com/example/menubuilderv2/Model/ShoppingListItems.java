package com.example.menubuilderv2.Model;

import java.io.Serializable;

public class ShoppingListItems implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private float quantity;
    private String unit;
    private Ingredient ingredient;

    public ShoppingListItems() {
    }

    public ShoppingListItems(String id, float quantity, String unit, Ingredient ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.unit = unit;
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "ShoppingListItems{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", ingredient=" + ingredient +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
