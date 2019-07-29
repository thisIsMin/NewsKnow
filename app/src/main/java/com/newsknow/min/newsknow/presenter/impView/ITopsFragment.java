package com.newsknow.min.newsknow.presenter.impView;

import com.newsknow.min.newsknow.bean.tops.TopsItem;

import java.util.ArrayList;

public interface ITopsFragment extends IBaseFragment {
    void updateList(ArrayList<TopsItem> topsItems);

}
