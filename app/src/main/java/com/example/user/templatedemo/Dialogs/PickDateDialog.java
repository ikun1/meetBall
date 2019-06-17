package com.example.user.templatedemo.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.user.templatedemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PickDateDialog extends Dialog {
    private Button confirm;
    private DatePicker datePicker;
    public Date date;
    public onConfrimInterface react;

    public PickDateDialog(Context context, int width, int height, View layout, int style,Date date) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);

        this.date = date;
    }

    public void setReact(onConfrimInterface react){
        this.react = react;
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        datePicker = findViewById(R.id.datePicker);
        confirm = findViewById(R.id.confirmPicker);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                react.onConfrim(date);
                cancel();
            }
        });

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                // 获取一个日历对象，并初始化为当前选中的时间
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy年MM月dd日");
                date = calendar.getTime();
            }
        });


    }

    public interface onConfrimInterface{
        void onConfrim(Date pickedDate);
    }


}
