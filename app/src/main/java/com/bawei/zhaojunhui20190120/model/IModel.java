package com.bawei.zhaojunhui20190120.model;

import com.bawei.zhaojunhui20190120.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mGetRequest(String urlStr, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
