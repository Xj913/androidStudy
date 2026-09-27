package com.xiajun.data.http.function.impl;


import com.google.gson.Gson
import com.xiajun.config.AssembleConfig
import com.xiajun.data.http.request.LoginRequest
import com.xiajun.data.http.response.LoginBean
import com.xiajun.data.http.response.TokenResponse
import com.xiajun.entity.UserInfo
import com.xiajun.http.core.ApiResp
import com.xiajun.http.core.NetClient
import com.xiajun.http.core.NetRequest
import okhttp3.ResponseBody


object UserNetSourceImpl {
    private const val BASE_URL = AssembleConfig.URL_BASE

    suspend fun login(userName: String, password: String) : ApiResp<LoginBean> {
        val request = NetClient.postForm(
            "$BASE_URL/app/changePsd.html",
            mapOf("userName" to userName
                ,"password" to password)
        )
        return NetRequest.execute(request)
    }

    suspend fun getToken() : ApiResp<TokenResponse> {
        val request = NetClient.postForm(
            "https://192.168.0.3/OAuth/Token",
            mapOf("grant_type" to "client_credentials")
        )
        return NetRequest.execute(request)
    }

    suspend fun login2(userName: String, passWord: String) : ApiResp<UserInfo>{
        val request = NetClient.postJsonT("https://192.168.0.3/OAuth/Token", LoginRequest(userName, passWord))
        return NetRequest.execute(request)
    }

    suspend fun test() : ApiResp<ResponseBody>{
        val request = NetClient.postForm(
            "$BASE_URL/enbdvytbvfs",
            mapOf("grant_type" to "client_credentials")
        )
        return NetRequest.execute(request)
    }
}
