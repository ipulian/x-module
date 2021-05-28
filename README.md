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
- 1. 绑定小号并外呼(需要先申请android.permission.CALL_PHONE权限)
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
或者通过RxJava3 的Observer 返回bindingInfo

```java
XPhoneHttp.queryXPhone(string, new Observer<BindingInfo>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                
            }

            @Override
            public void onNext(@NonNull BindingInfo bindingInfo) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
````
- 2. 获取通话状态,可以参考 IpuSDK(https://github.com/ipulian/ipusdk) 中的说明。
- 3. 展示通话弹屏,可以参考 IpuSDK(https://github.com/ipulian/ipusdk) 中的说明。
- 4. 查看通话记录，使用拨号键盘，查看电话统计，客户统计，综合排名等功能，可以跳转到内部的H5页面
```java
      startActivity(new Intent(this, IpuWebViewActivity.class));
```
## ProGuard rules
```pro
-keep class com.ipusoft.xlibrary.** { *;}
```




