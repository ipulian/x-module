package com.ipusoft.xlibrary.module

import com.ipusoft.context.http.OpenRetrofitManager
import com.ipusoft.xlibrary.api.XAPIService
import com.ipusoft.xlibrary.bean.IAuthCode
import com.ipusoft.xlibrary.bean.IToken
import com.ipusoft.xlibrary.bean.VirtualNumber
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
         * 查询AuthCode
         */
        fun getAuthCode(auth: String, observer: Observer<IAuthCode>) {
            OpenRetrofitManager.getInstance().retrofit.create(XAPIService::class.java)
                    .getAuthCode(auth)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 查询Token
         */
        fun getAuthCodeInfo(params: Map<String, Any>, observer: Observer<IToken>) {
            OpenRetrofitManager.getInstance().retrofit.create(XAPIService::class.java)
                    .getAuthCodeInfo(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 取号外呼
         */
        fun callPhone(params: Map<String, Any>, observer: Observer<VirtualNumber>) {
            OpenRetrofitManager.getInstance().retrofit.create(XAPIService::class.java)
                    .callPhone(OpenRetrofitManager.getInstance().getRequestBody(params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }
}