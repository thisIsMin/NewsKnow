package com.newsknow.min.newsknow.presenter.impPresenter;

import com.newsknow.min.newsknow.api.ApiManager;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuStory;
import com.newsknow.min.newsknow.presenter.IZhihuStoryPresenter;
import com.newsknow.min.newsknow.presenter.impView.IZhihuStory;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZhihuStoryPresenterImp extends BasePresenterImpl implements IZhihuStoryPresenter {

    private IZhihuStory mIZhihuStory;

    public ZhihuStoryPresenterImp(IZhihuStory zhihuStory) {
        if (zhihuStory == null)
            throw new IllegalArgumentException("zhihuStory must not be null");
        mIZhihuStory = zhihuStory;
    }
    @Override
    public void getZhihuStory(String id) {
        Subscription s = ApiManager.getInstence().getZhihuApiService().getZhihuStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIZhihuStory.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuStory zhihuStory) {
                        mIZhihuStory.showZhihuStory(zhihuStory);
                    }
                });
        addSubscription(s);

    }

    @Override
    public void getGuokrArticle(String id) {

    }
}
