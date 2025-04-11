package com.example.menubuilderv2.Model;

public class UsedIngredients {
    public String id;
    public float quantity;
    public Ingredient ingredient;

    public UsedIngredients() {
    }

    public UsedIngredients(String id, float quantity, Ingredient ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "UsedIngredients{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
