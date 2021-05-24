package com.ipusoft.xlibrary;

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.xlibrary.bean.IToken;
import com.ipusoft.xlibrary.http.AuthHttp;
import com.ipusoft.xlibrary.utils.XStringUtils;
import com.tencent.mmkv.MMKV;

import java.security.MessageDigest;
import java.util.Date;

import io.reactivex.rxjava3.core.Observer;

/**
 * author : GWFan
 * time   : 5/17/21 2:16 PM
 * desc   :
 */

public class XModuleApp extends IpuSoftSDK {

    private static final String TAG = "XLibraryApplication";
    private static String authCode;

    public static String token = "";

    @Override
    public void onCreate() {
        super.onCreate();
        IpuSoftSDK.init(this);
    }

    /**
     * 该方法通过反射调用
     *
     * @param key
     * @param secret
     * @param username
     */
    public static void initXModule(String key, String secret, String username) {
        initMMKV();
        String sign = getSign(key, secret, username);
        authCode = getAuth(key, username, sign);
        AuthHttp.checkIdentity(authCode);
    }

    public static void initXModule(String key, String secret, String username, Observer<IToken> observer) {
        initMMKV();
        String sign = getSign(key, secret, username);
        authCode = getAuth(key, username, sign);
        AuthHttp.checkIdentity(authCode, observer);
    }

    /**
     * 该方法通过反射调用
     *
     * @param iAuthInfo
     */
    public static void initXModule(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null) {
            String key = iAuthInfo.getKey(), secret = iAuthInfo.getSecret(), username = iAuthInfo.getUsername();
            if (XStringUtils.isNotEmpty(key) && XStringUtils.isNotEmpty(secret) && XStringUtils.isNotEmpty(username)) {
                initXModule(key, secret, username);
            }
        }
    }

    public static void initXModule(IAuthInfo iAuthInfo, Observer<IToken> observer) {
        if (iAuthInfo != null) {
            String key = iAuthInfo.getKey(), secret = iAuthInfo.getSecret(), username = iAuthInfo.getUsername();
            if (XStringUtils.isNotEmpty(key) && XStringUtils.isNotEmpty(secret) && XStringUtils.isNotEmpty(username)) {
                initXModule(key, secret, username, observer);
            }
        }
    }

    //初始化MMKV
    private static void initMMKV() {
        MMKV.initialize(getAppContext());
    }

    public static String getAuthCode() {
        return authCode;
    }

    private static String getSign(String key, String secret, String username) {
        System.currentTimeMillis();
        return getMD5("dev=SDK&key=" + key + "&ts=" + getSecondTimestamp(new Date())
                + "&username=" + username + secret);
    }

    private static String getAuth(String key, String username, String sign) {
        String str = ("dev=SDK&key=" + key + "&ts=" + getSecondTimestamp(new Date())
                + "&username=" + username + "&sign=" + sign);
        return XStringUtils.base64Encode2String(str.getBytes());
    }

    private static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }

    private static String getMD5(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    @Override
    public void initModule() {

    }
}
