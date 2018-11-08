package com.example.wx.myapplication.shoushimima;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lib.shoushi.GestureEventListener;
import com.example.lib.shoushi.GestureLockViewGroup;
import com.example.lib.shoushi.GesturePasswordSettingListener;
import com.example.lib.shoushi.GestureUnmatchedExceedListener;
import com.example.wx.myapplication.R;

public class ShoushiMimaActivity extends AppCompatActivity {

    GestureLockViewGroup gesture_lock_group;
    boolean isReset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoushi_mima);
        gesture_lock_group = findViewById(R.id.gesture_lock_group);

        gesturePasswordSettingListener();// 设置手势密码
        gestureRetryLimitListener();//重试次数超过监听
        gestureEventListener();//设置手势密码监听事件
    }


    private void gesturePasswordSettingListener() {
        gesture_lock_group.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                if (len > 3) {
                    Toast.makeText(ShoushiMimaActivity.this, "再次绘制手势", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(ShoushiMimaActivity.this, "最少连接四个点", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            @Override
            public void onSuccess() {
                Toast.makeText(ShoushiMimaActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail() {
                Toast.makeText(ShoushiMimaActivity.this, "与上一次绘制不一致,请重新绘制", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gestureRetryLimitListener() {
        gesture_lock_group.setGestureUnmatchedExceedListener(3, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(ShoushiMimaActivity.this, "错误次数太多,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gestureEventListener() {
        gesture_lock_group.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (!matched) {
                    Toast.makeText(ShoushiMimaActivity.this, "手势密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    if (isReset) {
                        isReset = false;
                        Toast.makeText(ShoushiMimaActivity.this, "清除成功!", Toast.LENGTH_SHORT).show();
//                        resetGesturePattern();
                    } else {
                        Toast.makeText(ShoushiMimaActivity.this, "手势密码正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}
