package com.example.user.templatedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.Service.AccountService;

public class registerActivity extends Activity {
    int registerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        Button btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user  = new User();
                EditText etUserName = (EditText) findViewById(R.id.et_username_register);
                EditText etPsw = (EditText) findViewById(R.id.et_psw_register);
                EditText etHeight = (EditText) findViewById(R.id.et_height);
                EditText etAge = (EditText) findViewById(R.id.et_age);
                EditText etPosition = (EditText) findViewById(R.id.et_position);
                String userName = etUserName.getText().toString().trim();
                String psw = etPsw.getText().toString().trim();
                int position = Integer.parseInt(etPosition.getText().toString().trim());
                int height = Integer.parseInt(etHeight.getText().toString().trim());
                int age = Integer.parseInt(etAge.getText().toString().trim());
                user.setUserName(userName);
                user.setPassword(psw);
                user.setRole(position);
                user.setHeight(height);
                user.setAge(age);
                registerState = AccountService.getInstance().getRegisterState();
                System.out.println("注册状态"+registerState);
                if (registerState == 1){
                    finish();
                }
            }
        });
    }
}
