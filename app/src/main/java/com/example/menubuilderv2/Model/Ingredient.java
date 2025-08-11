package com.example.menubuilderv2.Model;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String category;
    private String image;
    private String desc;

    public Ingredient() {
    }

    public Ingredient(String id, String name, String category, String image, String desc) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.image = image;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
