package com.ipusoft.xphone.http;

import android.util.Log;
import android.widget.Toast;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.OnSDKLoginListener;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.manager.PhoneManager;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.MD5Utils;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.xphone.bean.BindingInfo;
import com.ipusoft.xphone.constant.Constant;
import com.ipusoft.xphone.constant.HttpStatus;
import com.ipusoft.xphone.listener.QueryXNumberListener;
import com.ipusoft.xphone.module.XService;

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

    public static void queryXPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(IpuSoftSDK.getAppContext(), "号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        checkToken(phone);
    }

    /**
     * 发送短信
     *
     * @param phone
     * @param content
     */
    public static void sendMsgByXPhone(String phone, String content) {
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(IpuSoftSDK.getAppContext(), "号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        checkToken(phone, content);
    }

    public static void sendMsgByXPhone(String phone) {
        sendMsgByXPhone(phone, "");
    }

    /**
     * 验证Token
     *
     * @param phone
     * @return
     */

    private static void checkToken(String phone) {
        if (StringUtils.isEmpty(AppContext.getToken())) {
            Log.d(Constant.TAG, "checkToken: token认证失败,请尝试重新初始化SDK");
            IpuSoftSDK.reLogin(status -> {
                if (OnSDKLoginListener.LoginStatus.SUCCESS == status) {
                    callPhone(phone);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), "SDK初始化失败", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "checkToken: reLogin失败");
                }
            });
        } else {
            callPhone(phone);
        }
    }

    private static void checkToken(String phone, String content) {
        if (StringUtils.isEmpty(AppContext.getToken())) {
            Log.d(Constant.TAG, "checkToken: token认证失败,请尝试重新初始化SDK");
            IpuSoftSDK.reLogin(status -> {
                if (OnSDKLoginListener.LoginStatus.SUCCESS == status) {
                    callPhone(phone, content);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), "SDK初始化失败", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "checkToken: reLogin失败");
                }
            });
        } else {
            callPhone(phone, content);
        }
    }

    private static void checkToken(String phone, Observer<BindingInfo> observer) {
        if (StringUtils.isEmpty(AppContext.getToken())) {
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
        if (StringUtils.isEmpty(AppContext.getToken())) {
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
        AuthInfo authInfo = IpuSoftSDK.getAuthInfo();
        String username = "";
        if (authInfo != null) {
            username = authInfo.getUsername();
        }
        params.put("callerId", username);//坐席编号
        params.put("ts", getSecondTimestamp(new Date()));//时间戳
        return params;
    }

    private static void callPhone(String phone, Observer<BindingInfo> observer) {
        AuthInfo authInfo = AppContext.getAuthInfo();
        if (authInfo != null) {
            Map<String, Object> params = getParams(phone);
            XService.Companion.callPhone(authInfo.getKey(),
                    getSign(GsonUtils.toJson(params)), params, observer);
        } else {
            Toast.makeText(AppContext.getActivityContext(), "认证失败", Toast.LENGTH_SHORT).show();
        }
    }

    private static void callPhone(String phone) {
        AuthInfo authInfo = AppContext.getAuthInfo();
        if (authInfo != null) {
            Map<String, Object> params = getParams(phone);
            XService.Companion.callPhone(authInfo.getKey(),
                    getSign(GsonUtils.toJson(params)), params, new IObserver<BindingInfo>() {
                        @Override
                        public void onNext(@NotNull BindingInfo bindingInfo) {
                            int code = bindingInfo.getCode();
                            if (HttpStatus.CODE_SUCCESS == code) {
                                BindingInfo.BindingData data = bindingInfo.getData();
                                if (data != null) {
                                    String number = data.getNumber();
                                    if (StringUtils.isNotEmpty(number)) {
                                        PhoneManager.callPhone(number);
                                    } else {
                                        Toast.makeText(IpuSoftSDK.getAppContext(), "号码为空", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(Constant.TAG, "XPhoneHttp->onError: " + e.toString());
                            Toast.makeText(IpuSoftSDK.getAppContext(), "拨号出错", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(AppContext.getActivityContext(), "认证失败", Toast.LENGTH_SHORT).show();
        }
    }


    private static void callPhone(String phone, String content) {
        AuthInfo authInfo = AppContext.getAuthInfo();
        if (authInfo != null) {
            Map<String, Object> params = getParams(phone);
            XService.Companion.callPhone(authInfo.getKey(),
                    getSign(GsonUtils.toJson(params)), params, new IObserver<BindingInfo>() {
                        @Override
                        public void onNext(@NotNull BindingInfo bindingInfo) {
                            int code = bindingInfo.getCode();
                            if (HttpStatus.CODE_SUCCESS == code) {
                                BindingInfo.BindingData data = bindingInfo.getData();
                                if (data != null) {
                                    String number = data.getNumber();
                                    if (StringUtils.isNotEmpty(number)) {
                                        PhoneManager.sendSms(number, content);
                                    } else {
                                        Toast.makeText(IpuSoftSDK.getAppContext(), "号码为空", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(Constant.TAG, "XPhoneHttp->onError: " + e.toString());
                            Toast.makeText(IpuSoftSDK.getAppContext(), "拨号出错", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(AppContext.getActivityContext(), "认证失败", Toast.LENGTH_SHORT).show();
        }
    }

    private static void callPhone(String phone, QueryXNumberListener listener) {
        AuthInfo authInfo = AppContext.getAuthInfo();
        if (authInfo != null) {
            Map<String, Object> params = getParams(phone);
            XService.Companion.callPhone(authInfo.getKey(),
                    getSign(GsonUtils.toJson(params)), params, new IObserver<BindingInfo>() {
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
            Toast.makeText(AppContext.getActivityContext(), "认证失败", Toast.LENGTH_SHORT).show();
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

    public static void main(String[] args) {
        System.out.println(MD5Utils.getMD5("321"));
    }
}
