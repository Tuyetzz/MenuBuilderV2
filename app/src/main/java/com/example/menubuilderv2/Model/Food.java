package com.example.menubuilderv2.Model;

import java.util.List;
import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String desc;
    private String category;
    private String guide;
    private String image;
    private List<UsedIngredients> listUsedIngredients;

    // Constructor rỗng (cần cho Firestore)
    public Food() {
    }

    // Constructor đầy đủ
    public Food(String id, String name, String desc, String category, String guide, String image, List<UsedIngredients> listUsedIngredients) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.guide = guide;
        this.image = image;
        this.listUsedIngredients = listUsedIngredients;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<UsedIngredients> getListUsedIngredients() {
        return listUsedIngredients;
    }

    public void setListUsedIngredients(List<UsedIngredients> listUsedIngredients) {
        this.listUsedIngredients = listUsedIngredients;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", category='" + category + '\'' +
                ", guide='" + guide + '\'' +
                ", image='" + image + '\'' +
                ", listUsedIngredients=" + listUsedIngredients +
                '}';
    }
}
