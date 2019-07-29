package com.newsknow.min.newsknow.presenter.impPresenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newsknow.min.newsknow.api.ApiManager;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDaily;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDailyItem;
import com.newsknow.min.newsknow.presenter.IZhihuPresenter;
import com.newsknow.min.newsknow.presenter.impView.IZhihuFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ZhihuPresenterImp extends BasePresenterImpl implements IZhihuPresenter {
    private IZhihuFragment mZhihuFragment;
    //private CacheUtil mCacheUtil;
    private Gson gson = new Gson();

    public ZhihuPresenterImp(Context context, IZhihuFragment zhihuFragment) {

        mZhihuFragment = zhihuFragment;
        //mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getLastZhihuNews() {
        Log.i("testtt","getlastnew");
        mZhihuFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getZhihuApiService().getLastDaily()
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        Log.i("testtt","call");
                        Log.i("testtt",zhihuDaily.getDate());
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStories()) {
                            zhihuDailyItem.setDate(date);
                        }
                        return zhihuDaily;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("testtt","onerr");
                        mZhihuFragment.hidProgressDialog();
                        mZhihuFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        Log.i("testtt","onnext");
                        mZhihuFragment.hidProgressDialog();
                        //mCacheUtil.put(Config.ZHIHU, gson.toJson(zhihuDaily));
                        mZhihuFragment.updateList(zhihuDaily);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getTheDaily(String date) {
        mZhihuFragment.showProgressDialog();
        Subscription subscription =ApiManager.getInstence().getZhihuApiService().getTheDaily(date)
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStories()) {
                            zhihuDailyItem.setDate(date);
                        }
                        return zhihuDaily;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mZhihuFragment.hidProgressDialog();
                        mZhihuFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mZhihuFragment.hidProgressDialog();
                        mZhihuFragment.updateList(zhihuDaily);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getLastFromCache() {

    }

}
