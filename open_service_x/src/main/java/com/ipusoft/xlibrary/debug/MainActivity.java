package com.ipusoft.xlibrary.debug;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.manager.PhoneManager;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.context.view.IpuWebViewActivity;
import com.ipusoft.xlibrary.R;
import com.ipusoft.xlibrary.bean.BindingInfo;
import com.ipusoft.xlibrary.constant.HttpStatus;
import com.ipusoft.xlibrary.databinding.ActivityMainBinding;
import com.ipusoft.xlibrary.http.XPhoneHttp;

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
        String string = binding.etPhone.getText().toString();
        if (StringUtils.isEmpty(string)) {
            Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onDial: 号码不能为空");
            return;
        }

        XPhoneHttp.queryXPhone(string, bindingInfo -> {
            int code = bindingInfo.getCode();
            if (HttpStatus.CODE_SUCCESS == code) {
                BindingInfo.BindingData data = bindingInfo.getData();
                if (data != null) {
                    String number = data.getNumber();
                    PhoneManager.callPhone(number);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}