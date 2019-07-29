package com.newsknow.min.newsknow.api;



import com.newsknow.min.newsknow.bean.zhihu.ZhihuDaily;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuStory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface ZhihuApi {

    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStory> getZhihuStory(@Path("id") String id);
//
//    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
//    Observable<ImageResponse> getImage();

}
