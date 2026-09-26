package com.xiajun.http.core

import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
                    // .header("Authorization", "Bearer ${UserInfo.token}")
                    .build()
                chain.proceed(request)
            }
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

    /** 构造 POST JSON Request */
    fun postJson(url: String, json: String): Request =
        Request.Builder().url(url).post(jsonBody(json)).build()

    /** 构造 POST Form Request */
    fun postForm(url: String, params: Map<String, String>): Request =
        Request.Builder().url(url).post(formBody(params)).build()

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
