package com.ipusoft.xlibrary.debug;

import android.app.Application;

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.config.Env;

/**
 * author : GWFan
 * time   : 5/24/21 5:57 PM
 * desc   :
 */
public class MyApplication extends Application {
    public static Env env = Env.DEV;
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /**
         * 初始化SDK。 key，secret，username替换成在连连微号平台注册的key，secret和username
         */
        String key = "4571122846924808";
        String secret = "90237f5970f805250f07fef18fff45cb";
        String username = "17047151254";
        IpuSoftSDK.init(this, env, new AuthInfo(key, secret, username));

        /**
         * 注册通话状态的listener
         */
        IpuSoftSDK.registerPhoneStatusChangedListener(new OnPhoneStatusChangedListenerImpl());
    }
}
