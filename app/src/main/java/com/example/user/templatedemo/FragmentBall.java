package com.example.user.templatedemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.templatedemo.Domain.Match;
import com.example.user.templatedemo.Service.SocketService;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.adapter.SimpleWheelAdapter;
import com.wx.wheelview.common.WheelData;
import com.wx.wheelview.util.WheelUtils;
import com.wx.wheelview.widget.WheelView;
import com.wx.wheelview.widget.WheelViewDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FragmentBall extends Fragment {
    private WheelView hourWheelView,minuteWheelView,secondWheelView;
    Date date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ball, container, false);
    }

    /**
     * holo皮肤
     */
    @Override
    public void onActivityCreated(Bundle bundle)
    {
        initWheel2();
        Button btnMatch = (Button) getView().findViewById(R.id.btn_match);
        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按下按钮，使用单例的service来发送请求
                Match match = new Match();
                int hour = hourWheelView.getSelection();
                int minutes = minuteWheelView.getSelection();
                int seconds = secondWheelView.getSelection();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateInString = "2019-6-11 " + hour +":"+ minutes + ":" + seconds;

                try {
                    date = formatter.parse(dateInString);
                    System.out.println(date);
                    System.out.println(formatter.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                match.setBeginTime(date);
                match.setEndTime(date);
                match.setLocation_lat(1.145f);
                match.setLocation_lng(1.1564f);
                match.setMethod(3);
                SocketService.getInstance().beginMatch(MainActivity.cookie,match);
            }
        });
        super.onActivityCreated(bundle);
    }

    private void initWheel2() {
        hourWheelView = (WheelView) getView().findViewById(R.id.hour_wheelview);
        hourWheelView.setWheelAdapter(new ArrayWheelAdapter(this.getActivity()));
        hourWheelView.setSkin(WheelView.Skin.Holo);
        hourWheelView.setWheelData(createHours());
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelView.setStyle(style);
        hourWheelView.setExtraText("时", Color.parseColor("#0288ce"), 40, 170);
        System.out.println("啊啊啊啊啊啊啊" + hourWheelView.getSelection());

        minuteWheelView = (WheelView) getView().findViewById(R.id.minute_wheelview);
        minuteWheelView.setWheelAdapter(new ArrayWheelAdapter(this.getActivity()));
        minuteWheelView.setSkin(WheelView.Skin.Holo);
        minuteWheelView.setWheelData(createMinutes());
        minuteWheelView.setStyle(style);
        minuteWheelView.setExtraText("分", Color.parseColor("#0288ce"), 40, 7);

        secondWheelView = (WheelView) getView().findViewById(R.id.second_wheelview);
        secondWheelView.setWheelAdapter(new ArrayWheelAdapter(this.getActivity()));
        secondWheelView.setSkin(WheelView.Skin.Holo);
        secondWheelView.setWheelData(createMinutes());
        secondWheelView.setStyle(style);
        secondWheelView.setExtraText("秒", Color.parseColor("#0288ce"), 40, 70);
    }


    private ArrayList<String> createHours() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    private ArrayList<String> createMinutes() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }
}
