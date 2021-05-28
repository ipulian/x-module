package com.ipusoft.xlibrary.module

import com.ipusoft.context.http.manager.OpenRetrofitManager
import com.ipusoft.xlibrary.api.XAPIService
import com.ipusoft.xlibrary.bean.BindingInfo
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
            OpenRetrofitManager.getInstance().retrofit.create(XAPIService::class.java)
                    .callPhone(apiKey, sign, OpenRetrofitManager.getInstance().getRequestBody(params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }
}