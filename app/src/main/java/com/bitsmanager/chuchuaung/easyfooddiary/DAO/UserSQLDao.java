package com.bitsmanager.chuchuaung.easyfooddiary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserSQLDao extends BaseDao {
    String tableName = "user";
    String user_id = "user_id";
    String username = "user_username";
    String name = "user_name";
    String gender = "user_gender";
    String password = "user_password";

    public UserSQLDao(Context context) {
        super(context);
    }

    public User checkUserExit(String u_name, String u_password) {
        String query = "SELECT * FROM " + tableName + " WHERE " + username + " = \'" + u_name + "\' AND " + password + " = \'" + u_password + "\'";
        Log.i("query", "" + query);
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            User user = new User();
            do {
                user.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow(user_id)));
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(name)));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(username)));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(gender)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(password)));
            } while (cursor.moveToNext());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //insert User Data
    public long insertUserData(User user) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(name, user.getName());
        values.put(username, user.getUsername());
        values.put(gender, user.getGender());
        values.put(password, user.getPassword());
        try {
            result = database.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Update User Data
    public long updateUserData(User user) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(name, user.getName());
        values.put(username, user.getUsername());
        values.put(gender, user.getGender());
        values.put(password, user.getPassword());
        try {
            result = database.update(tableName, values, user_id + " = " + user.getUser_id(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Delete User Data
    public void deleteUserData(User user) {
        try {
            database.delete(tableName, "user_id = " + user.getUser_id(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(int u_id){
        String query = "SELECT * FROM " + tableName + " WHERE " + user_id + " = " + u_id;
        User user = new User();
        try {
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    user.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow(user_id)));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow(name)));
                    user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(username)));
                    user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(gender)));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(password)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    //Retrieve User Data
    public List<User> retrieveUserData(int user_Id) {
        List<User> users = new ArrayList<>();
        String sql = "Select * From user where user_id= " + user_Id;
        try {
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow(user_id)));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow(name)));
                    user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(username)));
                    user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(gender)));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(password)));
                    users.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
