package com.example.user.templatedemo.Domain;

import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class Match {
    private int matchID;
    private String userName;
    private Date beginTime;
    private Date endTime;
    private float location_lat;
    private float location_lng;
    int method;


    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(float location_lat) {
        this.location_lat = location_lat;
    }

    public float getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(float location_lng) {
        this.location_lng = location_lng;
    }

    public static Match getFromJson(JSONObject jsonObject)//从Json转换获得一个实体
    {
        Match match = new Match();
        match.setBeginTime((Date)jsonObject.get("beginTime"));
        match.setEndTime((Date)jsonObject.get("endTime"));
        match.setLocation_lat((float)jsonObject.get("location_lat"));
        match.setLocation_lng((float)jsonObject.get("location_lng"));
        match.setMethod((int)jsonObject.get("method"));
        return match;
    }

    public String getJsonFrom(){//通过java反射机制把该类所有的属性全部转成json
        JSONObject returnJson = new JSONObject();
        returnJson.put("matchID",matchID);
        returnJson.put("method",method);
        returnJson.put("userName",userName);
        returnJson.put("location_lng",location_lng);
        returnJson.put("location_lat",location_lat);
        returnJson.put("beginTime",beginTime);
        returnJson.put("endTime",endTime);
        return returnJson.toString();

    }


}
