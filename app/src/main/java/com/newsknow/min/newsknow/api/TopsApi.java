package com.newsknow.min.newsknow.api;

import com.newsknow.min.newsknow.bean.tops.NewsList;
import com.newsknow.min.newsknow.bean.tops.TopsItem;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDaily;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuStory;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface TopsApi {
    @GET("http://c.m.163.com/nc/article/headline/T1348647909107/20.html")
    Observable<ArrayList<TopsItem>> getTopsNews();

    @GET("/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStory> getZhihuStory(@Path("id") String id);
}
