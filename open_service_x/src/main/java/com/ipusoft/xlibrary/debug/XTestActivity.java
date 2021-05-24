package com.ipusoft.xlibrary.debug;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.xlibrary.R;
import com.ipusoft.xlibrary.XModuleApp;
import com.ipusoft.xlibrary.bean.IToken;
import com.ipusoft.xlibrary.bean.VirtualNumber;
import com.ipusoft.xlibrary.databinding.ActivityXTestBinding;
import com.ipusoft.xlibrary.http.XPhoneHttp;
import com.ipusoft.xlibrary.utils.PhoneUtils;
import com.ipusoft.xlibrary.view.IpuWebViewActivity;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * author : GWFan
 * time   : 5/17/21 2:55 PM
 * desc   :
 */

public class XTestActivity extends BaseActivity {
    private static final String TAG = "XTestActivity";
    private ActivityXTestBinding binding;

    @Override
    protected void initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_x_test);
    }

    @Override
    protected void initData() {
        onLogin();
    }

    @Override
    protected void initUI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1024);
        }
    }

    @Override
    protected void bindLiveData() {

    }

    @Override
    protected void initRequest() {

    }

    /**
     * 登陆
     */
    public void onLogin() {
        String key = "4571122846924808";
        String secret = "90237f5970f805250f07fef18fff45cb";
        String username = "17047151254";
        XModuleApp.initXModule(key, secret, username, new Observer<IToken>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull IToken iToken) {
                String status1 = iToken.getStatus();
                if (HttpStatus.SUCCESS.equals(status1)) {
                    XModuleApp.token = iToken.getToken();
                    Toast.makeText(IpuSoftSDK.getAppContext(), "登陆成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), iToken.getMsg(), Toast.LENGTH_SHORT).show();
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
     * 加载h5
     *
     * @param view
     */
    public void onLoad(View view) {
        startActivity(new Intent(this, IpuWebViewActivity.class));
    }

    /**
     * 拨号
     *
     * @param view
     */
    public void onDial(View view) {
        try {
            String string = binding.etPhone.getText().toString();
            XPhoneHttp.queryXPhone(string, virtualNumber -> {
                String status = virtualNumber.getStatus();
                if (HttpStatus.SUCCESS.equals(status)) {
                    if ("X".equals(virtualNumber.getCallType())) {
                        VirtualNumber.BindInfoBean bindInfo = virtualNumber.getBindInfo();
                        if (bindInfo != null) {
                            PhoneUtils.callPhone(bindInfo.getVirtualNumber());
                        }
                    } else {
                        Toast.makeText(IpuSoftSDK.getAppContext(), "外呼方式配置错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), virtualNumber.getMsg(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
