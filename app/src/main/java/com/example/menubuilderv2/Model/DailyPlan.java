package com.example.menubuilderv2.Model;

import java.util.Date;
import java.util.List;

public class DailyPlan {
    public String id;
    public Date date;
    public String userId;
    public List<UsedFood> listUsedFood;

    public DailyPlan() {
    }

    public DailyPlan(String id, Date date, String userId, List<UsedFood> listUsedFood) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.listUsedFood = listUsedFood;
    }

    @Override
    public String toString() {
        return "DailyPlan{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                ", listUsedFood=" + listUsedFood +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UsedFood> getListUsedFood() {
        return listUsedFood;
    }

    public void setListUsedFood(List<UsedFood> listUsedFood) {
        this.listUsedFood = listUsedFood;
    }
}
