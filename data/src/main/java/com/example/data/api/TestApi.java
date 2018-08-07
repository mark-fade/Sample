package com.example.data.api;

import com.example.data.bean.ResultBo;
import com.example.data.bean.test.TestBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public interface TestApi {

    @GET("open/api/weather/json.shtml")
    Observable<ResultBo<TestBean>> getTestData(@Query("city") String city);


}
