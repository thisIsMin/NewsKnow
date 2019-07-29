package com.newsknow.min.newsknow.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.newsknow.min.newsknow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiziActivity extends AppCompatActivity {

    private String mImageUrl;
    @BindView(R.id.shot)
    ImageView mDragPhotoView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.background)
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_picture);
        Log.i("testtt","meiziactivity1  "+mImageUrl);
        ButterKnife.bind(this);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Log.i("testtt","meiziactivity2  "+mImageUrl);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandImageAndFinish();
            }
        });
        mToolbar.setAlpha(0.3f);

        mImageUrl = getIntent().getStringExtra("imageUrl");
        Log.i("testtt","meiziactivity  "+mImageUrl);
        Glide.with(this)
                .load(mImageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mDragPhotoView);

//        initialPhotoAttacher();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().getSharedElementEnterTransition().addListener(mListener);
//            getWindow().setSharedElementEnterTransition(new ChangeBounds());
////            setStatusColor();
//        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_picture);
        Log.i("testtt","meiziactivity1  "+mImageUrl);
        ButterKnife.bind(this);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Log.i("testtt","meiziactivity2  "+mImageUrl);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandImageAndFinish();
            }
        });
        mToolbar.setAlpha(0.3f);

        mImageUrl = getIntent().getStringExtra("imageUrl");
        Log.i("testtt","meiziactivity  "+mImageUrl);
        Glide.with(this)
                .load(mImageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mDragPhotoView);

//        initialPhotoAttacher();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().getSharedElementEnterTransition().addListener(mListener);
//            getWindow().setSharedElementEnterTransition(new ChangeBounds());
////            setStatusColor();
//        }
    }

    private void expandImageAndFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }
}
