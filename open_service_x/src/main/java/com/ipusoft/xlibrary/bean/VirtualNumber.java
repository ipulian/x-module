package com.ipusoft.xlibrary.bean;

import com.ipusoft.context.bean.base.BaseHttpResponse;

import java.util.List;

/**
 * author : GWFan
 * time   : 7/9/20 11:37 AM
 * desc   :
 */

public class VirtualNumber extends BaseHttpResponse {
    private static final long serialVersionUID = 64764129134858215L;

    private String callId;
    private String serviceType;
    private String isClue;
    private Integer isPushApp;
    private String isSip;
    private String userId;
    private String callType;
    private BindInfoBean bindInfo;
    private String phone;
    private String device;

    public static class BindInfoBean {
        private List<String> caller;
        private String phone;
        private String xphone;
        private String phoneArea;
        private String channelName;
        private String virtualNumber;
        private String xphoneArea;

        public List<String> getCaller() {
            return caller;
        }

        public void setCaller(List<String> caller) {
            this.caller = caller;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getXphone() {
            return xphone;
        }

        public void setXphone(String xphone) {
            this.xphone = xphone;
        }

        public String getPhoneArea() {
            return phoneArea;
        }

        public void setPhoneArea(String phoneArea) {
            this.phoneArea = phoneArea;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getVirtualNumber() {
            return virtualNumber;
        }

        public void setVirtualNumber(String virtualNumber) {
            this.virtualNumber = virtualNumber;
        }

        public String getXphoneArea() {
            return xphoneArea;
        }

        public void setXphoneArea(String xphoneArea) {
            this.xphoneArea = xphoneArea;
        }
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getIsClue() {
        return isClue;
    }

    public void setIsClue(String isClue) {
        this.isClue = isClue;
    }

    public Integer getIsPushApp() {
        return isPushApp;
    }

    public void setIsPushApp(Integer isPushApp) {
        this.isPushApp = isPushApp;
    }

    public String getIsSip() {
        return isSip;
    }

    public void setIsSip(String isSip) {
        this.isSip = isSip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public BindInfoBean getBindInfo() {
        return bindInfo;
    }

    public void setBindInfo(BindInfoBean bindInfo) {
        this.bindInfo = bindInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}

