package com.example.user.templatedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.Service.AccountService;

public class loginActivity extends Activity {
    int loginState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        TextView tvRegister =(TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user  = new User();
                EditText etUserName = (EditText) findViewById(R.id.et_user_name);
                EditText etPsw = (EditText) findViewById(R.id.et_psw);
                String userName = etUserName.getText().toString().trim();
                String psw = etPsw.getText().toString().trim();
                user.setUserName(userName);
                user.setPassword(psw);
                loginState = AccountService.getInstance().getLoginState(user);
                if (loginState == 1){
                    finish();
                }
            }
        });
    }
}
