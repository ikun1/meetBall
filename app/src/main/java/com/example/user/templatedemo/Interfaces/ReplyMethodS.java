package com.example.user.templatedemo.Interfaces;

import com.example.user.templatedemo.Domain.User;

import org.json.JSONObject;

public interface ReplyMethodS{
    void connect_failed();//连接失败响应
    void getInfomation(User user);
}
