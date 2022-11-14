package com.bitsmanager.chuchuaung.easyfooddiary.Interface;

import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;

public interface FoodViewClickListener {
    void onClick(Food food);

    void showCustomDialog(Food food);
    void onEdit(Food food,int index);
}
