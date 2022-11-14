package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bitsmanager.chuchuaung.easyfooddiary.Adapter.FoodListAdapter;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.BaseDao;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.DailySQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.FoodSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.FoodViewClickListener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodItemActivity extends AppCompatActivity implements FoodViewClickListener {

    EditText edit_search_foodItem;
    RecyclerView rcy_foodItem;
    FoodSQLDao foodSQLDao;
    private List<Food> foodList = new ArrayList<>();
    private FoodListAdapter adapter;
    int food_type_id = 1;
    int quantity=1;
    int REQ_NEW_FOOD_ITEM;
    private boolean is_show;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new_food_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == R.id.add_new_food_item) {
            Intent intent = new Intent(FoodItemActivity.this, NewFoodItemAddActivity.class);
            intent.putExtra("f_type_id", food_type_id);
            intent.putExtra("isEdit", false);
            startActivityForResult(intent, REQ_NEW_FOOD_ITEM);
        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bindToList();
        if (requestCode == REQ_NEW_FOOD_ITEM && resultCode == RESULT_OK && data != null) {
            bindToList();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        is_show = getIntent().getBooleanExtra("is_show", false);

        edit_search_foodItem = (EditText) findViewById(R.id.edit_search_foodItem);
        rcy_foodItem = (RecyclerView) findViewById(R.id.rcy_foodItem);

        DailyCalDetail dailyCalDetail = new DailyCalDetail();

        food_type_id = getIntent().getIntExtra("food_type", 1);
        quantity = getIntent().getIntExtra("qty",1);


        foodSQLDao = new FoodSQLDao(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcy_foodItem.setLayoutManager(layoutManager);
        bindToList();

        edit_search_foodItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void bindToList() {
        foodList = foodSQLDao.retrieveFoodData(food_type_id);
        adapter = new FoodListAdapter(foodList, this);
        adapter.setListener(this);
        rcy_foodItem.setAdapter(adapter);
    }


    @Override
    public void onClick(Food food) {
        Intent intent = new Intent(this, NewFoodItemAddActivity.class);
        intent.putExtra("fid", food);
        intent.putExtra("name", food.getName());
        intent.putExtra("calorie", food.getCalorie());
        startActivityForResult(intent, REQ_NEW_FOOD_ITEM);
    }

    @Override
    public void onEdit(Food food, int index) {
        Intent intent = new Intent(this, NewFoodItemAddActivity.class);
        intent.putExtra("edit_food", food);
        intent.putExtra("position", index);
        intent.putExtra("isEdit", true);
        startActivityForResult(intent, REQ_NEW_FOOD_ITEM);
    }

    @Override
    public void showCustomDialog(Food food) {
        if (is_show) {
            showQuantitySelectDialog(food);
        }
    }


    public void showQuantitySelectDialog(final Food food) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_custom_dialog, null, false);


        Button btnAdding = (Button) view.findViewById(R.id.btnAdding);
        final Button btnDividing = (Button) view.findViewById(R.id.btnDividing);
        final TextView text_qty = (TextView) view.findViewById(R.id.text_qty);
        Button addDb = (Button) view.findViewById(R.id.btnAddDb);
        Button cancel = (Button) view.findViewById(R.id.btnCancel);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        /*dialog.dismiss();*/

        btnAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(text_qty.getText().toString().trim());
                num = num + 1;
                text_qty.setText("" + num);
                quantity = Integer.parseInt(text_qty.getText().toString());

            }
        });

        btnDividing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(text_qty.getText().toString().trim());
                if (num == 1) {
                } else {
                    num = num - 1;
                    text_qty.setText("" + num);
                    //quantity = Integer.parseInt(text_qty.getText().toString());
                }
            }
        });


        addDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DailyCalDetail calDetail = new DailyCalDetail();
                calDetail.setFood_id(food.getFood_id());
                calDetail.setQuantity(quantity);
                Intent intent = new Intent();
                intent.putExtra(Utility.key_add_food, calDetail);
               //intent.putExtra("food_cal", calDetail);
                setResult(RESULT_OK, intent);
                Log.i("food"+"....k....", String.valueOf(calDetail));
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

