package com.xiajun.http.core

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiajun.data.app.MyAppManager
import com.xiajun.data.prefs.AppPrefsManager
import com.xiajun.http.core.ApiResp.Companion.OK
import com.xiajun.lib.common.BuildConfig
import com.xiajun.toast.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.BufferedSource
import java.io.IOException
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

    fun jsonBody(json: String): RequestBody = json.toRequestBody(JSON)

    fun formBody(params: Map<String, String>): RequestBody {
        val builder = FormBody.Builder()
        params.forEach { (k, v) -> builder.add(k, v) }
        return builder.build()
    }

    suspend inline fun <reified T : Any> postJsonT(url: String, bean: Any): ApiResp<T> = withContext(IO) {
        val r = Request.Builder().url(url).post(jsonBody(Gson().toJson(bean))).build()
        execute(r)
    }

    suspend inline fun <reified T : Any> postJson(url: String, json: String): ApiResp<T> = withContext(IO) {
        val r = Request.Builder().url(url).post(jsonBody(json)).build()
        execute(r)
    }

    suspend inline fun <reified T : Any> postForm(url: String, params: Map<String, String>): ApiResp<T> = withContext(IO) {
        val r = Request.Builder().url(url).post(formBody(params)).build()
        execute(r)
    }

    suspend inline fun <reified T : Any> get(url: String, params: Map<String, String>? = null): ApiResp<T> = withContext(IO) {
        val finalUrl = if (params.isNullOrEmpty()) {
            url
        } else {
            val httpUrl = url.toHttpUrl().newBuilder()
            params.forEach { (k, v) -> httpUrl.addQueryParameter(k, v) }
            httpUrl.build().toString()
        }
        val r = Request.Builder().url(finalUrl).get().build()
        execute(r)
    }

    suspend inline fun <reified T : Any> putJson(url: String, json: String): ApiResp<T> = withContext(IO) {
        val r = Request.Builder().url(url).put(jsonBody(json)).build()
        execute(r)
    }

    suspend inline fun <reified T : Any> delete(url: String, json: String? = null): ApiResp<T> = withContext(IO) {
        val builder = Request.Builder().url(url)
        if (json != null) builder.delete(jsonBody(json)) else builder.delete()
        val r = builder.build()
        execute(r)
    }

    suspend inline fun <reified T : Any> execute(request: Request): ApiResp<T> {
        runCatching {
            okHttpClient.newCall(request).execute()
        }.onSuccess {
            if (it.isSuccessful) {
                if (T::class == ResponseBody::class) {
                    return ApiResp(OK, it.body as T?, it.message)
                }
                val bs: BufferedSource = it.body.source().buffer
                val tempStr = bs.readUtf8()
                bs.close()
                if (T::class == String::class) {
                    return ApiResp(OK, tempStr as T?, it.message)
                }
                val type = object : TypeToken<T>() {}.type
                val t: T = Gson().fromJson(tempStr, type)
                return ApiResp(OK, t, "请求成功")
            }
            withContext(Dispatchers.Main) {
                ToastManager.showToast(MyAppManager.getInstance().app, it.message)
            }
            return netResp(code = it.code, msg = it.message)
        }.onFailure { e ->
            e.printStackTrace()
            if (e is IOException) {
                withContext(Dispatchers.Main) {
                    ToastManager.showToast(MyAppManager.getInstance().app, e.message)
                }
            }
        }
        return netResp(d = null)
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