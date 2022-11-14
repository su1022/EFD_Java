package com.bitsmanager.chuchuaung.easyfooddiary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodSQLDao extends BaseDao
{
    String tableName="food_diary_table";
    String food_id="food_id";
    String fid="food_type_id";
    String foodName="name";
    String foodCalorie="calorie";


    public FoodSQLDao(Context context) {
        super(context);
    }

    //Insert User Data
    public long insertFoodData(Food food){
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(fid, food.getFood_type_id());
        values.put(foodName, food.getName());
        values.put(foodCalorie, food.getCalorie());
        try {
            result = database.insert(tableName,null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Food getFood(int f_id) {
        String query = "SELECT * FROM " + tableName + " WHERE " + food_id + " = " + f_id;
        Food food = new Food();
        try {
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    food.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow(food_id)));
                    food.setFood_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(fid)));
                    food.setName(cursor.getString(cursor.getColumnIndexOrThrow(foodName)));
                    food.setCalorie(cursor.getInt(cursor.getColumnIndexOrThrow(foodCalorie)));
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return food;
    }

    //Update User Data
    public long updateFoodData(Food food) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(fid, food.getFood_type_id());
        values.put(foodName, food.getName());
        values.put(foodCalorie, food.getCalorie());
        try {
            result = database.update(tableName,values,food_id + " = " + food.getFood_id(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //Retrieve all user
    public List<Food> retrieveFoodData(int food_type){
        List<Food> foods = new ArrayList<>();
        String sql = "Select * From food_diary_table where food_type_id= " + food_type ;
        try {
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    Food food= new Food();
                    food.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow(food_id)));
                    food.setFood_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(fid)));
                    food.setName(cursor.getString(cursor.getColumnIndexOrThrow(foodName)));
                    food.setCalorie(cursor.getInt(cursor.getColumnIndexOrThrow(foodCalorie)));
                    foods.add(food);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return foods;
    }

}
