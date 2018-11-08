package com.example.wx.myapplication.zhiwen;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wx.myapplication.R;
import com.example.wx.myapplication.util.CommUtils;
import com.example.wx.myapplication.util.FingerUtil;
import com.example.wx.myapplication.util.FingerprintUtil;

public class ZhiWenActivity extends AppCompatActivity   {

    private Button btn_zhiwen;

    private FingerUtil fingerUtil;
    private FingerprintManagerCompat.AuthenticationCallback mFingerListen;

    FingerprintUtil fingerprintUtil;
    FingerprintUtil.IFingerprintResultListener listener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_wen);

        btn_zhiwen = findViewById(R.id.btn_zhiwen);
        initView();
        inintData();

    }

    private void inintData() {
        listener = new FingerprintUtil.IFingerprintResultListener(){
            @Override
            public void onInSecurity() {
                //判断是否开启锁屏密码
                CommUtils.onToast(ZhiWenActivity.this,"判断是否开启锁屏密码");
            }

            @Override
            public void onNoEnroll() {
                //是否录入指纹，有些设备上即使录入了指纹，但是没有开启锁屏密码的话此方法还是返回false
                CommUtils.onToast(ZhiWenActivity.this,"onNoEnroll");
            }

            @Override
            public void onSupport() {
                //监听事件空了
                CommUtils.onToast(ZhiWenActivity.this,"onSupport");
            }

            @Override
            public void onAuthenticateStart() {
                //监听事件空了
                CommUtils.onToast(ZhiWenActivity.this,"onAuthenticateStart");
            }

            @Override
            public void onAuthenticateError(int errMsgId, CharSequence errString) {
//多次尝试都失败会走onAuthenticationError，会停止响应一段时间，提示尝试次数过多，请稍后再试。
                CommUtils.onToast(ZhiWenActivity.this,"onAuthenticateError");
            }

            @Override
            public void onAuthenticateFailed() {
//指纹验证失败走此方法，例如小米前4次验证失败走onAuthenticationFailed,第5次走onAuthenticationError
                CommUtils.onToast(ZhiWenActivity.this,"onAuthenticateFailed");
            }

            @Override
            public void onAuthenticateHelp(int helpMsgId, CharSequence helpString) {
                CommUtils.onToast(ZhiWenActivity.this,"onAuthenticateHelp");
            }

            @Override
            public void onAuthenticateSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
//当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
                CommUtils.onToast(ZhiWenActivity.this,"成功");
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        btn_zhiwen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                fingerprintUtil.callFingerPrintVerify(listener );
            }
        });
//        if (checkFingerMoudle()) {
//            fingerUtil = new FingerUtil(this);
//            fingerUtil.startFingerListen(mFingerListen);
//            initListener();
//        } else {
//            Toast.makeText(this, "不可指纹识别", Toast.LENGTH_SHORT).show();
//        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListener() {
//        mFingerListen = new FingerprintManagerCompat.AuthenticationCallback() {
//            //成功
//            @Override
//            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
//                super.onAuthenticationSucceeded(result);
//                btn_zhiwen.setText("指纹识别成功");
//            }
//
//            //失败
//            @Override
//            public void onAuthenticationFailed() {
//                super.onAuthenticationFailed();
//                btn_zhiwen.setText("指纹识别失败");
//            }
//
//            @Override
//            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
//                super.onAuthenticationHelp(helpMsgId, helpString);
//                if (btn_zhiwen.getTag() != null && false == (boolean) btn_zhiwen.getTag()) {
//                    return;
//                }
//
//                switch (helpMsgId) {
//                    case 1001:
//                        btn_zhiwen.setText("请按下手指");
//                        break;
//                    case 1002:
//                        btn_zhiwen.setText("正着识别");
//                        break;
//                    case 1003:
//                        btn_zhiwen.setText("手指抬起,请重新按下");
//                        break;
//                }
//            }
//
//            /**
//             * 多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
//             * @param errMsgId 错误码
//             * @param errString 剩余禁用时间
//             */
//            @Override
//            public void onAuthenticationError(int errMsgId, CharSequence errString) {
//                switch (errMsgId) {
//                    case 5:      // 可以进行识别
//                        btn_zhiwen.setTag(true);
//                        break;
//                    case 7:      // 失败次数过多，禁用倒计时未结束
//                        btn_zhiwen.setText("失败次数过多！请" + errString + "秒后再试");
//                        break;
//                }
//            }
//        };
    }

    /**
     * 检查是否有指纹模块
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkFingerMoudle() {
        try {
            FingerprintManager manager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            return manager.isHardwareDetected();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fingerUtil != null) {
            fingerUtil.stopFingerListen();
        }
    }


}
