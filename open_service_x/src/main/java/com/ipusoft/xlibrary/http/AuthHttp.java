package com.ipusoft.xlibrary.http;

import android.util.Log;

import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.xlibrary.XModuleApp;
import com.ipusoft.xlibrary.bean.IAuthCode;
import com.ipusoft.xlibrary.bean.IToken;
import com.ipusoft.xlibrary.constant.Constant;
import com.ipusoft.xlibrary.module.XService;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 5/17/21 2:43 PM
 * desc   :
 */

public class AuthHttp {

    /**
     * 验证身份信息
     *
     * @param auth
     */
    public static void checkIdentity(String auth) {
        XService.Companion.getAuthCode(auth, new IObserver<IAuthCode>() {
            @Override
            public void onNext(@NonNull IAuthCode result) {
                String status = result.getStatus();
                if (HttpStatus.SUCCESS.equals(status)) {
                    String authCode = result.getAuthCode();
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("authCode", authCode);
                    XService.Companion.getAuthCodeInfo(params, new IObserver<IToken>() {
                        @Override
                        public void onNext(@NonNull IToken iToken) {
                            String status1 = iToken.getStatus();
                            if (HttpStatus.SUCCESS.equals(status1)) {
                                XModuleApp.token = iToken.getToken();
                            } else {
                                Log.d(Constant.TAG, "AuthHttp->onNext1: " + iToken.getMsg());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(Constant.TAG, "AuthHttp->onNext1: " + e.toString());
                        }
                    });
                } else {
                    Log.d(Constant.TAG, "AuthHttp->onNext2: " + result.getMsg());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(Constant.TAG, "AuthHttp->onNext2: " + e.toString());
            }
        });
    }
}
