package com.newsknow.min.newsknow.presenter.impView;

public interface IBaseFragment {
    void showProgressDialog();

    void hidProgressDialog();

    void showError(String error);
}
