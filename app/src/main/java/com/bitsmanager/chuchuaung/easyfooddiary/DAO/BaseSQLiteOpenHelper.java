package com.bitsmanager.chuchuaung.easyfooddiary.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME = "easy_food_diary";
    public static final int DATABASEVERSION = 1;


    public BaseSQLiteOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        //create new switch relay button
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //onCreate(db);
    }


    public void openDatabase() {
        this.getWritableDatabase();
    }
}
