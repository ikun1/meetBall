package com.example.user.templatedemo.Handlers;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

public abstract class SocketHandler extends Handler {
    /**
     * 请使用该类来实现响应socket结果以完成相应的UI更新
     */

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        JSONObject jsonObject;
        jsonObject = (JSONObject) msg.obj;

        switch (msg.what) {
            case SocketContact.CONNECT_FAILED:
                connect_failed();break;
            case SocketContact.MATCH:
                getMatchRe();break;



            default:
                break;
        }

    }

    public abstract void connect_failed();//连接失败响应
    public abstract void getMatchRe();//匹配返回信息响应

}
