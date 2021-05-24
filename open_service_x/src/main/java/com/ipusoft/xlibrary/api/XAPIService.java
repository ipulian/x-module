package com.ipusoft.xlibrary.api;

import com.ipusoft.xlibrary.bean.IAuthCode;
import com.ipusoft.xlibrary.bean.IToken;
import com.ipusoft.xlibrary.bean.VirtualNumber;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * author : GWFan
 * time   : 5/17/21 2:43 PM
 * desc   :
 */

public interface XAPIService {

    /**
     * 获取授权码
     *
     * @param auth
     * @return
     */
    @GET("/getAuthCode/{auth}")
    Observable<IAuthCode> getAuthCode(@Path("auth") String auth);


    /**
     * 获取Token
     */
    @POST("/getAuthCodeInfo")
    @FormUrlEncoded
    Observable<IToken> getAuthCodeInfo(@FieldMap Map<String, Object> params);

    /**
     * 取号外呼
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/call/callPhone")
    Observable<VirtualNumber> callPhone(@Body RequestBody requestBody);
}
