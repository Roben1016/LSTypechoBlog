package com.roshine.lstypechoblog.http.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Roshine
 * @date 2017/8/17 15:13
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class HeaderInterceptor implements Interceptor{

    private Map<String, Object> headerMaps = new TreeMap<>();

    public HeaderInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (headerMaps != null && headerMaps.size() > 0) {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }
        else {
            request
//                    .addHeader("Content-type", "application/json")
//                    .addHeader("Version", getAppVersion())
//                    .addHeader("uuid", getUUID())
                    .addHeader("User-Agent","plain-text ");//  System.getProperty("http.agent")
        }


        return chain.proceed(request.build());
    }

}
