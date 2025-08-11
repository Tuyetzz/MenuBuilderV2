package com.example.menubuilderv2.Model;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class ShoppingList implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Date startDate;
    private Date endDate;
    private List<ShoppingListItems> listShoppingListItems;
    private User user;

    public ShoppingList(String id, Date startDate, Date endDate, List<ShoppingListItems> listShoppingListItems, User user) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listShoppingListItems = listShoppingListItems;
        this.user = user;
    }

    public ShoppingList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<ShoppingListItems> getListShoppingListItems() {
        return listShoppingListItems;
    }

    public void setListShoppingListItems(List<ShoppingListItems> listShoppingListItems) {
        this.listShoppingListItems = listShoppingListItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id='" + id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", listShoppingListItems=" + listShoppingListItems +
                ", user=" + user +
                '}';
    }
}
