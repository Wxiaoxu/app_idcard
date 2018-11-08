package com.example.lib.shoushi;

public interface GesturePasswordSettingListener {

    /**
     * 手势密码设置监听
     * @param len
     * @return
     */
    boolean onFirstInputComplete(int len);
    void onSuccess();
    void onFail();
}
