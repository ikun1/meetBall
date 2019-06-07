package com.example.user.templatedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_splash);
        Thread myThread = new Thread(){//创建子线程
            @Override
            public void run(){
                try {
                    sleep(3000);
                    Intent it = new Intent(getApplicationContext(),MainActivity.class);//启动MainActivity
                    startActivity(it);
                    if (true){
                        //加入是否首次登录的判断
                        Intent intent = new Intent(Splash.this, loginActivity.class);
                        startActivity(intent);
                    }

                    //finish();关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
