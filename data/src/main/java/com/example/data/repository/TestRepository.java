package com.example.data.repository;

import com.example.data.api.TestApi;
import com.example.data.bean.ResultBo;
import com.example.data.bean.test.TestBean;

import java.util.List;

import rx.Observable;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class TestRepository {

    TestApi testApi;

    public TestRepository(TestApi testApi) {
        this.testApi = testApi;
    }

    public Observable<ResultBo<TestBean>> getTestData(String city) {
        return this.testApi.getTestData(city);
    }
}
