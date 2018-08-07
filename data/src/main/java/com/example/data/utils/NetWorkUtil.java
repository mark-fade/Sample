package com.example.data.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import okhttp3.RequestBody;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class NetWorkUtil {

    /**判断网络连接是否可用*/
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    public static RequestBody getRequestBodyByObject(Object object) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), getJsonStringByObject(object));
    }

    public static RequestBody getRequestBodyByJsonString(String jsonString) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
    }

    private static String getJsonStringByObject(Object object) {
        String jsonString = "";
        if (object != null) {
            Gson gson = new Gson();
            jsonString = gson.toJson(object);
        }
        return jsonString;
    }
}
