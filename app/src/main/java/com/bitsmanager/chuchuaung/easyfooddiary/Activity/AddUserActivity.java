package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.UserSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.ViewUtility;

public class AddUserActivity extends AppCompatActivity {

    Button btnAddUserSave;
    int REQ_ADD_USER;
    ImageButton imgBtnMale, imgBtnFemale;
    private boolean is_male_checked = false, is_female_checked = false;
    UserSQLDao userSQLDao;
    EditText editname, editUsername, editPassword;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editname = (EditText) findViewById(R.id.editname);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

        userSQLDao = new UserSQLDao(this);

        btnAddUserSave = (Button) findViewById(R.id.btnAddUserSave);
        imgBtnMale = (ImageButton) findViewById(R.id.imgBtnMale);
        imgBtnFemale = (ImageButton) findViewById(R.id.imgBtnFemale);

        imgBtnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_male_checked) {
                    is_male_checked = false;
                } else {
                    is_male_checked = true;
                }
                setStateMale();
            }
        });

        imgBtnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_female_checked) {
                    is_female_checked = false;
                } else {
                    is_female_checked = true;
                }
                setStateFemale();
            }
        });


        btnAddUserSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editname.getText().toString().trim();
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (!is_female_checked && !is_male_checked) {
                    ViewUtility.showAlertDialog(AddUserActivity.this, "You need to select your gender.");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    editname.setError("Require!");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    editUsername.setError("Require!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editPassword.setError("Require!");
                    return;
                }

                User user = new User();
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);
                String gender;
                if (is_female_checked) {
                    gender = "Female";
                } else {
                    gender = "Male";
                }
                user.setGender(gender);
                long result = userSQLDao.insertUserData(user);
                if (result > 0) {
                    Utility.LOGIN_USER = userSQLDao.checkUserExit(username,password);
                    ViewUtility.showToast(AddUserActivity.this,"Registration successful.");
                    Intent intent= new Intent(AddUserActivity.this,MainActivity.class);
                    startActivityForResult(intent,REQ_ADD_USER);
                }

            }
        });
    }

    private void setStateFemale() {
        if (is_female_checked) {
            imgBtnFemale.setBackground(getResources().getDrawable(R.drawable.bg_checked_background));
            if (is_male_checked) {
                is_male_checked = false;
                imgBtnMale.setBackground(getResources().getDrawable(R.drawable.bg_unchecked_background));
            }
        } else {
            imgBtnFemale.setBackground(getResources().getDrawable(R.drawable.bg_unchecked_background));
        }
    }

    private void setStateMale() {
        if (is_male_checked) {
            imgBtnMale.setBackground(getResources().getDrawable(R.drawable.bg_checked_background));
            if (is_female_checked) {
                is_female_checked = false;
                imgBtnFemale.setBackground(getResources().getDrawable(R.drawable.bg_unchecked_background));
            }
        } else {
            imgBtnMale.setBackground(getResources().getDrawable(R.drawable.bg_unchecked_background));
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;

    }

}
