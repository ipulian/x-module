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
import com.ipusoft.xlibrary.R;
import com.ipusoft.xlibrary.XModuleApp;
import com.ipusoft.xlibrary.bridge.NativeJSBridge;
import com.ipusoft.xlibrary.databinding.XActivityIpuWebViewBinding;
import com.ipusoft.xlibrary.iface.IpuWebInterface;
import com.ipusoft.xlibrary.utils.PhoneUtils;
import com.ipusoft.xlibrary.utils.XGsonUtils;
import com.ipusoft.xlibrary.utils.XStringUtils;

/**
 * author : GWFan
 * time   : 5/13/21 10:02 AM
 * desc   :
 */

public class IpuWebViewActivity extends BaseActivity implements NativeJSBridge {
    private static final String TAG = "IpuWebViewActivity";
    private XActivityIpuWebViewBinding binding;
    private WebView webView;
    private static final String URL = "http://192.168.0.64:8086/mui/container.html";

    @Override
    protected void initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.x_activity_ipu_web_view);
    }

    @Override
    protected void initData() {
//        String key = "4571122846924808";
//        String secret = "90237f5970f805250f07fef18fff45cb";
//        String username = "17047151254";
//        IpuSoftSDK.init(this.getApplication(), new IAuthInfo(key, secret, username));
    }

    @Override
    protected void initUI() {
        // new Handler().postDelayed(() -> {
        webView = binding.webview;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new IpuWebInterface(this), "android");
        webView.loadUrl(URL);
        authorize();
        //  }, 2000);
    }

    @Override
    protected void bindLiveData() {

    }

    @Override
    protected void initRequest() {

    }

    @Override
    public void title(String title) {
        //runOnUiThread(() -> binding.titlebar.setTitle(title));
    }

    @Override
    public void call(String phone) {
        PhoneUtils.callPhone(phone);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void authorize() {
        String authCode = XModuleApp.getAuthCode();
        if (XStringUtils.isEmpty(authCode)) {
            Log.d(TAG, "authorize: 认证失败");
            Toast.makeText(IpuSoftSDK.getAppContext(), "认证失败", Toast.LENGTH_SHORT).show();
            return;
        }
        new Handler().postDelayed(() -> {
            webView.evaluateJavascript("javascript:authorize(" + XGsonUtils.toJson(authCode)
                    + ","
                    + XGsonUtils.toJson("SDK") + ") ", value -> {
            });
        }, 1000);
    }
}