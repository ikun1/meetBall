package com.example.user.templatedemo.Domain;



import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Match {
    private int matchID;
    private String userName;
    private float location_lat;
    private float location_lng;
    private String beginStr;
    private String endStr;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(beginStr, pos);
        return strtodate;
    }

    public void setBeginTime(Date beginTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(beginTime);
        beginStr = dateString;
    }

    public Date getEndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(endStr, pos);
        return strtodate;
    }

    public void setEndTime(Date endTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(endTime);
        endStr = dateString;
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


    public String getBeginStr() {
        return beginStr;
    }

    public void setBeginStr(String beginStr) {
        this.beginStr = beginStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }







}
