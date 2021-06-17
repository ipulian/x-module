package com.ipusoft.xphone.debug;

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
    public static String env = Env.OPEN_DEV;
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /**
         * 初始化SDK。 key，secret，username替换成在连连微号平台注册的key，secret和username
         */
        String key = "4571122846924808";
        String secret = "6d8f5dff290bac1dd92708b638046125";
        String username = "17047151254";
        IpuSoftSDK.init(this, env, new AuthInfo(key, secret, username));

        /**
         * 注册通话状态的listener
         */
        IpuSoftSDK.registerPhoneStatusChangedListener(new OnPhoneStatusChangedListenerImpl());
    }
}
