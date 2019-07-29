package com.newsknow.min.newsknow.presenter.impPresenter;

import android.util.Log;

import com.google.gson.Gson;
import com.newsknow.min.newsknow.api.ApiManager;
import com.newsknow.min.newsknow.bean.tops.NewsList;
import com.newsknow.min.newsknow.bean.tops.TopsItem;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDaily;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDailyItem;
import com.newsknow.min.newsknow.presenter.ITopsPresenter;
import com.newsknow.min.newsknow.presenter.impView.ITopsFragment;
import com.newsknow.min.newsknow.presenter.impView.ITopsStory;
import com.newsknow.min.newsknow.presenter.impView.IZhihuFragment;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TopsPresenterImp extends BasePresenterImpl implements ITopsPresenter {

    private ITopsFragment mTopsFragment;
    private ITopsStory mTopsStory;
    private Gson gson = new Gson();

    public TopsPresenterImp(ITopsFragment iTopsFragment) {
        this.mTopsFragment = iTopsFragment;
    }

    public TopsPresenterImp(ITopsStory iTopsStory) {
        this.mTopsStory = iTopsStory;
    }

    @Override
    public void getTopsNews() {
        mTopsFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getTopsApiService().getTopsNews()
                .map(new Func1<ArrayList<TopsItem>, ArrayList<TopsItem>>() {
                    @Override
                    public ArrayList<TopsItem>  call(ArrayList<TopsItem> topsItems) {
//                        String date = topsItems.get(0).getDate();
//                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStories()) {
//                            zhihuDailyItem.setDate(date);
//                        }
                        return topsItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<TopsItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("testtt","topsp onerr");
                        mTopsFragment.hidProgressDialog();
                        mTopsFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<TopsItem> topsItems) {
                        Log.i("testtt","topsp onnext");
                        Log.i("testtt",String.valueOf(topsItems.size()));
                        mTopsFragment.hidProgressDialog();
                        //mCacheUtil.put(Config.ZHIHU, gson.toJson(zhihuDaily));
                        mTopsFragment.updateList(topsItems);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getNews(int i) {
        Log.i("testtt",String.valueOf(i));
        mTopsFragment.showProgressDialog();
        Subscription subscription =ApiManager.getInstence().getTopsApiService().getNews(i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mTopsFragment.hidProgressDialog();
                        mTopsFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(NewsList newsList) {
                        mTopsFragment.hidProgressDialog();
                        Log.i("testtt",String.valueOf(newsList.getList().size()));
                        Log.i("testtt",String.valueOf(newsList.getList().get(0).getTitle()));
                        mTopsFragment.updateList(newsList.getList());
                    }
                });
        addSubscription(subscription);

    }

    @Override
    public void getTopstory(String id) {

    }
}
