package com.ipusoft.xlibrary.iface;

import android.webkit.JavascriptInterface;

import com.ipusoft.xlibrary.bridge.NativeJSBridge;

/**
 * author : GWFan
 * time   : 5/13/21 10:10 AM
 * desc   :
 */

public class IpuWebInterface {
    private final NativeJSBridge bridge;

    public IpuWebInterface(NativeJSBridge bridge) {
        this.bridge = bridge;
    }

    @JavascriptInterface
    public void call(String phone) {
        bridge.call(phone);
    }
}
