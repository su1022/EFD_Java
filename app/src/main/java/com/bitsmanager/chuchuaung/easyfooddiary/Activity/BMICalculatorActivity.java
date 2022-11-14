package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.BmiSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.BMI;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;
import com.goodiebag.protractorview.ProtractorView;
import com.kyleduo.switchbutton.SwitchButton;
import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.marcinmoskala.arcseekbar.ProgressListener;

import java.text.DecimalFormat;


public class BMICalculatorActivity extends AppCompatActivity {

    SwitchButton weightBMI_switch_button;
    SwitchButton heightBMI_switch_button;
    TextView tvBMIWeightUnit;
    TextView tvBMIunit;
    TextView tvBMIunitInch, tv_bmi_result,tv_bmi_status,tv_lable_status,tv_lable_result;
    EditText editBMIWeight, editBMIHeight, editBMIinches;
    Button Bmi_Calculate;
    BMI bmi;
    BmiSQLDao bmiSQLDao;
    Button Bmi_History;
    int REQ_History;
    int is_Kg=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        weightBMI_switch_button = (SwitchButton) findViewById(R.id.BMISwitch_Weight);
        heightBMI_switch_button = (SwitchButton) findViewById(R.id.BMISwitch_Height);
        tvBMIWeightUnit = (TextView) findViewById(R.id.tvBMIWeightUnit);
        tvBMIunit = (TextView) findViewById(R.id.tvBMIunit);
        tvBMIunitInch = (TextView) findViewById(R.id.tvBMIunitInch);
        editBMIHeight = (EditText) findViewById(R.id.editBMIHeight);
        editBMIWeight = (EditText) findViewById(R.id.editBMIWeight);
        editBMIinches = (EditText) findViewById(R.id.editBMIinches);

        tv_bmi_result = (TextView) findViewById(R.id.tv_bmi_result);
        tv_bmi_status = (TextView) findViewById(R.id.tv_bmi_status);
        tv_lable_result = (TextView) findViewById(R.id.tv_lable_result);
        tv_lable_status = (TextView) findViewById(R.id.tv_lable_status);

        Bmi_Calculate = (Button) findViewById(R.id.Bmi_Calculate);

        tv_lable_result.setVisibility(View.INVISIBLE);
        tv_bmi_result.setVisibility(View.INVISIBLE);
        tv_lable_status.setVisibility(View.INVISIBLE);
        tv_bmi_status.setVisibility(View.INVISIBLE);

        Bmi_History = (Button)findViewById(R.id.Bmi_History);

        bmiSQLDao = new BmiSQLDao(this);
        bmi = (BMI) getIntent().getSerializableExtra("history");

        Bmi_Calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userWeight = editBMIWeight.getText().toString().trim();
                String userHeight = editBMIHeight.getText().toString().trim();
                int user_id = Utility.LOGIN_USER.getUser_id();
                String inches = editBMIinches.getText ().toString ().trim ();

                if (TextUtils.isEmpty (userHeight)) {
                    editBMIHeight.setError ("Require");
                    return;
                }

                if (!heightBMI_switch_button.isChecked () && TextUtils.isEmpty (inches)) {
                    editBMIinches.setError ("Require");
                    return;
                }

                double weight;
                if (weightBMI_switch_button.isChecked ()) {
                    weight = Double.parseDouble (userWeight) / 0.45;
                }else {
                    weight = Double.parseDouble (userWeight);
                }

                double height;
                if (heightBMI_switch_button.isChecked ()) {
                    height = Double.parseDouble (userHeight) / 2.54;
                }else {
                    height = Double.parseDouble (userHeight) * 12 + Double.parseDouble (inches);
                }

                if (!userWeight.isEmpty() && !userHeight.isEmpty()){
                    String bmiResult = String.valueOf (weight * 703 / (height * height));
                    String bmiStatus = bmiStatus (Double.parseDouble (String.valueOf (bmiResult)));
                    tv_bmi_result.setText (bmiResult);
                    tv_bmi_status.setText (bmiStatus);
                    tv_lable_result.setVisibility (View.VISIBLE);
                    tv_bmi_result.setVisibility (View.VISIBLE);
                    tv_lable_status.setVisibility (View.VISIBLE);
                    tv_bmi_status.setVisibility (View.VISIBLE);
                    BMI bmi = new BMI();
                    bmi.setBmi_weight(Double.parseDouble(userWeight));
                    bmi.setBmi_height(Double.parseDouble(userHeight));
                    bmi.setResult(Double.parseDouble(bmiResult));

                    /*User login_user = new User();
                    if (login_user == null){
                        Intent intent = new Intent();
                        intent.putExtra("user_Id", bmi);
                        bmi.setUser_id(user_id);
                    }else{
                        bmi.setUser_id(Integer.parseInt(String.valueOf(login_user)));
                    }*/
                    Intent intent = new Intent();
                    intent.putExtra(String.valueOf(Utility.LOGIN_USER), bmi.getUser_id());
                    setResult(RESULT_OK, intent);
                    bmi.setUser_id(user_id);
                    if(weightBMI_switch_button.isChecked()){
                        int is_Kg =1;
                        bmi.setIs_Kg(is_Kg);
                    }
                    if (heightBMI_switch_button.isChecked()) {
                        int is_cm = 1;
                        bmi.setIs_cm(is_cm);
                    }else{
                        bmi.setBmi_height(height);
                    }
                        bmiSQLDao.insertBMIData(bmi);
                    }
            }

        });

        Bmi_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BMICalculatorActivity.this, HistroyActivity.class);
                intent.putExtra("history", 1);
                intent.putExtra("BMI", bmi);
                startActivityForResult(intent, REQ_History);
            }
        });
        weightBMI_switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvBMIWeightUnit.setText("kg");
                } else {
                    tvBMIWeightUnit.setText("lb");
                }
            }
        });

        heightBMI_switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvBMIunit.setText("cm");
                    editBMIinches.setVisibility(View.GONE);
                    tvBMIunitInch.setVisibility(View.GONE);
                } else {
                    tvBMIunit.setText("ft");
                    tvBMIunitInch.setText("in");
                    editBMIinches.setVisibility(View.VISIBLE);
                    tvBMIunitInch.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public String bmiStatus (double bmi){

        String status;
        if (bmi < 18.5){
            status = "Underweight :(";
        }else if (bmi >= 18.5 && bmi <= 25){
            status = "Normal :)";
        }else if (bmi >= 25 && bmi <= 30){
            status = "Overweight :(";
        }else if (bmi >= 30){
            status = "Obese :(";
        }
        else {
            status = "Unknown";
        }
        return status;
    }
}
