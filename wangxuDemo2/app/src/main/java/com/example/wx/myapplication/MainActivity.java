package com.example.wx.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wx.myapplication.danmu.DanMuActivity;
import com.example.wx.myapplication.notification.NotificationTestActivity;
import com.example.wx.myapplication.shoushimima.ShoushiMimaActivity;
import com.example.wx.myapplication.time.TimePickerActivity;
import com.example.wx.myapplication.util.ImageLoadUtil;
import com.example.wx.myapplication.zhiwen.ZhiWenActivity;

public class MainActivity extends AppCompatActivity {

    Button bt_img, bt_img_gif, bt_recycle, bt_view, bt_notification, bt_danmu, bt_shoushi, bt_zhiwen, bt_time;
    ImageView iv_img;
    GroupImageTextLayout personal_authentication_id_card_front_Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_img = (Button) findViewById(R.id.bt_img);
        bt_img_gif = (Button) findViewById(R.id.bt_img_gif);
        iv_img = findViewById(R.id.iv_img);
        bt_recycle = findViewById(R.id.bt_recycle);
        bt_view = findViewById(R.id.bt_view);
        bt_notification = findViewById(R.id.bt_notification);
        bt_danmu = findViewById(R.id.bt_danmu);
        bt_shoushi = findViewById(R.id.bt_shoushi);
        bt_zhiwen = findViewById(R.id.bt_zhiwen);
        bt_time = findViewById(R.id.bt_time);
        personal_authentication_id_card_front_Layout = findViewById(R.id.personal_authentication_id_card_front_Layout);
        initListener();
        initData();
    }

    private void initData() {

    }

    private void initListener() {
        bt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoadUtil.loadImageWithDefault(iv_img, "https://static.zcool.cn/v45.05.28.04-v18-05-21-12/special-resource/category-people/assets/img/2.png");
            }
        });

        bt_img_gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoadUtil.loadImage(iv_img, "http://storage.slide.news.sina.com.cn/slidenews/77_ori/2018_31/74766_830920_110658.gif");
            }
        });
        bt_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        bt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
            }
        });
        bt_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotificationTestActivity.class);
                startActivity(intent);
            }
        });
        bt_danmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DanMuActivity.class);
                startActivity(intent);
            }
        });
        bt_shoushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoushiMimaActivity.class);
                startActivity(intent);
            }
        });
        bt_zhiwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ZhiWenActivity.class);
                startActivity(intent);
            }
        });
        bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
                startActivity(intent);
            }
        });
    }
}
