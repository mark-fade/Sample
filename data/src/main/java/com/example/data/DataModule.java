package com.example.data;

import android.content.Context;

import com.example.data.api.TestApi;
import com.example.data.repository.TestRepository;
import com.example.data.utils.CacheIntercepter;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*****************************
 * @作者：chenk
 * @描述：数据层 Module
 ******************************/

@Module
public class DataModule {

    // OkHttp Constants
    public static final String RESPONSE_CACHE_FILE = "response_cache";
    public static final long RESPONSE_CACHE_SIZE = 10 * 1024 * 1024L;
    public static final long HTTP_CONNECT_TIMEOUT = 10L;
    public static final long HTTP_READ_TIMEOUT = 30L;
    public static final long HTTP_WRITE_TIMEOUT = 10L;

    // API 地址
    private final String mApiHostUrl;
    private final Context context;
    private final boolean isDebug;
    private final String mChannelId; // 渠道id
    private final int mVersionCode; // 版本code
    private final String mDeviceId; // 设备Id

    public DataModule(Context context, String apiHostUrl, boolean isDebug, String channelId, int versionCode, String deviceId) {
        this.context = context.getApplicationContext();
        mApiHostUrl = apiHostUrl;
        this.isDebug = isDebug;
        this.mChannelId = channelId;
        this.mVersionCode = versionCode;
        this.mDeviceId = deviceId;

        if (isDebug) {
            Stetho.Initializer initializer = Stetho.newInitializerBuilder(context)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                    .build();
            Stetho.initialize(initializer);
        }
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache() {
        File cacheDir = new File(context.getCacheDir(), RESPONSE_CACHE_FILE);
        return new Cache(cacheDir, RESPONSE_CACHE_SIZE);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, CacheIntercepter cacheIntercepter) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(cacheIntercepter)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        if (isDebug)
            builder.addNetworkInterceptor(new StethoInterceptor());
        /**
         * 在请求头中加入App信息
         */
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("cId", mChannelId) // 渠道id
                        .addHeader("pId", "3") // 平台id
                        .addHeader("vId", String.valueOf(mVersionCode)) // 版本id
                        .addHeader("eId", mDeviceId) // 设备id
                        .build();
                return chain.proceed(request);
            }
        });
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mApiHostUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public CacheIntercepter provideCacheIntercepter() {
        return new CacheIntercepter(context);
    }

    @Singleton
    @Provides
    public TestApi provideTestApi(Retrofit retrofit) {
        return retrofit.create(TestApi.class);
    }

    @Singleton
    @Provides
    public TestRepository provideTestRepository(TestApi testApi) {
        return new TestRepository(testApi);
    }

}
