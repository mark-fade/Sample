package com.example.sample;



import com.example.data.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/
@Module
public class AppModule {

    private final App mApp;
    private final String mHostUrl;
    private final boolean isDebug;
    private final String mChannelId;
    private final int mVersionCode;
    private final String mDeviceId;


    public AppModule(App app, String apiHostUrl, boolean isDebug, String channelId, int versionCode, String deviceId) {
        this.mApp = app;
        this.mHostUrl = apiHostUrl;
        this.isDebug = isDebug;
        this.mChannelId = channelId;
        this.mVersionCode = versionCode;
        this.mDeviceId = deviceId;
    }

    @Provides
    @Singleton
    DataManager providesDataManager() {
        return new DataManager(mApp, mHostUrl, isDebug, mChannelId, mVersionCode, mDeviceId);
    }



}
