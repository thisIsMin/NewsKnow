package com.newsknow.min.newsknow.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.newsknow.min.newsknow.R;
import com.newsknow.min.newsknow.activity.MeiziActivity;
import com.newsknow.min.newsknow.bean.meizi.MeiziItem;

import java.util.ArrayList;

public class MeiziAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean loadingMore;

    private ArrayList<MeiziItem> meiziItems = new ArrayList<>();
    private Context mContext;

    public MeiziAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MeiziViewHolder(LayoutInflater.from(mContext).inflate(R.layout.meizi_layout_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MeiziViewHolder holder=(MeiziViewHolder) viewHolder;
        final MeiziItem meizi = meiziItems.get(holder.getAdapterPosition());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDescribeActivity(meizi,holder);
            }
        });

        holder.textView.setText(meizi.getType());
        Glide.with(mContext)
                .load(meizi.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(holder.imageView));

    }

    private void startDescribeActivity(MeiziItem meiziItem,RecyclerView.ViewHolder holder){

        Intent intent = new Intent(mContext, MeiziActivity.class);
        //int location[] = new int[2];

        //BadgedFourThreeImageView imageView=((MeiziViewHolder)holder).getBitmap();
        //imageView.getLocationOnScreen(location);
        //intent.putExtra("left", location[0]);
        //intent.putExtra("top", location[1]);
        //intent.putExtra("height", imageView.getHeight());
        //intent.putExtra("width", imageView.getWidth());

        intent.putExtra("imageUrl",meiziItem.getUrl());
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//
//            final android.support.v4.util.Pair<View, String>[] pairs = Help.createSafeTransitionParticipants
//                    ((Activity) mContext, false,new android.support.v4.util.Pair<>(((MeiziViewHolder)holder).imageView, mContext.getString(R.string.meizi)));
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, pairs);
//            mContext.startActivity(intent, options.toBundle());
//        }else {
//            mContext.startActivity(intent);
//        }
        Log.i("testtt","startmeiziactivity  "+meiziItem.getUrl());
        mContext.startActivity(intent);
    }

    public void addItems(ArrayList<MeiziItem> list) {
        meiziItems.addAll(list);
        notifyDataSetChanged();
    }

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

    private int getLoadingMoreItemPosition() {
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void clearData() {
        meiziItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.meiziItems.size();
    }

    public class MeiziViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public MeiziViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
            textView = (TextView) itemView.findViewById(R.id.item_text_id);
        }
    }
}
