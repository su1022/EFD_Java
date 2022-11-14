package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.FoodSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    CardView cardviewfruit, cardviewSnack, cardviewMeal, cardviewSoup, cardviewDrink;
    private int REQ_ALL_FOOD = 11;
    boolean is_show;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ALL_FOOD && resultCode == RESULT_OK && data != null) {
            DailyCalDetail dailyCalDetail = (DailyCalDetail) data.getSerializableExtra(Utility.key_add_food);
            if (dailyCalDetail != null) {
                Intent intent = new Intent();
                intent.putExtra(Utility.key_add_food, dailyCalDetail);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    LinearLayout FruitLayout, SnackLayout, CurryLayout, SoupLayout, DrinkLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        if (id == android.R.id.home) {
            finish ();
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_food);

        is_show = getIntent().getBooleanExtra("is_show", false);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        FruitLayout = (LinearLayout) findViewById (R.id.FruitLayout);
        SnackLayout = (LinearLayout) findViewById (R.id.SnackLayout);
        CurryLayout = (LinearLayout) findViewById (R.id.CurryLayout);
        SoupLayout = (LinearLayout) findViewById (R.id.SoupLayout);
        DrinkLayout = (LinearLayout) findViewById (R.id.DrinkLayout);

        FruitLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this,FoodItemActivity.class);
                intent.putExtra("food_type",3);
                intent.putExtra("is_show", is_show);
                startActivityForResult(intent,REQ_ALL_FOOD);

            }
        });

        SnackLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this,FoodItemActivity.class);
                intent.putExtra("food_type",2);
                intent.putExtra("is_show", is_show);
                startActivityForResult(intent,REQ_ALL_FOOD);
            }
        });

        CurryLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this,FoodItemActivity.class);
                intent.putExtra("food_type",1);
                intent.putExtra("is_show", is_show);
                startActivityForResult(intent,REQ_ALL_FOOD);
            }
        });

        SoupLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this,FoodItemActivity.class);
                intent.putExtra("food_type",5);
                intent.putExtra("is_show", is_show);
                startActivityForResult(intent,REQ_ALL_FOOD);
            }
        });

        DrinkLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this,FoodItemActivity.class);
                intent.putExtra("food_type",4);
                intent.putExtra("is_show", is_show);
                startActivityForResult(intent,REQ_ALL_FOOD);
            }
        });


    }
}

