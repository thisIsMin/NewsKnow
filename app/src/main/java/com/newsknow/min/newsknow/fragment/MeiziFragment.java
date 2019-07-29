package com.newsknow.min.newsknow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.adapter.MeiziAdapter;
import com.newsknow.min.newsknow.bean.meizi.MeiziItem;
import com.newsknow.min.newsknow.presenter.IMeiziPresenter;
import com.newsknow.min.newsknow.presenter.impPresenter.MeiziPresenterImp;
import com.newsknow.min.newsknow.presenter.impView.IMeiziFragment;
import com.newsknow.min.newsknow.util.WrapContentGManager;
import com.newsknow.min.newsknow.util.WrapContentLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiziFragment extends BaseFragment implements IMeiziFragment {
    @BindView(R.id.recycle_meizi)
    RecyclerView mRecycleMeizi;
    @BindView(R.id.prograss)
    ProgressBar mPrograss;

    //private WrapContentLinearLayoutManager linearLayoutManager;
    private WrapContentGManager mManager;
    private MeiziAdapter meiziAdapter;
    private RecyclerView.OnScrollListener loadmoreListener;
    private IMeiziPresenter mMeiziPresenter;

    private boolean isLoading;

    private int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meizi_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mMeiziPresenter = new MeiziPresenterImp(getContext(), this);

        meiziAdapter = new MeiziAdapter(getContext());
        //linearLayoutManager = new WrapContentLinearLayoutManager(getContext());

        mManager = new WrapContentGManager(this.getContext(),2);
        mManager.setOrientation(GridLayoutManager.VERTICAL);

        loadmoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //向下滚动
                {
//                    int visibleItemCount = linearLayoutManager.getChildCount();
//                    int totalItemCount = linearLayoutManager.getItemCount();
//                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    int visibleItemCount = mManager.getChildCount();
                    int totalItemCount = mManager.getItemCount();
                    int pastVisiblesItems = mManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        index += 1;
                        loadMoreDate();
                    }
                }
            }
        };
        mRecycleMeizi.setLayoutManager(mManager);
        mRecycleMeizi.setAdapter(meiziAdapter);
        mRecycleMeizi.addOnScrollListener(loadmoreListener);
//        new Once(getContext()).show("tip_guide_6", new Once.OnceCallback() {
//            @Override
//            public void onOnce() {
//                Snackbar.make(mRecycleMeizi, getString(R.string.meizitips), Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.meiziaction, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                            }
//                        })
//                        .show();
//            }
//        });
        mRecycleMeizi.setItemAnimator(new DefaultItemAnimator());

        loadDate();

    }

    private void loadDate() {
        if (meiziAdapter.getItemCount() > 0) {
            meiziAdapter.clearData();
        }
        mMeiziPresenter.getMeizi(index);

    }

    private void loadMoreDate() {
        meiziAdapter.loadingStart();
        mMeiziPresenter.getMeizi(index);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMeiziPresenter.unsubscrible();
    }

    @Override
    public void updateMeiziData(ArrayList<MeiziItem> meiziItems) {
        meiziAdapter.loadingfinish();
        isLoading = false;
        meiziAdapter.addItems(meiziItems);
        //mMeiziPresenter.getVedioData(index);

        if (!this.mRecycleMeizi.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }

    @Override
    public void showProgressDialog() {
        if (mPrograss != null) {
            mPrograss.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hidProgressDialog() {
        if (mPrograss!= null) {
            mPrograss.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String error) {
        Log.i("testtt","meizifragment"+error);
    }
}
