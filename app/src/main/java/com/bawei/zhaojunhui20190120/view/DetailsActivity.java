package com.bawei.zhaojunhui20190120.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.zhaojunhui20190120.R;
import com.bawei.zhaojunhui20190120.adapter.DetailPagerAdapter;
import com.bawei.zhaojunhui20190120.bean.DetailsBean;
import com.bawei.zhaojunhui20190120.presenter.IPresenterImpl;
import com.bawei.zhaojunhui20190120.utils.Apis;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements IView {
    private IPresenterImpl iPresenter;
    private ImageView handPic, shared;
    private ViewPager viewPager;
    private TextView title, price, login;
    private Button addCar;
    private String pid;
    private DetailPagerAdapter imgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        pid = getIntent().getStringExtra("pid");
        initView();
        initData();

    }

    //初始化View
    private void initView() {
        iPresenter = new IPresenterImpl(this);
        viewPager = findViewById(R.id.viewPager);
        handPic = findViewById(R.id.handPic);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        addCar = findViewById(R.id.addCar);
        shared = findViewById(R.id.shared);
        login = findViewById(R.id.login);

        //轮播适配
        imgAdapter = new DetailPagerAdapter(this);
        viewPager.setAdapter(imgAdapter);
        //点击
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //第三方qq登录


    }


    //拼接接口
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        iPresenter.pGetRequest(Apis.GET_DETAILS_URL, map, DetailsBean.class);
    }


    //返回的数据
    @Override
    public void viewDatas(Object data) {
        if (data instanceof DetailsBean){
            DetailsBean detailsBean = (DetailsBean) data;

            title.setText(detailsBean.getData().getTitle());
            price.setText("￥"+ detailsBean.getData().getPrice());

            //String[] split = detailsBean.getData().getImages().split("\\|");
            //图片数据
            imgAdapter.setDatas(detailsBean.getData().getImages());

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
