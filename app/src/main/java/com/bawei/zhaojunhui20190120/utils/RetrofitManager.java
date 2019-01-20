package com.bawei.zhaojunhui20190120.utils;

import android.content.SyncRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static volatile RetrofitManager retrofitManager;
    private final BaseApis baseApis;

    public static RetrofitManager getInstance(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }


    private RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Apis.BASE_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);

    }

    public void get(String url, Map<String, String> map, HttpLinstener linstener){
        baseApis.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(linstener));
    }

    public Observer getObserver(final HttpLinstener linstener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (linstener != null){
                    linstener.fails(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if (linstener != null){
                        linstener.success(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (mHttpLinstener != null){
                        mHttpLinstener.fails(e.getMessage());
                    }
                }
            }
        };

        return observer;
    }


    HttpLinstener mHttpLinstener;
    public void setHttpLinstener(HttpLinstener httpLinstener){
        this.mHttpLinstener = httpLinstener;
    }
    public interface HttpLinstener{
        void success(String data);
        void fails(String error);
    }


}
