package com.newsknow.min.newsknow.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.activity.TopsActivity;
import com.newsknow.min.newsknow.activity.ZhihuActivity;
import com.newsknow.min.newsknow.bean.tops.TopsItem;
import com.newsknow.min.newsknow.bean.zhihu.ZhihuDailyItem;
import com.newsknow.min.newsknow.util.Config;
import com.newsknow.min.newsknow.util.DBUtils;
import com.newsknow.min.newsknow.util.DensityUtil;

import java.util.ArrayList;

public class TopsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean loadingMore;
    private int mImageWidth;
    private int mImageHeigh;
    private ArrayList<TopsItem> topsItems =new ArrayList<>();
    //private List<String> zhihuDailyItems=new ArrayList<>();
    private Context mContext;

    public TopsAdapter(Context mContext) {
        this.mContext = mContext;
        float width = mContext.getResources().getDimension(R.dimen.image_width);
        mImageWidth = DensityUtil.dip2px(mContext, width);
        mImageHeigh = mImageWidth * 3 / 4;
    }

    private View inflater;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("testtt","topsadaptercreateviewholder");
        inflater = LayoutInflater.from(mContext).inflate(R.layout.tops_item_layout,viewGroup,false);
        TopsAdapter.TopsViewHolder topsViewHolder=new TopsViewHolder(inflater);
        return  topsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final TopsItem topsItem = topsItems.get(i);
        final TopsViewHolder holder=(TopsViewHolder) viewHolder;

        if (DBUtils.getDB(mContext).isRead(Config.TOPNEWS, topsItem.getDocid(), 1))
            holder.textView.setTextColor(Color.GRAY);
        else
            holder.textView.setTextColor(Color.BLACK);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDescribeActivity(holder, topsItem);

            }
        });
        //holder.imageView.setBackgroundResource(R.drawable.ic_color_lens_white_24dp);
        holder.tibleTextView.setText(topsItem.getTitle());
        holder.textView.setText(topsItem.getDigest());
        holder.linearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDescribeActivity(holder, topsItem);
                    }
                });


        Glide.with(mContext)
                .load(topsItem.getImgsrc())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(mImageWidth, mImageHeigh)
                .into(new GlideDrawableImageViewTarget(holder.imageView));
    }

    @Override
    public int getItemCount() {
        return this.topsItems.size();
    }

    public void clearData() {
        topsItems.clear();
        notifyDataSetChanged();
    }

    public void loadingStart() {
        if (loadingMore) return;
        loadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    private int getLoadingMoreItemPosition() {
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void loadingfinish() {
        if (!loadingMore) return;
        final int loadingPos = getLoadingMoreItemPosition();
        loadingMore = false;
        notifyItemRemoved(loadingPos);
    }

    public void addItems(ArrayList<TopsItem> list) {

        topsItems.addAll(list);
        notifyDataSetChanged();
    }

    private void startDescribeActivity(TopsViewHolder holder, TopsItem topsItem) {

        DBUtils.getDB(mContext).insertHasRead(Config.TOPNEWS, topsItem.getDocid(), 1);
        holder.textView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext,TopsActivity.class);
        intent.putExtra("id", topsItem.getDocid());
        intent.putExtra("title", topsItem.getTitle());
        intent.putExtra("digest",topsItem.getDigest());
        intent.putExtra("image", topsItem.getImgsrc());
        intent.putExtra("url", topsItem.getUrl());


        mContext.startActivity(intent);
    }

    class TopsViewHolder extends RecyclerView.ViewHolder {
        TextView tibleTextView;
        TextView textView;
        LinearLayout linearLayout;
        ImageView imageView;

        public TopsViewHolder(View itemView) {
            super(itemView);
            Log.i("testtt","zhuhuviewholder  ");
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
            tibleTextView=(TextView) itemView.findViewById(R.id.item_text_tible);
            textView = (TextView) itemView.findViewById(R.id.item_text_data);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.zhihu_item_layout);
        }
    }
}
