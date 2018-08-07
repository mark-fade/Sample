package com.example.data;

import android.content.Context;

import com.example.data.repository.TestRepository;
import com.google.gson.Gson;

import javax.inject.Inject;



/*****************************
 * @作者：chenk
 * @描述：DataManager 类
 ******************************/

public class DataManager {

    public static final int PAGE_SIZE_CONTACTS = 1000;

    @Inject
    public Gson gson;

    public DataManager(Context context, String apiHostUrl, boolean isDebug, String channelId, int versionCode, String deviceId) {
        DaggerDataComponent.builder()
                .dataModule(new DataModule(context, apiHostUrl, isDebug, channelId, versionCode, deviceId))
                .build()
                .inject(this);
    }

    @Inject
    public TestRepository testRepository;


}
