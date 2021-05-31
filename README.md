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
    implementation 'com.github.ipulian:x-module:latest-version'//使用时把 latest-version 替换成最新release版本
}
```
在AndroidManifest.xml中注册需要的权限
```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
```
## Usage
- 1.绑定小号并外呼(需要先申请android.permission.CALL_PHONE权限)
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
- 2.获取通话状态,可以参考 IpuSDK(https://github.com/ipulian/ipusdk) 中的说明。
- 3.展示通话弹屏,可以参考 IpuSDK(https://github.com/ipulian/ipusdk) 中的说明。
## ProGuard rules
```pro
-keep class com.ipusoft.xlibrary.** { *;}
```
# License
```
MIT License

Copyright (c) 2021 ipulian

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

