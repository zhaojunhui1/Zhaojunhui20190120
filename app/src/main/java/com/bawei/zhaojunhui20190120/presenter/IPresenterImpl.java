package com.bawei.zhaojunhui20190120.presenter;

import com.bawei.zhaojunhui20190120.callback.MyCallBack;
import com.bawei.zhaojunhui20190120.model.IModelImpl;
import com.bawei.zhaojunhui20190120.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void pGetRequest(String urlStr, Map<String, String> map, Class clazz) {
        iModel.mGetRequest(urlStr, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDatas(data);
            }

            @Override
            public void OnFails(String error) {

            }
        });
    }

    public void onDetch(){
        if (iView != null){
            iView = null;
        }
        if (iModel != null){
            iModel = null;
        }
    }


}
