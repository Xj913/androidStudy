package com.xiajun.http.core

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.xiajun.data.prefs.AppPrefsManager
import com.xiajun.lib.common.BuildConfig
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object NetClient {

    private const val CONNECT_TIMEOUT = 15L
    private const val READ_TIMEOUT = 15L
    private const val WRITE_TIMEOUT = 15L

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(NetInterceptors.loggingInterceptor)
            .addInterceptor(NetInterceptors.headerInterceptor)
            .build()
    }

    /** 构造 JSON RequestBody */
    fun jsonBody(json: String): RequestBody = json.toRequestBody(JSON)

    /** 构造 Form RequestBody */
    fun formBody(params: Map<String, String>): RequestBody {
        val builder = FormBody.Builder()
        params.forEach { (k, v) -> builder.add(k, v) }
        return builder.build()
    }

    fun postJsonT(url: String, bean: Any): Request =
        postJson(url, Gson().toJson(bean))

    /** 构造 POST JSON Request */
    fun postJson(url: String, json: String): Request =
        Request.Builder().url(url).post(jsonBody(json)).build()

    /** 构造 POST Form Request */
    fun postForm(url: String, params: Map<String, String>): Request =
        Request.Builder().url(url).post(formBody(params)).build()

    /** 构造 GET Request */
    fun get(url: String, params: Map<String, String>? = null): Request {
        val finalUrl = if (params.isNullOrEmpty()) {
            url
        } else {
            val httpUrl = url.toHttpUrl().newBuilder()
            params.forEach { (k, v) -> httpUrl.addQueryParameter(k, v) }
            httpUrl.build().toString()
        }
        return Request.Builder().url(finalUrl).get().build()
    }

    /** 构造 PUT JSON Request */
    fun putJson(url: String, json: String): Request =
        Request.Builder().url(url).put(jsonBody(json)).build()

    /** 构造 DELETE Request */
    fun delete(url: String, json: String? = null): Request {
        val builder = Request.Builder().url(url)
        if (json != null) builder.delete(jsonBody(json)) else builder.delete()
        return builder.build()
    }
}

object NetInterceptors {
    val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor({ message: String? ->
            if (BuildConfig.DEBUG) Log.e("okhttp", message!!)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val headerInterceptor: Interceptor by lazy {
        Interceptor { chain ->
            val original = chain.request()
            val newBuilder = original.newBuilder()
            if (TextUtils.isEmpty(original.header("Authorization")))  //这里没打印Authorization因为执行在日志拦截后
                newBuilder.addHeader(
                    "Authorization",
                    AppPrefsManager.getInstance().signKey
                )
            //String language = Locale.getDefault().getLanguage();//服务器根据不同语言返回不同描述
            val newRequest = newBuilder.build()
            chain.proceed(newRequest)
        }
    }
}