package com.newsknow.min.newsknow.presenter.impView;

import com.newsknow.min.newsknow.bean.meizi.MeiziItem;
import com.newsknow.min.newsknow.bean.meizi.MeiziList;

import java.util.ArrayList;

public interface IMeiziFragment extends IBaseFragment{
    void updateMeiziData(ArrayList<MeiziItem> meiziItems);
}
