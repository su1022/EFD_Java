package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.FoodSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.R;

public class NewFoodItemAddActivity extends AppCompatActivity {

    EditText edit_add_foodName, edit_add_foodCal;
    Button btnAdd;
    Food food;
    FoodSQLDao foodSQLDao;
    int edit_position;
    private int food_type_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food_item_add);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        edit_add_foodName = (EditText) findViewById(R.id.edit_add_foodName);
        edit_add_foodCal = (EditText) findViewById(R.id.edit_add_foodCal);
        foodSQLDao = new FoodSQLDao(this);
        food_type_id = getIntent().getIntExtra("f_type_id", 1);

        final Intent data = getIntent();
        final boolean isEdit = data.getBooleanExtra("isEdit", false);
        Food editfood = new Food();

        if (isEdit) {
            editfood = (Food) data.getSerializableExtra("edit_food");
            edit_add_foodName.setText(""+ editfood.getName());
            edit_add_foodCal.setText("" + editfood.getCalorie());
            edit_position = data.getIntExtra("position", 0);
        }

        final Food finalEditFood = editfood;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String food_name = edit_add_foodName.getText().toString().trim();
                String food_cal = edit_add_foodCal.getText().toString().trim();

                if (TextUtils.isEmpty(food_name)) {
                    edit_add_foodName.setError("Require");
                    return;
                }

                if (TextUtils.isEmpty(food_cal)) {
                    edit_add_foodCal.setError("Require");
                    return;
                }

                int calorie = Integer.parseInt(food_cal);
                Food food = new Food();
                food.setName(food_name);
                food.setFood_type_id(food_type_id);
                food.setCalorie(calorie);

                if (isEdit) {
                    food.setFood_id(finalEditFood.getFood_id());
                    food.setFood_type_id(finalEditFood.getFood_type_id());
                    foodSQLDao.updateFoodData(food);
                }else {
                    foodSQLDao.insertFoodData(food);
                }

                Intent intent = new Intent();
                intent.putExtra("new_food", food);
                intent.putExtra("isEdit", isEdit);
                intent.putExtra("position", edit_position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
