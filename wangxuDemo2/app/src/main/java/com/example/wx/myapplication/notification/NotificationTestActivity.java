package com.example.wx.myapplication.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wx.myapplication.R;

public class NotificationTestActivity extends AppCompatActivity implements View.OnClickListener {

    Button send_chatmsg, send_subcribe_msg;
    String msg;
    String msgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            msg = "chat";
            msgName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            onCreatNotification(msg, msgName, importance);


            msg = "subscribe";
            msgName = "点阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            onCreatNotification(msg, msgName, importance);
        }
        initData();
        initListener();
    }


    private void initData() {
        send_chatmsg = findViewById(R.id.send_chatmsg);
        send_subcribe_msg = findViewById(R.id.send_subcribe_msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onCreatNotification(String msg, String msgName, int importance) {
        NotificationChannel channel = new NotificationChannel(msg, msgName, importance);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    private void initListener() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.send_chatmsg:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this, "chat")
                        .setContentTitle("收到一条消息")
                        .setContentText("今天中午吃什么？")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .build();

                manager.notify(1, notification);
                break;
            case R.id.send_subcribe_msg:
                NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification build = new NotificationCompat.Builder(this, "subscribe")
                        .setContentTitle("收到一条订阅消息")
                        .setContentText("地铁沿线30万商铺抢购中")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .build();
                manager1.notify(2, build);
                break;
        }
    }
}
