package com.example.menubuilderv2.Model;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class DailyPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Date date;
    private String userId;
    private List<UsedFood> listUsedFood;

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
