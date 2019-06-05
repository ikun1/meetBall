package com.example.user.templatedemo.Service;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.SocketContact;
import com.example.user.templatedemo.Handlers.SocketHandler;
import com.example.user.templatedemo.Interfaces.ReplyMethodS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public  class SocketService {
    //socket整体封装处理类，请注意保持单例
    private SocketContact socketContact;
    private SocketHandler socketHandler;
    private ReplyMethodS replyMethodS;
    private static SocketService socketService;
    private static String fetch = System.getProperty("line.separator");

    public void ResetMethod(ReplyMethodS replyMethodS){
        //特殊情况会用到，重构响应，用于发送消息前临时重构响应
        this.replyMethodS = replyMethodS;
    }

    public SocketService(final ReplyMethodS replyMethodS){
        //构造方法，传入已经写好的接口操作
        this.replyMethodS = replyMethodS;
            socketHandler = new SocketHandler() {
                @Override
                public void connect_failed() {
                    replyMethodS.connect_failed();
                }

                @Override
                public void getResult(int type, JSONObject jsonObject) {
                    processResult(type, jsonObject);
                }
            };
            socketContact = new SocketContact(socketHandler);
            socketContact.Connect();
            socketService = this;
        }

    public static SocketService getInstance(){
        //由于socket链接的特殊性，用该方法保持单例模式
        return socketService;
    }

    public void askInfomation (String userName){
        try {
            //传入昵称，获取个人信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", userName);

            System.out.println(jsonObject.toString());

            socketContact.sendMessage("<getInfo>" + fetch + jsonObject.toString() + fetch + "</getInfo>");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void processResult(int type, JSONObject jsonObject){
        switch (type){
            case SocketContact.GETINFO:getInformation(jsonObject);break;
        }

    }

    private void getInformation(JSONObject jsonObject){
        try {
            JSONObject userJson = (JSONObject) jsonObject.get("user");
            Gson gson = new Gson();
            User user = gson.fromJson(userJson.toString(),new TypeToken<User>(){}.getType());
            //使用gson进行Bean强转
            replyMethodS.getInfomation(user);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
