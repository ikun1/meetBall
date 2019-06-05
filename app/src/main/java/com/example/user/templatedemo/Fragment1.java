package com.example.user.templatedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.Service.SocketService;

public class Fragment1 extends Fragment {
    @Nullable

//示范响应类，重写它的内部抽象方法可实现根据http返回值更新UI
    ProcessHandler proH = new ProcessHandler(){
        public  void getLogInReturn(int result,String cookie){
            TextView textView = (TextView) getView().findViewById(R.id.firstPage);
            textView.setText("cookie:"+  cookie);
        }
        @Override
        public void getRegitserReturn(int result) {
        }
    };

    //示范请求类，用它发送请求，构造的时候把重写好的proH响应类传进去
    HttpContact httpC = new HttpContact(proH);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle)
    {
        //测试按钮，主要是示范一哈网络模块的用法
        Button testButton = (Button) getView().findViewById(R.id.button);
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User user  = new User();
                user.setUserName("xiayu");
                user.setPassword("853172836");
                httpC.logIn(user);
            }
        });

        Button testButton2 = (Button) getView().findViewById(R.id.button2);
        testButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //按下按钮，使用单例的service来发送请求
                SocketService.getInstance().askInfomation("xiayu");
            }
        });


        super.onActivityCreated(bundle);
    }

    public void reactInfo(User user)
    //响应信息获取方法，被MainActivity中的service调用
    {
        TextView textView = (TextView) getView().findViewById(R.id.firstPage);
        textView.setText("用户id:"+  user.getUserName());
    }



}
