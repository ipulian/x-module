package com.ipusoft.xphone.debug;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.config.Env;
import com.ipusoft.context.manager.PhoneManager;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.context.view.IpuWebViewActivity;
import com.ipusoft.xphone.R;
import com.ipusoft.xphone.bean.BindingInfo;
import com.ipusoft.xphone.constant.HttpStatus;
import com.ipusoft.xphone.databinding.ActivityMainBinding;
import com.ipusoft.xphone.http.XPhoneHttp;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    String key = "4571122846924808";
    String secret = "90237f5970f805250f07fef18fff45cb";
    String username = "17047151254";

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

        binding.etKey.setText(key);
        binding.etSecret.setText(secret);
        binding.etUsername.setText(username);

        binding.tvMsg.setText("当前运行环境：预发布");


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
                    Toast.makeText(AppContext.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AppContext.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSwitch(View view) {
        if (StringUtils.equals(Env.OPEN_DEV, MyApplication.env)) {
            MyApplication.env = Env.OPEN_PRO;
            binding.tvMsg.setText("当前运行环境：正式");
            binding.etKey.setText("");
            binding.etSecret.setText("");
            binding.etUsername.setText("");
        } else {
            MyApplication.env = Env.OPEN_DEV;
            binding.tvMsg.setText("当前运行环境：预发布");
            binding.etKey.setText(key);
            binding.etSecret.setText(secret);
            binding.etUsername.setText(username);
        }
    }

    public void onlogin(View view) {
        String ke = binding.etKey.getText().toString();
        String se = binding.etSecret.getText().toString();
        String us = binding.etUsername.getText().toString();
        if (StringUtils.isEmpty(ke) || StringUtils.isEmpty(se) || StringUtils.isEmpty(us)) {
            Toast.makeText(this, "请输入", Toast.LENGTH_SHORT).show();
            return;
        }
        IpuSoftSDK.init(MyApplication.instance, MyApplication.env, new AuthInfo(ke, se, us));
    }

    public void onsend(View view) {
        String string = binding.etPhone.getText().toString();
        if (StringUtils.isEmpty(string)) {
            Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onDial: 号码不能为空");
            return;
        }
        XPhoneHttp.sendMsgByXPhone(string, "123");
    }
}