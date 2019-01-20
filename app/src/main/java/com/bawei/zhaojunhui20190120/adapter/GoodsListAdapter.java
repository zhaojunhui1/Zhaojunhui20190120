package com.bawei.zhaojunhui20190120.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.zhaojunhui20190120.R;
import com.bawei.zhaojunhui20190120.bean.GoodsListBean;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GoodsListBean.DataBean> mData;
    private Context mContext;

    public GoodsListAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(List<GoodsListBean.DataBean> data) {
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    //长按删除

    public void setRemove(int i) {
        mData.remove(i);
        notifyItemRemoved(i);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.title.setText(mData.get(i).getTitle());
        mHolder.price.setText("￥"+mData.get(i).getPrice());
        String[] split = mData.get(i).getImages().split("\\|");
        //mHolder.imageView.setImageURI(split[0]);
        Glide.with(mContext).load(split[0]).into(mHolder.imageView);

        //回调监听
        //点击
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsListener != null){
                    mGoodsListener.OnClick(i, mData.get(i).getPid());
                }
            }
        });
        //长按
        mHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mGoodsListener != null){
                    mGoodsListener.OnLongClick(i);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }
    }

    GoodsListener mGoodsListener;
    public void setGoodsListener(GoodsListener listener){
        this.mGoodsListener = listener;
    }
    public interface GoodsListener{
        void OnLongClick(int i);
        void OnClick(int i, String pid);
    }

}
