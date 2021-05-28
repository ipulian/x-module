package com.ipusoft.xlibrary.constant;

/**
 * author : GWFan
 * time   : 5/27/21 3:03 PM
 * desc   :
 */

public class HttpStatus {
    /**
     * * 0成功
     * * 1签名验证错误
     * * 2参数错误
     * * 3坐席员工错误
     * * 4小号错误
     * * 5企业相关错误
     * * 6其他错误
     */
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR_SIGN = 1;
    public static final int CODE_ERROR_PARAM = 2;
    public static final int CODE_ERROR_SEAT = 3;
    public static final int CODE_ERROR_X_PHONE = 4;
    public static final int CODE_ERROR_COMPANY = 5;
    public static final int CODE_ERROR_OTHER = 6;
}
