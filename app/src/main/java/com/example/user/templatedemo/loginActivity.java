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

public class loginActivity extends Activity {
    int loginState;
    int registerState;
    //示范响应类，重写它的内部抽象方法可实现根据http返回值更新UI
    ProcessHandler proH = new ProcessHandler(){
        public void getLogInReturn(int result,String cookie){
            //TextView textView = (TextView) findViewById(R.id.firstPage);
            //textView.setText("cookie:"+  cookie);//cookie必须存起来
            MainActivity.cookie=cookie;
            loginState = result;
        }
        @Override
        public void getRegitserReturn(int result) {
            registerState = result;
        }
    };

    //示范请求类，用它发送请求，构造的时候把重写好的proH响应类传进去
    HttpContact httpC = new HttpContact(proH);
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
                httpC.logIn(user);
                System.out.println("当前登录状态"+loginState);
                if (loginState == 1){
                    finish();
                }
            }
        });
    }
}
