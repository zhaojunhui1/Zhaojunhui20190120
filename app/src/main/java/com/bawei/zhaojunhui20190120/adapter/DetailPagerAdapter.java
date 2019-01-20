package com.bawei.zhaojunhui20190120.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bawei.zhaojunhui20190120.bean.DetailsBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailPagerAdapter extends PagerAdapter {
    private List<String> mList;
    private Context mContext;

    public DetailPagerAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

  /*  public void setDatas(DetailsBean.DataBean data) {
        if (data != null){
            mList.add(data);
        }
        notifyDataSetChanged();
    }*/
  public void setDatas(String images) {
      mList.add(images);
      notifyDataSetChanged();
  }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        for (int i = 0; i < mList.size(); i++) {
            String[] split = mList.get(i).split("\\|");
            Glide.with(mContext).load(split[position]).into(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
