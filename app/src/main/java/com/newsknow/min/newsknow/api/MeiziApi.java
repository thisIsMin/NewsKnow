package com.newsknow.min.newsknow.api;

import com.newsknow.min.newsknow.bean.meizi.MeiziList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MeiziApi {
    @GET("/api/data/福利/10/{page}")
    Observable<MeiziList> getMeizhi(@Path("page") int page);
}
