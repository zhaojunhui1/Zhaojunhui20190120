package com.bawei.zhaojunhui20190120.view;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.myapplication.greendao.DaoMaster;
import com.bawei.myapplication.greendao.DaoSession;
import com.bawei.myapplication.greendao.TextBeanDao;
import com.bawei.zhaojunhui20190120.R;
import com.bawei.zhaojunhui20190120.adapter.GoodsListAdapter;
import com.bawei.zhaojunhui20190120.bean.GoodsListBean;
import com.bawei.zhaojunhui20190120.presenter.IPresenterImpl;
import com.bawei.zhaojunhui20190120.utils.Apis;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private IPresenterImpl iPresenter;
    private RecyclerView recyclerView;
    private GoodsListAdapter mAdapter;
    private Button address;
    private TextBeanDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        dataBase();
    }

    //greenDao
    private void dataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mDao = daoSession.getTextBeanDao();

    }

    //初始化View
    private void initView() {
        iPresenter = new IPresenterImpl(this);
        recyclerView = findViewById(R.id.recycleView);
        address = findViewById(R.id.address);

        //点击展示地图
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddressActivity.class));
            }
        });

    }

    //拼接接口数据
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("keywords", "手机");
        map.put("page", "1");
        iPresenter.pGetRequest(Apis.GET_SEARCH_URL, map, GoodsListBean.class);

        //创建适配器
        mAdapter = new GoodsListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //动画
      /*  ObjectAnimator animator = new ObjectAnimator();
        animator.start();*/
        //监听
        mAdapter.setGoodsListener(new GoodsListAdapter.GoodsListener() {
            @Override
            public void OnLongClick(int i) {
                mAdapter.setRemove(i);
            }

            @Override
            public void OnClick(int i, String pid) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("pid", pid);
                startActivity(intent);
                Toast.makeText(MainActivity.this, pid, Toast.LENGTH_SHORT).show();
            }
        });
        DividerItemDecoration decoration = new DividerItemDecoration(this, OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(decoration);

    }


    //返回来的数据
    @Override
    public void viewDatas(Object data) {
        if (data instanceof GoodsListBean){
            GoodsListBean listBean = (GoodsListBean) data;
           // System.out.println(listBean.getMsg());
            mAdapter.setDatas(listBean.getData());

            //加入greenDao
          /*  for (int i = 0; i < listBean.getData().size(); i++) {
                mDao.insertOrReplace(listBean.getData());
            }*/

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
