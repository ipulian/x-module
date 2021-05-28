package com.ipusoft.xlibrary.view;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.xlibrary.R;
import com.ipusoft.xlibrary.bridge.NativeJSBridge;
import com.ipusoft.xlibrary.databinding.XActivityIpuWebViewBinding;
import com.ipusoft.xlibrary.iface.IpuWebInterface;
import com.ipusoft.xlibrary.router.IRouterPath;
import com.ipusoft.xlibrary.utils.PhoneUtils;

/**
 * author : GWFan
 * time   : 5/13/21 10:02 AM
 * desc   :
 */

@Route(path = IRouterPath.ACTION_WEB_VIEW_ACTIVITY)
public class IpuWebViewActivity extends BaseActivity implements NativeJSBridge {
    private static final String TAG = "IpuWebViewActivity";
    private XActivityIpuWebViewBinding binding;

    @Override
    protected void initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.x_activity_ipu_web_view);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initUI() {
        WebView webView = binding.webview;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new IpuWebInterface(this), "android");
        String url = "https://presaas.51lianlian.cn/h5/container.html?authCode="
                + IpuSoftSDK.getAuthCode() + "&type=SDK";
        Log.d(TAG, "initUI: ---" + url);
        webView.loadUrl(url);
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

    @Override
    public void goBack() {
        finish();
    }
}
