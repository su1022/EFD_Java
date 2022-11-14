package com.bitsmanager.chuchuaung.easyfooddiary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;

import java.util.ArrayList;
import java.util.List;

public class DailySQLDao extends BaseDao {
    String tableName="detail_calories_table";
    String detailId="id";
    String dailyCalId="dailyCalId";
    String foodId="food_id";
    String quantity="quantity";

    public DailySQLDao(Context context) {
        super(context);
    }

    public long insertDailyFoodData(DailyCalDetail daily_calDetail){
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(foodId, daily_calDetail.getFood_id());
        values.put(dailyCalId, daily_calDetail.getId());
        values.put(quantity, daily_calDetail.getQuantity());

        try {
            result = database.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long updateDailyFoodData(DailyCalDetail daily_calDetail) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(foodId, daily_calDetail.getFood_id());
        values.put(dailyCalId, daily_calDetail.getId());
        values.put(quantity, daily_calDetail.getQuantity());

        try {
            result = database.update(tableName,values,detailId + " = " + daily_calDetail.getId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<DailyCalDetail> retrieveDailyFoodData( ){
        List<DailyCalDetail> daily_calDetail = new ArrayList<>();
       String sql = "SELECT * FROM " + tableName;
        try {
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    DailyCalDetail daily_calories= new DailyCalDetail();
                    daily_calories.setId(cursor.getInt(cursor.getColumnIndexOrThrow(detailId)));
                    daily_calories.setDailyCalId(cursor.getInt(cursor.getColumnIndexOrThrow(dailyCalId)));
                    daily_calories.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow(foodId)));
                    daily_calories.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(quantity)));
                    daily_calDetail.add(daily_calories);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return daily_calDetail;
    }

}
