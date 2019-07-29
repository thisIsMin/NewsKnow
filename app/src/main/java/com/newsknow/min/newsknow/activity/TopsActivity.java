package com.newsknow.min.newsknow.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.bean.tops.TopsStory;
import com.newsknow.min.newsknow.presenter.impPresenter.TopsPresenterImp;
import com.newsknow.min.newsknow.presenter.impView.ITopsStory;
import com.newsknow.min.newsknow.util.Config;
import com.newsknow.min.newsknow.util.DensityUtil;
import com.newsknow.min.newsknow.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopsActivity extends AppCompatActivity implements ITopsStory {
    @BindView(R.id.shot)
    ImageView mImage;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.wv_zhihu)
    WebView mWebView;
    @BindView(R.id.nest)
    NestedScrollView mNest;
    @BindView(R.id.title)
    TextView mTextView;

    int[] mDeviceInfo;
    int width;
    int heigh;

    TopsPresenterImp mTopsPresenter;
    private String id;
    private String title;
    private String url;
    String mImageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihudescribe);
        ButterKnife.bind(this);
        mDeviceInfo = DensityUtil.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width * 3 / 4;
        setSupportActionBar(mToolbar);
        //initlistenr();
        initData();
        initView();
        getData();

        //chromeFader = new ElasticDragDismissFrameLayout.SystemChromeFader(this);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//
//            getWindow().getSharedElementReturnTransition().addListener(zhihuReturnHomeListener);
//            getWindow().setSharedElementEnterTransition(new ChangeBounds());
//        }

        //enterAnimation();
    }

    private void initData(){
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mImageUrl = getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");
        //this.mTopsPresenter=new TopsPresenterImp(this);
    }

    private void initView(){
        mToolbar.setTitleMargin(20,20,0,10);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNest.smoothScrollTo(0,0);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }else {
                    finish();
                }
            }
        });
        mTextView.setText(title);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());

    }

    private void getData(){
        //this.mTopsPresenter.getTopstory(id);
        Glide.with(this)
                .load(this.mImageUrl).centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).override(width,heigh)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(this.mImage);
        //url = zhihuStory.getShareUrl();
        //isEmpty=TextUtils.isEmpty(zhihuStory.getBody());
        //mBody=zhihuStory.getBody();
        //Log.i("testtt",String.valueOf(isEmpty));
        //scc=zhihuStory.getCss();
        //wvZhihu.loadUrl(url);
        //if (isEmpty) {
            mWebView.loadUrl(url);
        //} else {
            //String data = WebUtil.buildHtmlWithCss(mBody, scc, Config.isNight);
            //wvZhihu.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING,null);
        //}
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showtory(TopsStory topsStory) {

    }
}
