package com.newsknow.min.newsknow.presenter;

public interface ITopsPresenter extends BasePresenter {
    void getTopsNews();

    void getNews(int i);

    void getTopstory(String id);

    //void getLastFromCache();
}
