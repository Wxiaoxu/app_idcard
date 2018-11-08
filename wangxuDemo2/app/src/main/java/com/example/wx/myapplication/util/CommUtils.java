package com.example.wx.myapplication.util;

import android.content.Context;
import android.widget.Toast;

public class CommUtils {


    public static  void onToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
