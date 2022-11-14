package com.bitsmanager.chuchuaung.easyfooddiary.Interface;

import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;

public interface DailyCaloriesCalculationViewClickLisetener {
    void onClick(DailyCalDetail daily_calDetail);
    void showQtyCustomDialog(DailyCalDetail dailyCalDetail,int positon);

}
