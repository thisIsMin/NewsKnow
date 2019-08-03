package com.newsknow.min.newsknow.bean.tops;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsList {

    @SerializedName("T1348647909107")
    ArrayList<TopsItem> list;
    public ArrayList<TopsItem> getList() {
        return list;
    }
    public void setList(ArrayList<TopsItem> list) {
        this.list = list;
    }
}
