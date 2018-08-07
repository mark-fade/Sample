package com.example.data.utils;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*****************************
 * @作者：chenk
 * @描述：${Okhttp的拦截器，用于控制缓存}
 */

public class CacheIntercepter implements Interceptor {

    private Context mContext;

    public CacheIntercepter(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (!NetWorkUtil.isNetworkReachable(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }

        Response response = chain.proceed(request);
        if (!NetWorkUtil.isNetworkReachable(mContext)) {
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }

        return response;
    }
}
