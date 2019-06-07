package com.example.user.templatedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;

public class registerActivity extends Activity {
    int registerState;
    //示范响应类，重写它的内部抽象方法可实现根据http返回值更新UI
    ProcessHandler proH = new ProcessHandler(){
        public void getLogInReturn(int result,String cookie){
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
        setContentView(R.layout.register_main);
        Button btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
