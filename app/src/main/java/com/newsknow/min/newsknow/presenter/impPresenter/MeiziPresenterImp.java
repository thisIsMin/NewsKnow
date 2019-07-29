package com.newsknow.min.newsknow.presenter.impPresenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newsknow.min.newsknow.api.ApiManager;
import com.newsknow.min.newsknow.bean.meizi.MeiziList;
import com.newsknow.min.newsknow.presenter.IMeiziPresenter;
import com.newsknow.min.newsknow.presenter.impView.IMeiziFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MeiziPresenterImp extends BasePresenterImpl implements IMeiziPresenter {
    private IMeiziFragment mMeiziFragment;
    //private CacheUtil mCacheUtil;
    private Gson gson = new Gson();

    public MeiziPresenterImp(Context context, IMeiziFragment mMeiziFragment) {

        this.mMeiziFragment = mMeiziFragment;
        //mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getMeizi(int i) {
        Log.i("testtt","getmeizi");
        mMeiziFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getGankService().getMeizhi(i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiziList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mMeiziFragment.hidProgressDialog();
                        mMeiziFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(MeiziList meiziList) {
                        Log.i("testtt","getmeizi  onnext");
                        mMeiziFragment.hidProgressDialog();
                       // mCacheUtil.put(Config.ZHIHU, gson.toJson(meiziData));
                        mMeiziFragment.updateMeiziData(meiziList.getMeiziItems());
                    }
                });
        addSubscription(subscription);
    }
}
