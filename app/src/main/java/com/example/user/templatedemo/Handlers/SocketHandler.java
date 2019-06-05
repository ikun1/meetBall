package com.example.user.templatedemo.Handlers;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;


public  abstract class SocketHandler extends Handler {
    /**
     * 请使用该类来实现响应socket结果以完成相应的UI更新
     */



    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        JSONObject jsonObject = null;
        if(msg.obj!=null)
            jsonObject = (JSONObject) msg.obj;

        switch (msg.what) {
            case SocketContact.CONNECT_FAILED:
                connect_failed();break;

            default:
                getResult(msg.what,jsonObject);break;

        }

    }

    public abstract void connect_failed();//连接失败响应
    public abstract void getResult(int type,JSONObject jsonObject);//匹配返回信息响应,Type是常数，返回结果是jsonObject


}


