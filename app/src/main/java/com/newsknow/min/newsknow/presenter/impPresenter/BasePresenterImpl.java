package com.newsknow.min.newsknow.presenter.impPresenter;

import com.newsknow.min.newsknow.presenter.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class BasePresenterImpl implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unsubscrible() {

        // TODO: 16/8/17 find when unsubscrible
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
