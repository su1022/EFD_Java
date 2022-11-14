package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bitsmanager.chuchuaung.easyfooddiary.R;

public class TargetCaloriesActivity extends AppCompatActivity {

    NumberPicker numberPicker;
    TextView tvTargetCalories;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            recreate();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] targetCalories = new String[31];
        int cal = 1000;
        for (int i = 0; i <targetCalories.length; i++) {
            targetCalories[i
                    ] = cal + "";
            cal += 100;
        }
        setContentView(R.layout.activity_target_calories);

        tvTargetCalories=(TextView)findViewById(R.id.tvTargetCalories);

        numberPicker=(NumberPicker)findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(targetCalories.length - 1);
        numberPicker.setDisplayedValues(targetCalories);
        numberPicker.setWrapSelectorWheel(false);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvTargetCalories.setText("Your Selected Calories is :" + targetCalories[newVal]);

            }
        });



    }
}
