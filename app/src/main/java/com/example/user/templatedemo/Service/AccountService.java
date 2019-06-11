package com.example.user.templatedemo.Service;

import android.os.Handler;
import android.os.Message;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.MainActivity;
import com.example.user.templatedemo.loginActivity;
import com.example.user.templatedemo.registerActivity;

public class AccountService {
    int loginState;
    int registerState;
    HttpContact httpC;
    loginActivity react;
    registerActivity registerActivity1;
    private static AccountService accountService;

    public static int LOGIN = 1;

    public AccountService(){
        //示范响应类，重写它的内部抽象方法可实现根据http返回值更新UI
        ProcessHandler proH = new ProcessHandler(){
            public void getLogInReturn(int result,String cookie){
                //TextView textView = (TextView) findViewById(R.id.firstPage);
                //textView.setText("cookie:"+  cookie);//cookie必须存起来
                MainActivity.cookie=cookie;
                loginState = result;
                SocketService.getInstance().client(cookie);
                react.reactLogin(result);

            }
            @Override
            public void getRegitserReturn(int result) {
                registerActivity1.finishRegister(result);

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

    public void getLoginState(User user, loginActivity react){
        httpC.logIn(user);
        this.react = react;
    }

    public void sendRegister(User user,registerActivity registerActivity1)
    {
        httpC.register(user);
        this.registerActivity1 = registerActivity1;
    }

    public int getRegisterState(){
        return registerState;
    }

}
