package com.newsknow.min.newsknow.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
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
import com.newsknow.min.newsknow.bean.zhihu.ZhihuStory;
import com.newsknow.min.newsknow.presenter.IZhihuStoryPresenter;
import com.newsknow.min.newsknow.presenter.impPresenter.ZhihuStoryPresenterImp;
import com.newsknow.min.newsknow.presenter.impView.IZhihuStory;
import com.newsknow.min.newsknow.util.Config;
import com.newsknow.min.newsknow.util.DensityUtil;
import com.newsknow.min.newsknow.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuActivity extends AppCompatActivity implements IZhihuStory {

    @BindView(R.id.shot)
    ImageView mShot;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.wv_zhihu)
    WebView wvZhihu;
    @BindView(R.id.nest)
    NestedScrollView mNest;
    @BindView(R.id.title)
    TextView mTranslateYTextView;

    int[] mDeviceInfo;
    int width;
    int heigh;

    private String id;
    private String title;
    String mImageUrl;
    IZhihuStoryPresenter mIZhihuStoryPresenter;

    private String url;
    boolean isEmpty;
    String mBody;
    String[] scc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihudescribe);
        ButterKnife.bind(this);
        mDeviceInfo = DensityUtil.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width * 3 / 4;
        setSupportActionBar(mToolbar);
        initlistenr();
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

    private void initlistenr() {
//        zhihuReturnHomeListener =
//                new AnimUtils.TransitionListenerAdapter() {
//                    @Override
//                    public void onTransitionStart(Transition transition) {
//                        super.onTransitionStart(transition);
//                        // hide the fab as for some reason it jumps position??  TODO work out why
//                        mToolbar.animate()
//                                .alpha(0f)
//                                .setDuration(100)
//                                .setInterpolator(new AccelerateInterpolator());
//                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//                            mShot.setElevation(1f);
//                            mToolbar.setElevation(0f);
//                        }
//                        mNest.animate()
//                                .alpha(0f)
//                                .setDuration(50)
//                                .setInterpolator(new AccelerateInterpolator());
//                    }
//                };
//        scrollListener = new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (oldScrollY<168){
//                    mShot.setOffset(-oldScrollY);
//                    mTranslateYTextView.setOffset(-oldScrollY);
//                }
//
//            }
//        };
    }

    protected void initData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mImageUrl = getIntent().getStringExtra("image");
        mIZhihuStoryPresenter = new ZhihuStoryPresenterImp(this);
//        mNest.setOnScrollChangeListener(scrollListener);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//            postponeEnterTransition();
//            mShot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @Override
//                public boolean onPreDraw() {
//                    mShot.getViewTreeObserver().removeOnPreDrawListener(this);
//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//                        startPostponedEnterTransition();
//                    }
//                    return true;
//                }
//            });
//        }


    }

    private void initView() {
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
                expandImageAndFinish();
            }
        });
        mTranslateYTextView.setText(title);

        WebSettings settings = wvZhihu.getSettings();
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
        wvZhihu.setWebChromeClient(new WebChromeClient());

    }


    private void getData() {
        mIZhihuStoryPresenter.getZhihuStory(id);

    }

    private void expandImageAndFinish() {
//        if (mShot.getOffset() != 0f) {
//            Animator expandImage = ObjectAnimator.ofFloat(mShot, ParallaxScrimageView.OFFSET,
//                    0f);
//            expandImage.setDuration(80);
//            expandImage.setInterpolator(new AccelerateInterpolator());
//            expandImage.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//
//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//                        finishAfterTransition();
//                    }else {
//                        finish();
//                    }
//                }
//            });
//            expandImage.start();
//        } else {
//            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//                finishAfterTransition();
//            }else {
//                finish();
//            }
//        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {

        //webview内存泄露
        if (wvZhihu != null) {
            ((ViewGroup) wvZhihu.getParent()).removeView(wvZhihu);
            wvZhihu.destroy();
            wvZhihu = null;
        }
        mIZhihuStoryPresenter.unsubscrible();
        super.onDestroy();

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showZhihuStory(ZhihuStory zhihuStory) {
        Glide.with(this)
                .load(zhihuStory.getImage()).centerCrop()
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
                .into(mShot);
        url = zhihuStory.getShareUrl();
        isEmpty=TextUtils.isEmpty(zhihuStory.getBody());
        mBody=zhihuStory.getBody();
        //Log.i("testtt",String.valueOf(isEmpty));
        scc=zhihuStory.getCss();
        //wvZhihu.loadUrl(url);
        if (isEmpty) {
            wvZhihu.loadUrl(url);
        } else {
            String data = WebUtil.buildHtmlWithCss(mBody, scc, Config.isNight);
            wvZhihu.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING,null);
        }

    }
}
