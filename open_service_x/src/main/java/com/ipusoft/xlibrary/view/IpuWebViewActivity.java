package com.ipusoft.xlibrary.view;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.utils.GsonUtils;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.xlibrary.R;
import com.ipusoft.xlibrary.bridge.NativeJSBridge;
import com.ipusoft.xlibrary.databinding.XActivityIpuWebViewBinding;
import com.ipusoft.xlibrary.iface.IpuWebInterface;
import com.ipusoft.xlibrary.utils.PhoneUtils;

/**
 * author : GWFan
 * time   : 5/13/21 10:02 AM
 * desc   :
 */

public class IpuWebViewActivity extends BaseActivity implements NativeJSBridge {
    private static final String TAG = "IpuWebViewActivity";
    private XActivityIpuWebViewBinding binding;
    private WebView webView;
    private static final String URL = "https://presaas.51lianlian.cn/h5/container.html";

    @Override
    protected void initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.x_activity_ipu_web_view);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initUI() {
        webView = binding.webview;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new IpuWebInterface(this), "android");
        String temp = "https://presaas.51lianlian.cn/h5/container.html?authCode="
                + IpuSoftSDK.getAuthCode() + "&type=SDK";
        Log.d(TAG, "initUI: ---" + temp);
        webView.loadUrl(temp);
        // authorize();
    }

    @Override
    protected void bindLiveData() {

    }

    @Override
    protected void initRequest() {

    }

    @Override
    public void call(String phone) {
        PhoneUtils.callPhone(phone);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void authorize() {
        String authCode = IpuSoftSDK.getAuthCode();
        if (StringUtils.isEmpty(authCode)) {
            Log.d(TAG, "authorize: 认证失败");
            Toast.makeText(IpuSoftSDK.getAppContext(), "认证失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "authorize: -----》" + authCode);
        new Handler().postDelayed(() -> {
            webView.evaluateJavascript("javascript:authorize(" + GsonUtils.toJson(authCode)
                    + ","
                    + GsonUtils.toJson("SDK") + ") ", value -> {
            });
        }, 1000);
    }

    @Override
    public void goBack() {
        finish();
    }
}
