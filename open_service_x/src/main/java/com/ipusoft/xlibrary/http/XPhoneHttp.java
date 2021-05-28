package com.ipusoft.xlibrary.http;

import android.util.Log;
import android.widget.Toast;

import com.ipusoft.context.IActivityLifecycle;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.OnSDKLoginListener;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.utils.GsonUtils;
import com.ipusoft.context.utils.MD5Utils;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.xlibrary.bean.BindingInfo;
import com.ipusoft.xlibrary.constant.Constant;
import com.ipusoft.xlibrary.constant.HttpStatus;
import com.ipusoft.xlibrary.iface.QueryXNumberListener;
import com.ipusoft.xlibrary.module.XService;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;

/**
 * author : GWFan
 * time   : 5/17/21 5:05 PM
 * desc   :
 */

public class XPhoneHttp {
    private static final String TAG = "XPhoneHttp";

    /**
     * 小号外呼 取号
     *
     * @param phone 联系人真实号码
     */
    public static void queryXPhone(String phone, Observer<BindingInfo> observe) {
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(IpuSoftSDK.getAppContext(), "号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        checkToken(phone, observe);
    }

    public static void queryXPhone(String phone, QueryXNumberListener listener) {
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(IpuSoftSDK.getAppContext(), "号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        checkToken(phone, listener);
    }

    /**
     * 验证Token
     *
     * @param phone
     * @return
     */
    private static void checkToken(String phone, Observer<BindingInfo> observer) {
        if (StringUtils.isEmpty(IpuSoftSDK.token)) {
            Log.d(Constant.TAG, "checkToken: token认证失败,请尝试重新初始化SDK");
            IpuSoftSDK.reLogin(status -> {
                if (OnSDKLoginListener.LoginStatus.SUCCESS == status) {
                    callPhone(phone, observer);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), "SDK初始化失败", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "checkToken: reLogin失败");
                }
            });
        } else {
            callPhone(phone, observer);
        }
    }

    private static void checkToken(String phone, QueryXNumberListener listener) {
        if (StringUtils.isEmpty(IpuSoftSDK.token)) {
            Log.d(Constant.TAG, "checkToken: token认证失败,请尝试重新初始化SDK");
            IpuSoftSDK.reLogin(status -> {
                if (OnSDKLoginListener.LoginStatus.SUCCESS == status) {
                    callPhone(phone, listener);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), "SDK初始化失败", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "checkToken: reLogin失败");
                }
            });
        } else {
            callPhone(phone, listener);
        }
    }

    private static Map<String, Object> getParams(String phone) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("calledNo", phone);//被叫号码
        params.put("ts", getSecondTimestamp(new Date()));//时间戳
        IAuthInfo authInfo = IpuSoftSDK.getAuthInfo();
        String username = "";
        if (authInfo != null) {
            username = authInfo.getUsername();
        }
        params.put("callerId", username);//坐席编号

        Log.d(TAG, "getParams: ----->" + GsonUtils.toJson(params));
        return params;
    }

    private static void callPhone(String phone, Observer<BindingInfo> observer) {
        IAuthInfo authInfo = IpuSoftSDK.getAuthInfo();
        if (authInfo != null) {
            XService.Companion.callPhone(authInfo.getKey(),
                    getSign(GsonUtils.toJson(getParams(phone))), getParams(phone), observer);
        } else {
            Toast.makeText(IActivityLifecycle.getCurrentActivity(), "认证失败", Toast.LENGTH_SHORT).show();
        }
    }

    private static void callPhone(String phone, QueryXNumberListener listener) {
        IAuthInfo authInfo = IpuSoftSDK.getAuthInfo();
        if (authInfo != null) {
            XService.Companion.callPhone(authInfo.getKey(),
                    getSign(GsonUtils.toJson(getParams(phone))), getParams(phone), new IObserver<BindingInfo>() {
                        @Override
                        public void onNext(@NotNull BindingInfo result) {
                            if (listener != null) {
                                listener.onQueryListener(result);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(Constant.TAG, "XPhoneHttp->onError: " + e.toString());
                            if (listener != null) {
                                BindingInfo bindingInfo = new BindingInfo();
                                bindingInfo.setCode(HttpStatus.CODE_ERROR_OTHER);
                                bindingInfo.setMessage("取号出错：" + e.toString());
                                listener.onQueryListener(bindingInfo);
                            }
                        }
                    });
        } else {
            Toast.makeText(IActivityLifecycle.getCurrentActivity(), "认证失败", Toast.LENGTH_SHORT).show();
        }
    }

    private static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }

    private static String getSign(String paramsJson) {
        return MD5Utils.getMD5(paramsJson + IpuSoftSDK.getAuthInfo().getSecret());
    }
}
