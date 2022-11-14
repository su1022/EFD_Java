package com.bitsmanager.chuchuaung.easyfooddiary.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.BaseDao;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.UserSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.ViewUtility;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnregister;
    int REQ_Btn_Login;
    int REQ_Btn_Register;
    EditText editLoginName;
    EditText editLoginPassword;
    private UserSQLDao userSQLDao;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (REQ_Btn_Login == requestCode){
            recreate ();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!BaseDao.checkDatabase()) {
            BaseDao.copyDatabase(this);
        }
        userSQLDao = new UserSQLDao(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnregister = (Button) findViewById(R.id.btnregister);

        editLoginName=(EditText)findViewById(R.id.editname);
        editLoginPassword=(EditText)findViewById(R.id.editPassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editLoginName.getText().toString().trim();
                String password = editLoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    editLoginName.setError("Require!");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    editLoginPassword.setError("Require!");
                    return;
                }

                User login_user = userSQLDao.checkUserExit(name, password);
                if (login_user != null) {
                    Utility.LOGIN_USER = login_user;
                    ViewUtility.showToast(LoginActivity.this,"Login successful");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivityForResult(intent, REQ_Btn_Login);
                }else {
                    ViewUtility.showAlertDialog(LoginActivity.this,"Your username and password is incorrect.");
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivityForResult(intent, REQ_Btn_Register);
            }
        });


    }
}
