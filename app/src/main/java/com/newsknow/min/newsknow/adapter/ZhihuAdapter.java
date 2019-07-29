package com.newsknow.min.newsknow.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.activity.ZhihuActivity;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDailyItem;
import com.newsknow.min.newsknow.util.Config;
import com.newsknow.min.newsknow.util.DBUtils;
import com.newsknow.min.newsknow.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class ZhihuAdapter extends RecyclerView.Adapter<ZhihuAdapter.ZhihuViewHolder>{
    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NORMAL = 1;

    private boolean loadingMore;
    private int mImageWidth;
    private int mImageHeigh;
    private ArrayList<ZhihuDailyItem> zhihuDailyItems =new ArrayList<>();
    //private List<String> zhihuDailyItems=new ArrayList<>();
    private Context mContext;

    private View inflater;

    public ZhihuAdapter(Context context) {

        Log.i("testtt","zhihuadapter");
        this.mContext = context;

        float width = mContext.getResources().getDimension(R.dimen.image_width);
        mImageWidth = DensityUtil.dip2px(mContext, width);
        mImageHeigh = mImageWidth * 3 / 4;


    }

    @NonNull
    @Override
    public ZhihuAdapter.ZhihuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.i("testtt","oncreateviewholder  "+viewType+"size"+getItemCount());
        inflater = LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item,viewGroup,false);
        ZhihuViewHolder zhihuViewHolder=new ZhihuViewHolder(inflater);
        return  zhihuViewHolder;
        //return new ZhihuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item, null));
//        switch (viewType) {
//            case TYPE_NORMAL:
//                return new ZhihuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item, viewGroup, false));
//
//            case TYPE_LOADING_MORE:
//                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading, viewGroup, false));
//
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ZhihuAdapter.ZhihuViewHolder holder, int position) {
        //final ZhihuDailyItem zhihuDailyItem = zhihuDailyItems.get(position);
        //holder.textView.setText(zhihuDailyItems.get(position).getTitle());
        bindViewHolderNormal( holder, position);
//        int type = getItemViewType(position);
//        switch (type) {
//            case TYPE_NORMAL:
//                bindViewHolderNormal((ZhihuViewHolder) holder, position);
//                break;
//            case TYPE_LOADING_MORE:
//                bindLoadingViewHold((LoadingMoreHolder) holder, position);
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return this.zhihuDailyItems.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position < getDataItemCount()
//                && getDataItemCount() > 0) {
//
//            return TYPE_NORMAL;
//        }
//        return TYPE_LOADING_MORE;
//    }

//    private int getDataItemCount() {
//
//        return zhihuDailyItems.size();
//    }

//    private void bindLoadingViewHold(LoadingMoreHolder holder, int position) {
//        holder.progressBar.setVisibility(loadingMore == true ? View.VISIBLE : View.INVISIBLE);
//    }

    private void bindViewHolderNormal(final ZhihuViewHolder holder, final int position) {

        Log.i("testtt","bind  "+position);
        final ZhihuDailyItem zhihuDailyItem = zhihuDailyItems.get(position);

        if (DBUtils.getDB(mContext).isRead(Config.ZHIHU, zhihuDailyItem.getId(), 1))
            holder.textView.setTextColor(Color.GRAY);
        else
            holder.textView.setTextColor(Color.BLACK);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDescribeActivity(holder, zhihuDailyItem);

            }
        });
        //holder.imageView.setBackgroundResource(R.drawable.ic_color_lens_white_24dp);
        holder.textView.setText(zhihuDailyItem.getTitle());
        holder.linearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDescribeActivity(holder, zhihuDailyItem);
                    }
                });


        Glide.with(mContext)
                .load(zhihuDailyItems.get(position).getImages()[0])
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if (!zhihuDailyItem.hasFadedIn) {
//                            holder.imageView.setHasTransientState(true);
//                            final ObservableColorMatrix cm = new ObservableColorMatrix();
//                            final ObjectAnimator animator = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f);
//                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                @Override
//                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                                    holder.imageView.setColorFilter(new ColorMatrixColorFilter(cm));
//                                }
//                            });
//                            animator.setDuration(2000L);
//                            animator.setInterpolator(new AccelerateInterpolator());
//                            animator.addListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    holder.imageView.clearColorFilter();
//                                    holder.imageView.setHasTransientState(false);
//                                    animator.start();
//                                    zhihuDailyItem.hasFadedIn = true;
//
//                                }
//                            });
//                        }

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(mImageWidth, mImageHeigh)
                .into(new GlideDrawableImageViewTarget(holder.imageView));


    }

    public void addItems(ArrayList<ZhihuDailyItem> list) {

        zhihuDailyItems.addAll(list);
        notifyDataSetChanged();
    }
   // @Override
    public void loadingStart() {
        if (loadingMore) return;
        loadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    public void loadingfinish() {
        if (!loadingMore) return;
        final int loadingPos = getLoadingMoreItemPosition();
        loadingMore = false;
        notifyItemRemoved(loadingPos);
    }

    public void clearData() {
        zhihuDailyItems.clear();
        notifyDataSetChanged();
    }

    private int getLoadingMoreItemPosition() {
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    private void startDescribeActivity(ZhihuViewHolder holder, ZhihuDailyItem zhihuDailyItem) {

        DBUtils.getDB(mContext).insertHasRead(Config.ZHIHU, zhihuDailyItem.getId(), 1);
        holder.textView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, ZhihuActivity.class);
        intent.putExtra("id", zhihuDailyItem.getId());
        intent.putExtra("title", zhihuDailyItem.getTitle());
        intent.putExtra("image", zhihuDailyItem.getImages());


        mContext.startActivity(intent);
    }

//    private static class LoadingMoreHolder extends RecyclerView.ViewHolder {
//        ProgressBar progressBar;
//
//        public LoadingMoreHolder(View itemView) {
//            super(itemView);
//            progressBar = (ProgressBar) itemView;
//        }
//    }


    class ZhihuViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;
        ImageView imageView;

        public ZhihuViewHolder(View itemView) {
            super(itemView);
            Log.i("testtt","zhuhuviewholder  ");
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
            textView = (TextView) itemView.findViewById(R.id.item_text_id);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.zhihu_item_layout);
        }
    }
}
