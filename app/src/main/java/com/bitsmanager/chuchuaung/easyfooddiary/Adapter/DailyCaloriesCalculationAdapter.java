package com.bitsmanager.chuchuaung.easyfooddiary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.bitsmanager.chuchuaung.easyfooddiary.DAO.FoodSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.DailyCaloriesCalculationViewClickLisetener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.R;

import java.util.List;

public class DailyCaloriesCalculationAdapter extends RecyclerView.Adapter<DailyCaloriesCalculationAdapter.DailyCaloriesCalculationViewHolder> {

    private List<DailyCalDetail> dailyCaloriesCalculationList;
    private Context context;
    private DailyCaloriesCalculationViewClickLisetener listener;
    private FoodSQLDao foodSQLDao;


    public void setListener(DailyCaloriesCalculationViewClickLisetener listener) {
        this.listener = listener;
    }

    public DailyCaloriesCalculationAdapter(List<DailyCalDetail> dailyCaloriesCalculationList, Context context) {
        this.dailyCaloriesCalculationList = dailyCaloriesCalculationList;
        this.context = context;
        foodSQLDao = new FoodSQLDao(context);
    }

    @NonNull
    @Override
    public DailyCaloriesCalculationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_daily_calories_view, viewGroup, false);
        DailyCaloriesCalculationAdapter.DailyCaloriesCalculationViewHolder dailyCaloriesCalculationViewHolder = new DailyCaloriesCalculationViewHolder(view);
        dailyCaloriesCalculationViewHolder.setDailyCaloriesCalulatorViewClickListener(listener);
        return dailyCaloriesCalculationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyCaloriesCalculationViewHolder dailyCaloriesCalculationViewHolder, final int i) {
        dailyCaloriesCalculationViewHolder.bindData(dailyCaloriesCalculationList.get(i));
    }

    @Override
    public int getItemCount() {
        return dailyCaloriesCalculationList.size();
    }

    public class DailyCaloriesCalculationViewHolder extends RecyclerView.ViewHolder {
        TextView tvDailyFoodName, tvDailyFoodCal, tvDailyFoodQty;
        ImageButton imgBtnQty;

        public DailyCaloriesCalculationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDailyFoodCal = (TextView) itemView.findViewById(R.id.tvDailyFoodCal);
            tvDailyFoodName = (TextView) itemView.findViewById(R.id.tvDailyFoodName);
            tvDailyFoodQty = (TextView) itemView.findViewById(R.id.tvDailyFoodQty);
            imgBtnQty = (ImageButton) itemView.findViewById(R.id.imgBtnQty);
        }

        public void bindData(DailyCalDetail daily_calDetail){
            Food food = foodSQLDao.getFood(daily_calDetail.getFood_id());
            tvDailyFoodName.setText("Name     :  " + food.getName());
            tvDailyFoodCal.setText("Calories :  " + food.getCalorie());
            tvDailyFoodQty.setText("Quantity :  " + daily_calDetail.getQuantity());
        }

        public void setDailyCaloriesCalulatorViewClickListener(final DailyCaloriesCalculationViewClickLisetener listener) {
            if (listener != null) {
                imgBtnQty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.showQtyCustomDialog(dailyCaloriesCalculationList.get(getAdapterPosition()), getAdapterPosition());
                    }
                });
            }
        }
    }
}


