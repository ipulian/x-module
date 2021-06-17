package com.ipusoft.xphone.api;

import com.ipusoft.http.HttpConstant;
import com.ipusoft.xphone.bean.BindingInfo;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * author : GWFan
 * time   : 5/17/21 2:43 PM
 * desc   :
 */

public interface XAPIService {

    /**
     * 取号外呼
     *
     * @param requestBody
     * @return
     */
    @Headers({HttpConstant.OPEN_URL})
    @POST("/sdk/call/callPhone")
    Observable<BindingInfo> callPhone(@Header("api_key") String apiKey, @Header("sign") String sign,
                                      @Body RequestBody requestBody);
}
