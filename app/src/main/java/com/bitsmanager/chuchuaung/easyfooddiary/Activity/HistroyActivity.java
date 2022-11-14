package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bitsmanager.chuchuaung.easyfooddiary.Adapter.BmiListAdapter;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.BmiSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.UserSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.BmiViewClickListener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.BMI;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.DailyCalDetail;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class HistroyActivity extends AppCompatActivity implements BmiViewClickListener {

    RecyclerView rcy_bmi;
    BmiSQLDao bmiSQLDao;
    UserSQLDao userSQLDao;
    private List<BMI> bmiList = new ArrayList<>();
    private BmiListAdapter adapter;
    int REQ_History;
    int REQ_User = 1;
    private List<User> userList = new ArrayList<>();


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_User && resultCode == RESULT_OK && data != null) {
            User user = (User) data.getSerializableExtra(String.valueOf(Utility.LOGIN_USER));
            userList.add(user);
            //Utility.LOGIN_USER.getClass();
            bindToList();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);

        rcy_bmi = (RecyclerView) findViewById(R.id.rcy_bmi);

        //user_Id = getIntent().getIntExtra("user_Id", 1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcy_bmi.setLayoutManager(layoutManager);

        bmiSQLDao = new BmiSQLDao(this);
        bindToList();
    }
    private void bindToList(){
        bmiList = bmiSQLDao.retrieveBMIData();
        //userList = userSQLDao.retrieveUserData(user_Id);
        adapter = new BmiListAdapter(bmiList, this);
        rcy_bmi.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onClick(BMI bmi) {
        Intent intent = new Intent(HistroyActivity.this, BMICalculatorActivity.class);
        intent.putExtra("history", bmi);
        //intent.putExtra("user_Id", 1);
        //intent.putExtra("Kg", bmi.getIs_Kg());
        startActivityForResult(intent, REQ_History);
    }
    @Override
    public void onDeleteClick(BMI bmi) {
        bmiSQLDao.deleteBMIData(bmi);
        bindToList();
    }

}
