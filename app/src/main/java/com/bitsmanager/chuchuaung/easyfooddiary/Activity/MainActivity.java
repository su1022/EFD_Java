package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.BaseDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.UserViewClickListener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;

public class MainActivity extends AppCompatActivity {

    CardView cardviewCalories, cardviewFood, cardviewBmi, cardviewReport, cardviewSetting;
    TextView tvTitle;
    private int REQ_AddUser = 1;
    private int REQ_Calories = 2;
    private int REQ_Food = 3;
    private int REQ_Bmi = 4;
    private int REQ_Report = 6;
    private int REQ_Setting = 5;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == REQ_Setting){
            recreate ();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == R.id.action_new ){
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            startActivityForResult(intent, REQ_AddUser);
        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        cardviewCalories = (CardView) findViewById(R.id.cardviewCalories);
        cardviewFood = (CardView) findViewById(R.id.cardviewFood);
        cardviewBmi = (CardView) findViewById(R.id.cardviewBmi);
        cardviewReport = (CardView) findViewById(R.id.cardviewReport);
        cardviewSetting = (CardView) findViewById(R.id.cardviewSetting);

        tvTitle.setText("Welcome " + Utility.LOGIN_USER.getName());

        cardviewCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DailyCaloriesCalculationActivity.class);
                startActivityForResult(intent, REQ_Calories);
            }
        });

        cardviewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FoodActivity.class);
                intent.putExtra("is_show",false);
                startActivityForResult(intent,REQ_Food);
            }
        });

        cardviewBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BMICalculatorActivity.class);
                startActivityForResult(intent, REQ_Bmi);
            }
        });

        cardviewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivityForResult(intent, REQ_Report);
            }
        });

        cardviewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, REQ_Setting);
            }
        });


    }
}
