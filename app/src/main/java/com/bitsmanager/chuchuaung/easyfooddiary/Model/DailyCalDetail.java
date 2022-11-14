package com.bitsmanager.chuchuaung.easyfooddiary.Model;

import java.io.Serializable;

public class DailyCalDetail implements Serializable {
    int id,food_id,dailyCalId;
    int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getDailyCalId() {
        return dailyCalId;
    }

    public void setDailyCalId(int dailyCalId) {
        this.dailyCalId = dailyCalId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
