package com.ipusoft.demo;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.demo.databinding.ActivityMainBinding;
import com.ipusoft.xlibrary.bean.VirtualNumber;
import com.ipusoft.xlibrary.http.XPhoneHttp;
import com.ipusoft.xlibrary.utils.PhoneUtils;
import com.ipusoft.xlibrary.view.IpuWebViewActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initData() {
    }

    /**
     * 申请拨号权限
     */
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