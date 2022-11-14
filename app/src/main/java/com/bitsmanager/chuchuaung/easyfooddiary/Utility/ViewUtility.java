package com.bitsmanager.chuchuaung.easyfooddiary.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.bitsmanager.chuchuaung.easyfooddiary.R;

public class ViewUtility {
    public static void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
