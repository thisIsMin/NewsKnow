package com.newsknow.min.newsknow.api;

import android.util.Log;

import com.newsknow.min.newsknow.MyApplication;
import com.newsknow.min.newsknow.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {
    private static ApiManager apiManager;
    private Object zhihuMonitor = new Object();
    private ZhihuApi zhihuApi;
    private Object mTopsMonitor = new Object();
    private TopsApi mTopsApi;
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    private static File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "zhihuCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    public static ApiManager getInstence() {

        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    public ZhihuApi getZhihuApiService() {
        if (zhihuApi == null) {
            synchronized (zhihuMonitor) {
                if (zhihuApi == null) {
                    zhihuApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZhihuApi.class);
                }
            }
        }

        return zhihuApi;
    }

    public TopsApi getTopsApiService(){
        Log.i("testtt","gettopsapiservice");
        if (mTopsApi == null) {
            synchronized (mTopsMonitor) {
                if (mTopsApi == null) {
                    mTopsApi = new Retrofit.Builder()
                            .baseUrl("http://c.m.163.com")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(TopsApi.class);
                }
            }
        }

        return mTopsApi;
    }

    private MeiziApi meiziApi;
    public MeiziApi getGankService(){
        if (meiziApi==null){
            synchronized (zhihuMonitor){
                if (meiziApi==null){
                    meiziApi=new Retrofit.Builder()
                            .baseUrl("http://gank.io")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(MeiziApi.class);

                }
            }
        }
        return meiziApi;
    }
}
