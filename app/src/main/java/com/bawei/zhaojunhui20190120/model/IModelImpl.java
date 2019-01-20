package com.bawei.zhaojunhui20190120.model;

import com.bawei.zhaojunhui20190120.callback.MyCallBack;
import com.bawei.zhaojunhui20190120.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel {


    @Override
    public void mGetRequest(String urlStr, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(urlStr, map, new RetrofitManager.HttpLinstener() {
            @Override
            public void success(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null){
                    myCallBack.OnSuccess(o);
                }
            }

            @Override
            public void fails(String error) {

            }
        });

    }

}
