package com.newsknow.min.newsknow.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newsknow.min.newsknow.R;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {
    private Context context;
    private List<String> list;
    private View inflater;
    //构造方法，传入数据
    public TestAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public TestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.zhihu_layout_item,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.MyViewHolder myViewHolder, int i) {

        //将数据和控件绑定
        myViewHolder.textView1.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        //TextView textView2;
        //ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.item_text_id);
            //textView2 = (TextView) itemView.findViewById(R.id.text2);
            //imageView = (ImageView) itemView.findViewById(R.id.image1);
        }
    }
}
