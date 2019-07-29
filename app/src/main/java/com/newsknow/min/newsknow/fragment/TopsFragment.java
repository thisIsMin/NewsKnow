package com.newsknow.min.newsknow.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.adapter.TopsAdapter;
import com.newsknow.min.newsknow.adapter.ZhihuAdapter;
import com.newsknow.min.newsknow.bean.tops.TopsItem;
import com.newsknow.min.newsknow.presenter.ITopsPresenter;
import com.newsknow.min.newsknow.presenter.impPresenter.TopsPresenterImp;
import com.newsknow.min.newsknow.presenter.impPresenter.ZhihuPresenterImp;
import com.newsknow.min.newsknow.presenter.impView.ITopsFragment;
import com.newsknow.min.newsknow.util.WrapContentLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopsFragment extends BaseFragment implements ITopsFragment {
    ITopsPresenter iTopsPresenter;
    @BindView(R.id.recycle_tops)
    RecyclerView mRecycle;
    @BindView(R.id.prograss)
    ProgressBar mProgress;

    private TopsAdapter mTopsAdapter;
    RecyclerView.OnScrollListener loadingMoreListener;
    boolean connected = true;
    LinearLayoutManager mLinearLayoutManager;
    boolean loading;
    int i=0;
    //String mCurrentLoadDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        View view = inflater.inflate(R.layout.tops_fragment, container, false);
        //checkConnectivity(view);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialDate();
        initialView();

    }

    private void initialDate() {
        iTopsPresenter = new TopsPresenterImp(this);

        mTopsAdapter = new TopsAdapter(this.getContext());
        //testAdapter=new TestAdapter(getContext(),list);
    }

    private void initialView() {

        initialListener();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLinearLayoutManager =new WrapContentLinearLayoutManager(getContext());

        } else {
            mLinearLayoutManager = new LinearLayoutManager(getContext());
        }
//        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        mRecycle.setLayoutManager(mLinearLayoutManager);
        mRecycle.setHasFixedSize(true);
        //mRecycle.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        // TODO: 16/8/13 add  animation
        //mRecycle.setItemAnimator(new DefaultItemAnimator());
        mRecycle.setAdapter(mTopsAdapter);
        mRecycle.setNestedScrollingEnabled(false);
        mRecycle.addOnScrollListener(loadingMoreListener);
        if (connected) {
            loadDate();
        }
    }

    private void initialListener() {

        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMoreDate();
                    }
                }
            }
        };

    }

    private void loadDate() {
        if (mTopsAdapter.getItemCount() > 0) {
            mTopsAdapter.clearData();
        }
        //mCurrentLoadDate = "0";
        i=0;
        iTopsPresenter.getNews(i);

    }
    private void loadMoreDate() {
        mTopsAdapter.loadingStart();
        i+=20;
        iTopsPresenter.getNews(i);
    }

    @Override
    public void updateList(ArrayList<TopsItem> topsItems) {
        if (loading) {
            loading = false;
            mTopsAdapter.loadingfinish();
        }
        //mCurrentLoadDate = topsItems.get(0).getDate();
        mTopsAdapter.addItems(topsItems);
//        if the new data is not full of the screen, need load more data
        if (!mRecycle.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }

    @Override
    public void showProgressDialog() {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hidProgressDialog() {
        if (mProgress != null) {
            mProgress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String error) {
        Log.i("testtt",error);
    }
}
