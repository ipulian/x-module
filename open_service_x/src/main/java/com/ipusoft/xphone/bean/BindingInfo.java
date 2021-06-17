package com.ipusoft.xphone.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : GWFan
 * time   : 7/9/20 11:37 AM
 * desc   :
 */

public class BindingInfo implements Serializable {
    private static final long serialVersionUID = 64764129134858215L;
    /**
     * 0成功
     * 1签名验证错误
     * 2参数错误
     * 3坐席员工错误
     * 4小号错误
     * 5企业相关错误
     * 6其他错误
     */
    private int code;
    //错误原因描述
    private String message;

    private BindingData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BindingData getData() {
        return data;
    }

    public void setData(BindingData data) {
        this.data = data;
    }

    public static class BindingData {
        //绑定的小号
        private String number;
        //限制使用的主叫
        private List<String> caller;
        //是否是复用的号码
        private String reuse;
        //绑定ID
        private String bindId;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<String> getCaller() {
            return caller;
        }

        public void setCaller(List<String> caller) {
            this.caller = caller;
        }

        public String getReuse() {
            return reuse;
        }

        public void setReuse(String reuse) {
            this.reuse = reuse;
        }

        public String getBindId() {
            return bindId;
        }

        public void setBindId(String bindId) {
            this.bindId = bindId;
        }

    }
}

