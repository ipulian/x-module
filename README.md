# 小号通讯SDK
**使用小号通讯SDK之前，需要先集成 IpuSDK(https://github.com/ipulian/ipusdk)** 
有一些公共方法，在IpuSDK中已经做了说明，这里不再赘述。
首先可以通过 https://github.com/ipulian/x-module.git 把该项目在Android Studio中直接运行。
## Setup
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.ipulian:x-module:latest-version'
}
```
在AndroidManifest.xml中注册需要的权限
```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
```
## Usage
1. 绑定小号并外呼
```java
  //参数 phone 为联系人的真实号码, bindingInfo 为返回的绑定信息(详细说明，参考BindingInfo.class)
   XPhoneHttp.queryXPhone(phone, bindingInfo -> {
      int code = bindingInfo.getCode();
            if (HttpStatus.CODE_SUCCESS == code) {
                BindingInfo.BindingData data = bindingInfo.getData();
                if (data != null) {
                    //这个number 即是可以直接外呼的小号。
                    String number = data.getNumber();
                    PhoneUtils.callPhone(number);
                } else {
                    Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(IpuSoftSDK.getAppContext(), bindingInfo.getMessage(), Toast.LENGTH_SHORT).show();
            }
   })
````





