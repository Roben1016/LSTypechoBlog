package com.roshine.lstypechoblog.http;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author Roshine
 * @date 2017/8/17 21:11
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface RxService {
    @Headers({"Content-type:text/xml;charset=UTF-8"})
    @POST("index.php/action/xmlrpc")
    Flowable<Response<ResponseBody>> getResult(@Body RequestBody requestBody);
}
