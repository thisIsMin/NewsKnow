package com.newsknow.min.newsknow.bean.tops;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xinghongfei on 16/8/17.
 */
public class NewsList {

    // TODO: 16/8/17  donot forget remove first
    @SerializedName("T1348647909107")
    ArrayList<TopsItem> list;
    public ArrayList<TopsItem> getList() {
        return list;
    }
    public void setList(ArrayList<TopsItem> list) {
        this.list = list;
    }
}
