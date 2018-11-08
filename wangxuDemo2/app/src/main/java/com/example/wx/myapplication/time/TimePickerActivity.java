package com.example.wx.myapplication.time;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lib.shoushi.widget.CompatResourceUtils;
import com.example.lib.shoushi.widget.DateTimePicker;
import com.example.wx.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerActivity extends AppCompatActivity {

    private TextView currentDate, currentTime;
    private DateTimePicker dateTimePicker1;
    private DateTimePicker dateTimePicker2;

    SimpleDateFormat format1, format2;
    Date date1, date2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        currentDate = findViewById(R.id.currentDate);
        currentTime = findViewById(R.id.currentTime);
        initDatePicker();
    }




    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                dateTimePicker1.show(date1);
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                dateTimePicker2.show(date2);
                break;
        }
    }

    private void initDatePicker() {
        format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        format2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        date2 = date1 = calendar.getTime();
        currentDate.setText(format1.format(date1));
        currentTime.setText(format2.format(date1));

        calendar.set(Calendar.YEAR, 2099);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 8);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date endDate = calendar.getTime();

        DateTimePicker.Builder builder = new DateTimePicker.Builder(this)
                .setTitle("选择时间")
                .setCancelTextColor(Color.RED)
                .setOkTextColor(CompatResourceUtils.getColor(this, R.color.colorPrimary))
                .setTitleTextColor(0xFF999999)
                .setSelectedTextColor(CompatResourceUtils.getColor(this, R.color.colorAccent))
                .setKeepLastSelected(true)
                .setShowYMDHMLabel(false)
                .setShowType(DateTimePicker.ShowType.DAY);
        dateTimePicker1 = new DateTimePicker(this, new DateTimePicker.ResultHandler() {
            @Override
            public void handle(Date date) {
                TimePickerActivity.this.date1 = date;
                currentDate.setText(format1.format(date));
            }
        }, startDate, endDate, builder);
        //方式二：使用默认的builder
//        dateTimePicker1 = new DateTimePicker(this, new DateTimePicker.ResultHandler() {
//            @Override
//            public void handle(Date date) {
//                TimePickerActivity.this.date1 = date;
//                currentDate.setText(format1.format(date));
//            }
//        }, startDate, endDate);

        dateTimePicker2 = new DateTimePicker(this, new DateTimePicker.ResultHandler() {
            @Override
            public void handle(Date date) {
                date2 = date;
                currentTime.setText(format2.format(date));
            }
        }, startDate, endDate, new DateTimePicker.Builder(this).setLoopScroll(true));
    }
}
