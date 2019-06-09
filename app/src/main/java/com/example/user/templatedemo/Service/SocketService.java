package com.example.user.templatedemo.Service;

import com.example.user.templatedemo.Domain.Match;
import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.SocketContact;
import com.example.user.templatedemo.Handlers.SocketHandler;
import com.example.user.templatedemo.Interfaces.ReplyMethodS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                    System.out.println(jsonObject.toString());
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


            socketContact.sendMessage("<getInfo>" + fetch + jsonObject.toString() + fetch + "</getInfo>");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void client (String cookie){
        try {
            //传入昵称，获取个人信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cookie", cookie);


            socketContact.sendMessage("<client>" + fetch + jsonObject.toString() + fetch + "</client>");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void askMatchInfo (String matchID){
        try {
            //传入matchID,获取Match的全部信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("matchID", matchID);


            socketContact.sendMessage("<getMatchInfo>" + fetch + jsonObject.toString() + fetch + "</getMatchInfo>");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void beginMatch (String cookie,Match match){
        try {
            //传入除id外的Match信息，开始匹配
            JSONObject jsonObject = new JSONObject();
            Gson gson = new Gson();
            String matchStr = gson.toJson(match);

            jsonObject.put("cookie", cookie);
            jsonObject.put("match",matchStr);


            socketContact.sendMessage("<match>" + fetch + jsonObject.toString() + fetch + "</match>");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void processResult(int type, JSONObject jsonObject){
        switch (type){
            case SocketContact.GETINFO:getInformation(jsonObject);break;
            case SocketContact.MATCH:getMatchRe(jsonObject);break;
            case SocketContact.MATCHINFO:getMatchInfo(jsonObject);break;

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
    private void getMatchRe(JSONObject jsonObject){
        try {
            int result = (int)jsonObject.get("result");
            List<String> userNames = new ArrayList<String>();
            int MatchID = (int)jsonObject.get("matchID");


            JSONArray array = (JSONArray)jsonObject.get("userName");
            for(int i = 0;i<array.length();i++)
            {
                userNames.add((String)array.get(i));
            }

            replyMethodS.getMatchResult(result,userNames,MatchID);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void getMatchInfo(JSONObject jsonObject){
        try {
            JSONObject matchJson = (JSONObject) jsonObject.get("match");
            Gson gson = new Gson();
            Match match = gson.fromJson(matchJson.toString(),new TypeToken<Match>(){}.getType());
            //使用gson进行Bean强转
            replyMethodS.getMatchInfo(match);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
