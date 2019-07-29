package com.newsknow.min.newsknow.fragment;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.adapter.TestAdapter;
import com.newsknow.min.newsknow.adapter.ZhihuAdapter;
import com.newsknow.min.newsknow.bean.zhihu.TestBean;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDaily;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDailyItem;
import com.newsknow.min.newsknow.presenter.impPresenter.ZhihuPresenterImp;
import com.newsknow.min.newsknow.presenter.impView.IZhihuFragment;
import com.newsknow.min.newsknow.util.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuFragment extends BaseFragment implements IZhihuFragment {
    ZhihuPresenterImp zhihuPresenter;
    @BindView(R.id.recycle_zhihu)
    RecyclerView recycle;
    @BindView(R.id.prograss)
    ProgressBar progress;

    View view = null;
    ZhihuAdapter zhihuAdapter;
    LinearLayoutManager mLinearLayoutManager;
    boolean connected = true;
    RecyclerView.OnScrollListener loadingMoreListener;
    boolean loading;
    private String currentLoadDate;

    //TestAdapter testAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        view = inflater.inflate(R.layout.zhihu_fragment, container, false);
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
        zhihuPresenter = new ZhihuPresenterImp(getContext(), this);
        //ArrayList<ZhihuDailyItem> list=new ArrayList<>();
//        String z ="sfkjgkahfjgaggg";
//        String z1 ="sfkjgkahfjgsdfffaggg";
//        String z2 ="sshhjjjgjjjjjgg";
//        String z3 ="sfkvbnndrssgg";
//        String z4 ="sfkjg57gsgsfhgssdf6ygg";

//        TestBean z=new TestBean();
//        z.setA("dgklsgkjgg");
//        z.setB("dgkjhuiaghghau");
//        TestBean z=new TestBean();
//        z.setA("dgklsgkjgg");
//        z.setB("dgkjhuiaghghau");
//        TestBean z=new TestBean();
//        z.setA("dgklsgkjgg");
//        z.setB("dgkjhuiaghghau");
//        TestBean z=new TestBean();
//        z.setA("dgklsgkjgg");
//        z.setB("dgkjhuiaghghau");
//        TestBean z=new TestBean();
//        z.setA("dgklsgkjgg");
//        z.setB("dgkjhuiaghghau");
//        ZhihuDailyItem z=new ZhihuDailyItem();
//        z.setTitle("111");
//        //z.setDate("sfgseedfsfddd");
//
//        ZhihuDailyItem z1=new ZhihuDailyItem();
//        z1.setTitle("111111");
//        //z.setDate("sfgseedfsfddd");
//
//        ZhihuDailyItem z2=new ZhihuDailyItem();
//        z2.setTitle("111222");
//        //z.setDate("sfgseedfsfddd");
//
//        ZhihuDailyItem z3=new ZhihuDailyItem();
//        z3.setTitle("1113fffffff33");
//        //z.setDate("sfgseedfsfddd");

//        ZhihuDailyItem z4=new ZhihuDailyItem();
//        z4.setTitle("dfdhdhhgjfjfffff111444");
//        //z.setDate("sfgseedfsfddd");
//
//        list.add(z);
//        list.add(z1);
//        list.add(z2);
//        list.add(z3);
//        list.add(z4);
        zhihuAdapter = new ZhihuAdapter(getContext());
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
        recycle.setLayoutManager(mLinearLayoutManager);
        recycle.setHasFixedSize(true);
        //recycle.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        // TODO: 16/8/13 add  animation
        //recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(zhihuAdapter);
        recycle.setNestedScrollingEnabled(false);
        recycle.addOnScrollListener(loadingMoreListener);
        if (connected) {
            loadDate();
        }


    }

    private void loadDate() {
        Log.i("testtt","loaddata");
        if (zhihuAdapter.getItemCount() > 0) {
            zhihuAdapter.clearData();
        }
        currentLoadDate = "0";
        zhihuPresenter.getLastZhihuNews();

    }
    private void loadMoreDate() {
        zhihuAdapter.loadingStart();
        zhihuPresenter.getTheDaily(currentLoadDate);
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


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            connectivityCallback = new ConnectivityManager.NetworkCallback() {
//                @Override
//                public void onAvailable(Network network) {
//                    connected = true;
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            noConnectionText.setVisibility(View.GONE);
//                            loadDate();
//                        }
//                    });
//                }
//
//                @Override
//                public void onLost(Network network) {
//                    connected = false;
//                }
//            };
        //   }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zhihuPresenter.unsubscrible();

    }

    @Override
    public void updateList(ZhihuDaily zhihuDaily) {
        Log.i("testtt","zhihuDaily.getDate()");
        Log.i("testtt",zhihuDaily.getDate());
        Log.i("testtt",String.valueOf(zhihuDaily.getStories().size()));
        Log.i("testtt",zhihuDaily.getStories().get(0).getTitle());
        if (loading) {
            loading = false;
            zhihuAdapter.loadingfinish();
        }
        currentLoadDate = zhihuDaily.getDate();
        zhihuAdapter.addItems(zhihuDaily.getStories());
//        if the new data is not full of the screen, need load more data
        if (!recycle.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }

    @Override
    public void showProgressDialog() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hidProgressDialog() {
        if (progress != null) {
            progress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String error) {

        Log.i("testtt",error);
        //Toast.makeText(this.getContext(),error,Toast.LENGTH_LONG);
    }
}
