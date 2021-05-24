package com.ipusoft.xlibrary.http;

import android.widget.Toast;

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.xlibrary.XModuleApp;
import com.ipusoft.xlibrary.bean.IAuthCode;
import com.ipusoft.xlibrary.bean.IToken;
import com.ipusoft.xlibrary.module.XService;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

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
        XService.Companion.getAuthCode(auth, new Observer<IAuthCode>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull IAuthCode result) {
                String status = result.getStatus();
                if (HttpStatus.SUCCESS.equals(status)) {
                    String authCode = result.getAuthCode();
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("authCode", authCode);
                    XService.Companion.getAuthCodeInfo(params, new Observer<IToken>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull IToken iToken) {
                            String status1 = iToken.getStatus();
                            if (HttpStatus.SUCCESS.equals(status1)) {
                                XModuleApp.token = iToken.getToken();
                            } else {
                                Toast.makeText(IpuSoftSDK.getAppContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
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

    public static void checkIdentity(String auth, Observer<IToken> observer) {
        XService.Companion.getAuthCode(auth, new Observer<IAuthCode>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull IAuthCode result) {
                String status = result.getStatus();
                if (HttpStatus.SUCCESS.equals(status)) {
                    String authCode = result.getAuthCode();
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("authCode", authCode);
                    XService.Companion.getAuthCodeInfo(params, observer);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
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
}
