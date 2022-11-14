package com.bitsmanager.chuchuaung.easyfooddiary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bitsmanager.chuchuaung.easyfooddiary.Model.BMI;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;

import java.util.ArrayList;
import java.util.List;

public class BmiSQLDao extends BaseDao {
    String tableName = "bmi_table";
    String id = "bmi_id";
    String user_id = "user_id";
    String weight = "bmi_weight";
    String height = "bmi_height";
    String bmi_result = "result";
    String Kg = "is_Kg";
    String cm = "is_cm";

    public BmiSQLDao(Context context) {
        super(context);
    }

    //Insert BMI Data
    public long insertBMIData(BMI bmi){
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(user_id, bmi.getUser_id());
        values.put(weight, bmi.getBmi_weight());
        values.put(height, bmi.getBmi_height());
        values.put(bmi_result, bmi.getResult());
        values.put(Kg, bmi.getIs_Kg());
        values.put(cm, bmi.getIs_cm());
        try {
            result = database.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Update bMI Data
    public long updateBMIData(BMI bmi){
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(user_id, bmi.getUser_id());
        values.put(weight, bmi.getBmi_weight());
        values.put(height, bmi.getBmi_height());
        values.put(bmi_result, bmi.getResult());
        values.put(Kg, bmi.getIs_Kg());
        values.put(cm, bmi.getIs_cm());
        try {
            result = database.update(tableName, values, id + " = "+bmi.getBmi_id(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Delete BMI Data
    public void deleteBMIData(BMI bmi){
        try {
            database.delete(tableName, id + " = " + bmi.getBmi_id(), null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //Retrieve BMI Data
    public List<BMI> retrieveBMIData(){
        List<BMI> bmiList = new ArrayList<>();
        String sql = "Select * From " +tableName;
        try {
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.moveToFirst()){
                do {
                    BMI bmi = new BMI();
                    bmi.setBmi_id(cursor.getInt(cursor.getColumnIndexOrThrow(id)));
                    bmi.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow(user_id)));
                    bmi.setBmi_weight(cursor.getInt(cursor.getColumnIndexOrThrow(weight)));
                    bmi.setBmi_height(cursor.getInt(cursor.getColumnIndexOrThrow(height)));
                    bmi.setResult(cursor.getInt(cursor.getColumnIndexOrThrow(bmi_result)));
                    bmi.setIs_Kg(cursor.getInt(cursor.getColumnIndexOrThrow(Kg)));
                    bmi.setIs_cm(cursor.getInt(cursor.getColumnIndexOrThrow(cm)));
                    bmiList.add(bmi);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bmiList;
    }

}
