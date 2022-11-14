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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bitsmanager.chuchuaung.easyfooddiary.Adapter.DailyCaloriesCalculationAdapter;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.DailySQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.DailyCaloriesCalculationViewClickLisetener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;



import java.util.ArrayList;
import java.util.List;

public class DailyCaloriesCalculationActivity extends AppCompatActivity implements DailyCaloriesCalculationViewClickLisetener {


    int quantity,sums=333,id = 1;
    RecyclerView dailyRcyView;
    int REQ_FOOD = 11, REQ_DAILY = 22 ;
    DailyCaloriesCalculationAdapter adapter;
    private List<DailyCalDetail> dailyCalDetails = new ArrayList<>();
    Button btnCalculateDailyCal;
    DailySQLDao dailySQLDao;
    TextView tvTargetCal,tvDailyFoodQty,tvDailyTotalCal;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_FOOD && resultCode == RESULT_OK && data != null) {
            DailyCalDetail dailyCalDetail = (DailyCalDetail) data.getSerializableExtra(Utility.key_add_food);
            Log.i("food_id", "" + dailyCalDetail.getFood_id());
            dailyCalDetails.add(dailyCalDetail);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories_calculation);

        id = getIntent().getIntExtra("Item", 1);
        quantity = getIntent().getIntExtra("qty", 1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dailyRcyView = (RecyclerView) findViewById(R.id.dailyRcyView);
        btnCalculateDailyCal=(Button) findViewById(R.id.btnCalculateDailyCal);
        tvDailyTotalCal=(TextView)findViewById(R.id.tvDailyTotalCal);
        btnCalculateDailyCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tvDailyTotalCal.setText(""+sums);
            }
        });


        dailySQLDao = new DailySQLDao(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dailyRcyView.setLayoutManager(layoutManager);

        dailyCalDetails = dailySQLDao.retrieveDailyFoodData();
        adapter = new DailyCaloriesCalculationAdapter(dailyCalDetails, this);
        dailyRcyView.setAdapter(adapter);
        adapter.setListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_targetcalories, menu);
        getMenuInflater().inflate(R.menu.add_food_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_food_menu) {
            Intent intent = new Intent(DailyCaloriesCalculationActivity.this, FoodActivity.class);
            intent.putExtra("is_show",true);
            startActivityForResult(intent, REQ_FOOD);
        }
        if (id == R.id.add_TargetCalories) {
            showAddTargetCalDialog();
        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void showAddTargetCalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_target_calories, null, false);
        builder.setView(view);


        final TextView tvTargetCalories = (TextView) view.findViewById(R.id.tvTargetCalories);
        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberpicker);

        Button btnCancelDialog=(Button)view.findViewById(R.id.btnCancelDialog);
        Button btnAddQty=(Button)view.findViewById(R.id.btnAddQty);
        tvTargetCal=(TextView)findViewById(R.id.tvTargetCal);


        final String[] targetCalories = new String[31];
        int cal = 1000;
        for (int i = 0; i < targetCalories.length; i++) {
            targetCalories[i] = cal + "";
            cal += 100;
        }

        numberPicker.setMaxValue(targetCalories.length - 1);
        numberPicker.setDisplayedValues(targetCalories);
        numberPicker.setWrapSelectorWheel(false);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvTargetCalories.setText("Your selected calories is " + targetCalories[newVal]);
                tvTargetCal.setText("Your selected calories is " + targetCalories[newVal]);

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
        btnAddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(DailyCalDetail daily_calDetail) {
        Intent intent = new Intent(DailyCaloriesCalculationActivity.this, FoodItemActivity.class);
        intent.putExtra("food", daily_calDetail.getFood_id());
        intent.putExtra("qty", daily_calDetail.getQuantity());
        startActivityForResult(intent, REQ_DAILY);
    }


    @Override
    public void showQtyCustomDialog(final DailyCalDetail dailyCalDetail, final int positon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_custom_dialog, null, false);

        Button btnAdding = (Button) view.findViewById(R.id.btnAdding);
        final Button btnDividing = (Button) view.findViewById(R.id.btnDividing);
        final TextView text_qty = (TextView) view.findViewById(R.id.text_qty);
        Button addDb = (Button) view.findViewById(R.id.btnAddDb);
        Button cancel = (Button) view.findViewById(R.id.btnCancel);
        tvDailyFoodQty=(TextView) view.findViewById(R.id.tvDailyFoodQty);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        text_qty.setText(""+dailyCalDetail.getQuantity());

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
                    quantity = Integer.parseInt(text_qty.getText().toString());
                }
            }
        });


        addDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dailyCalDetail.setQuantity(quantity);
                dailyCalDetails.set(positon, dailyCalDetail);
                adapter.notifyDataSetChanged();
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
