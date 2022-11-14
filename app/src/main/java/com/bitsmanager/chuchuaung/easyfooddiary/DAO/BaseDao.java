package com.bitsmanager.chuchuaung.easyfooddiary.DAO;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class BaseDao {
    public static String DB_PATH = "/data/data/com.bitsmanager.chuchuaung.easyfooddiary/databases/";

    public static String DB_Name = "easy_food_diary";
    protected SQLiteDatabase database;
    protected BaseSQLiteOpenHelper dbHelper;
    static Context context;

    public BaseDao(Context context) {
        dbHelper = new BaseSQLiteOpenHelper(context);
        database = dbHelper.getWritableDatabase();
        //database =SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        this.context = context;
    }

    public static boolean checkDatabase() {
        Log.i("checkDatabase", "here");
        SQLiteDatabase db = null;
        File file = new File(DB_PATH);
        file.mkdirs();
        Log.i("checkDatabase", "filemaking :" + file.mkdirs());
        File fileDb = new File(DB_PATH + DB_Name);
        Log.i("checkDatabase", "dbexist?" + fileDb.exists());
        return fileDb.exists();
    }

    public static void copyDatabase(Context context) {
        Log.i("copyDatabase", "Database is being copied to device!");

        byte[] buffer = new byte[1024];
        OutputStream myOutputStream = null;
        InputStream myInputStream = null;
        int length;
        try {
            AssetManager assetManager = context.getResources().getAssets();
            myInputStream = assetManager.open(DB_Name);
            Log.i("DB Path", "++++++++" + DB_PATH + DB_Name);
            myOutputStream = new FileOutputStream(DB_PATH + DB_Name);
            while ((length = myInputStream.read(buffer)) > 0) {
                myOutputStream.write(buffer, 0, length);
            }
            myOutputStream.close();
            myOutputStream.flush();
            myInputStream.close();
            Log.i("copyDatabase", "Copy database to device is successful!");
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void exportDatabase(Context context, String fName) {
        String folderName = "/Easy_Food/";
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        FileChannel source = null;
        FileChannel destination = null;

        String currentDBPath = "/data/com.bitsmanager.chuchuaung.easyfooddiary/databases/" + DB_Name;
        String backupDBPath = folderName + fName;

        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            String fullPath = Environment.getExternalStorageDirectory().getPath() + folderName;
            Log.i("FullPath", fullPath);
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
                Log.i("Make dir", dir.mkdir() + "");
            }
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(context, "DB Backuped!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("______DB", e.getMessage());
        }
    }

    public static void replaceWithOldDatabase(Context context) {
        String noBkFile = "There is no backup file!";
        String bkSuccess = "Backup Successful";
        String bkErr = "Backup Error!";
        String bkDatabase = "easy_food_diary";
        String folderName = "/Easy_Food/";
        boolean isError = false;
        String backUpDBPath = Environment.getExternalStorageDirectory().getPath() + folderName;
        File bkFile = new File(backUpDBPath, bkDatabase);
        if (bkFile.exists()) {
            // replace backup db to android
            try {
                FileOutputStream fout = new FileOutputStream(DB_PATH + bkDatabase);
                FileInputStream fin = new FileInputStream(backUpDBPath + bkDatabase);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fin.read(buffer)) > 0) {
                    fout.write(buffer, 0, length);
                }
                fout.close();
                fout.flush();
                fin.close();
            } catch (FileNotFoundException e) {
                isError = true;
                e.printStackTrace();
            } catch (IOException e) {
                isError = true;
                e.printStackTrace();
            }
            if (isError){

            }
            else{

            }
        } else {

        }
    }
}
