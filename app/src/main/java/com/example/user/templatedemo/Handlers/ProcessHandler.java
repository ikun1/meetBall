package com.example.user.templatedemo.Handlers;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;


public abstract class ProcessHandler extends Handler {
    //请使用该类来实现响应http以完成相应的UI更新

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case HttpContact.LOGIN_RESULT:
                JSONObject jsonObject = (JSONObject) msg.obj;
                Login_result(jsonObject);
                break;

            default:
                break;
        }
    }

    private void Login_result(JSONObject jsonObject){
        try {
            int result = (int) jsonObject.get("result");
            String cookie = (String) jsonObject.get("cookie");
            getLogInReturn(result, cookie);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public abstract void getLogInReturn(int result,String cookie);
    //该方法进行登录之后的UI更新，传入值分别为result返回结果，cookie返回的cookie值，需要保存(其他的返回结果含义可以参照数据交换格式文档)


}
