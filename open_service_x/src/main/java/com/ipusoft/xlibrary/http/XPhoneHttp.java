package com.ipusoft.xlibrary.http;

import android.util.Log;

import com.ipusoft.xlibrary.XModuleApp;
import com.ipusoft.xlibrary.bean.VirtualNumber;
import com.ipusoft.xlibrary.iface.QueryXNumberListener;
import com.ipusoft.xlibrary.module.XService;
import com.ipusoft.xlibrary.utils.XStringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * author : GWFan
 * time   : 5/17/21 5:05 PM
 * desc   :
 */

public class XPhoneHttp {
    private static final String TAG = "XPhoneHttp";
    private static final String TOKEN = "token";
    private static final String PHONE = "phone";

    /**
     * 小号外呼 取号
     *
     * @param phone 联系人真实号码
     */
    public static void queryXPhone(String phone, Observer<VirtualNumber> observe) throws Exception {
        Map<String, Object> params = checkToken(phone);
        XService.Companion.callPhone(params, observe);
    }

    public static void queryXPhone(String phone, QueryXNumberListener listener) throws Exception {
        Map<String, Object> params = checkToken(phone);
        XService.Companion.callPhone(params, new Observer<VirtualNumber>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull VirtualNumber result) {
                if (listener != null) {
                    listener.onQueryListener(result);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 验证Token
     *
     * @param phone
     * @return
     * @throws Exception
     */
    private static Map<String, Object> checkToken(String phone) throws Exception {
        String token = XModuleApp.token;
        if (XStringUtils.isEmpty(token)) {
            Log.d(TAG, "checkToken: token认证失败,请尝试重新初始化SDK");
            throw new Exception("token认证失败,请尝试重新初始化SDK");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(TOKEN, token);
        map.put(PHONE, phone);
        return map;
    }
}
