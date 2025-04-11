package com.example.menubuilderv2.Model;

import java.util.List;

public class User {
    public String id;
    public String username;
    public String pass;
    public String email;
    public String fullName;

    public List<String> dailyPlanIds;
    public List<String> shoppingListIds;

    public User() {
    }

    public User(String id, String username, String pass, String email, String fullName, List<String> dailyPlanIds, List<String> shoppingListIds) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.fullName = fullName;
        this.dailyPlanIds = dailyPlanIds;
        this.shoppingListIds = shoppingListIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getDailyPlanIds() {
        return dailyPlanIds;
    }

    public void setDailyPlanIds(List<String> dailyPlanIds) {
        this.dailyPlanIds = dailyPlanIds;
    }

    public List<String> getShoppingListIds() {
        return shoppingListIds;
    }

    public void setShoppingListIds(List<String> shoppingListIds) {
        this.shoppingListIds = shoppingListIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dailyPlanIds=" + dailyPlanIds +
                ", shoppingListIds=" + shoppingListIds +
                '}';
    }
}
