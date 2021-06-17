package com.ipusoft.xphone.module

import com.ipusoft.http.manager.RetrofitManager
import com.ipusoft.xphone.api.XAPIService
import com.ipusoft.xphone.bean.BindingInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * author : GWFan
 * time   : 5/17/21 3:18 PM
 * desc   :
 */

class XService {
    companion object {
        /**
         * 取号外呼
         */
        fun callPhone(apiKey: String, sign: String,
                      params: Map<String, Any>, observer: Observer<BindingInfo>) {
            RetrofitManager.getInstance().retrofit.create(XAPIService::class.java)
                    .callPhone(apiKey, sign, RetrofitManager.getInstance().getRequestBody(params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }
}