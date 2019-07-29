package com.newsknow.min.newsknow.presenter;



public interface IZhihuPresenter extends BasePresenter{
    void getLastZhihuNews();

    void getTheDaily(String date);

    void getLastFromCache();
}
