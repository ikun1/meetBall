package com.example.user.templatedemo.Service;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.MainActivity;

public class AccountService {
    int loginState;
    int registerState;
    HttpContact httpC;
    private static AccountService accountService;

    public AccountService(){
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
        this.httpC = new HttpContact(proH);
        accountService = this;
    }

    public static AccountService getInstance(){
        //保持单例模式
        return accountService;
    }

    public int getLoginState(User user){
        httpC.logIn(user);
        System.out.println("结果为:" + loginState);
        return loginState;
    }

    public int getRegisterState(){
        return registerState;
    }

}
