package com.example.menubuilderv2.Model;

import java.io.Serializable;

public class UsedFood implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private float quantity;
    private String partOfDay;
    private Food food;

    public UsedFood() {
    }

    @Override
    public String toString() {
        return "UsedFood{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", partOfDay='" + partOfDay + '\'' +
                ", food=" + food +
                '}';
    }

    public UsedFood(String id, float quantity, String partOfDay, Food food) {
        this.id = id;
        this.quantity = quantity;
        this.partOfDay = partOfDay;
        this.food = food;
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

    public String getPartOfDay() {
        return partOfDay;
    }

    public void setPartOfDay(String partOfDay) {
        this.partOfDay = partOfDay;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
