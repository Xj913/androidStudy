package com.style.http.core;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.style.config.AssembleConfig;
import com.style.data.prefs.AppPrefsManager;
import com.style.http.converter.CustomConverterFactory;
import com.style.http.response.MyHttpException;
import com.style.lib.common.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public final class RetrofitImpl {
    private String TAG = this.getClass().getSimpleName();
    private static final long HTTP_TIME_OUT = 5;
    private final OkHttpClient mOkHttpClient;
    private static final Object mLock = new Object();
    private static RetrofitImpl mInstance;

    public static RetrofitImpl getInstance() {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new RetrofitImpl();
            }
            return mInstance;
        }
    }

    private RetrofitImpl() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();
    }

    public Retrofit getDefaultRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AssembleConfig.URL_BASE)
                .addConverterFactory(CustomConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }

    //日志拦截器
    public static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
        if (BuildConfig.DEBUG)
            Log.e("okhttp", message);
    }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别


    public static Interceptor headerInterceptor = new Interceptor() {
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder newBuilder = original.newBuilder();
            if (TextUtils.isEmpty(original.header("Authorization")))//这里没打印Authorization因为执行在日志拦截后
                newBuilder.addHeader("Authorization", AppPrefsManager.getInstance().getSignKey());
            //String language = Locale.getDefault().getLanguage();//服务器根据不同语言返回不同描述
            Request newRequest = newBuilder.build();
            Response s = chain.proceed(newRequest);
            if (s.code() != 200)
                throw new MyHttpException(s.code());
            return s;
        }
    };
}
