package com.newsknow.min.newsknow.bean.tops;

import com.google.gson.annotations.SerializedName;

public class TopsItem {
    /**
     * docid
     */
    @SerializedName("docid")
    private String docid;
    /**
     * 标题
     */
    @SerializedName("title")
    private String title;
    /**
     * 小内容
     */
    @SerializedName("digest")
    private String digest;
    /**
     * 图片地址
     */
    @SerializedName("imgsrc")
    private String imgsrc;
    /**
     * 来源
     */
    @SerializedName("url")
    private String url;
    /**
     * 时间
     */
    @SerializedName("ptime")
    private String ptime;
    /**
     * TAG
     */
    @SerializedName("tag")
    private String tag;

    public boolean hasFadedIn=false;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
