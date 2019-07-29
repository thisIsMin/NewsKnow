package com.newsknow.min.newsknow.bean.meizi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MeiziList {
    @SerializedName("results")
    public ArrayList<MeiziItem> meiziItems;

    public ArrayList<MeiziItem> getMeiziItems() {
        return meiziItems;
    }

    public void setMeiziItems(ArrayList<MeiziItem> meiziItems) {
        this.meiziItems = meiziItems;
    }
}
