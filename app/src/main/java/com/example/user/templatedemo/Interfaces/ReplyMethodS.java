package com.example.user.templatedemo.Interfaces;

import com.example.user.templatedemo.Domain.Match;
import com.example.user.templatedemo.Domain.User;

import org.json.JSONObject;

import java.util.List;

public interface ReplyMethodS{
    void connect_failed();//连接失败响应
    void getInfomation(User user);//获取到用户信息响应
    void getMatchResult(int result, List<String> userNames, int matchID);//获取到匹配响应结果触发
    void getMatchInfo(Match match);//从匹配的id获取到匹配信息的触发

}
