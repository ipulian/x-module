package com.ipusoft.xlibrary.debug;

import android.app.Application;

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.bean.IAuthInfo;

/**
 * author : GWFan
 * time   : 5/24/21 5:57 PM
 * desc   :
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化SDK。 key，secret，username替换成在连连微号平台注册的key，secret和username
         */
        String key = "4571122846924808";
        String secret = "90237f5970f805250f07fef18fff45cb";
        String username = "17047151254";
        IpuSoftSDK.init(this, new IAuthInfo(key, secret, username));
    }
}
