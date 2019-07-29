package com.newsknow.min.newsknow.presenter.impView;

import com.newsknow.min.newsknow.bean.zhihu.ZhihuStory;

public interface IZhihuStory {
    void showError(String error);

    void showZhihuStory(ZhihuStory zhihuStory);
}
