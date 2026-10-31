package com.xiajun.data.http.function.impl;

import com.xiajun.config.AssembleConfig
import com.xiajun.data.http.request.LoginRequest
import com.xiajun.data.http.response.LoginBean
import com.xiajun.data.http.response.TokenResponse
import com.xiajun.entity.UserInfo
import com.xiajun.http.core.ApiResp
import com.xiajun.http.core.NetClient
import okhttp3.ResponseBody

object UserNetSourceImpl {
    private const val BASE_URL = AssembleConfig.URL_BASE

    suspend fun login(userName: String, password: String) : ApiResp<LoginBean> {
        return NetClient.postForm(
            "$BASE_URL/app/changePsd.html",
            mapOf("userName" to userName
                ,"password" to password)
        )
    }

    suspend fun getToken() : ApiResp<TokenResponse> {
        return NetClient.postForm(
            "https://192.168.0.3/OAuth/Token",
            mapOf("grant_type" to "client_credentials")
        )
    }

    suspend fun login2(userName: String, passWord: String) : ApiResp<UserInfo>{
        return NetClient.postJsonT("https://192.168.0.3/OAuth/Token", LoginRequest(userName, passWord))
    }

    suspend fun test() : ApiResp<ResponseBody>{
        return NetClient.postForm(
            "$BASE_URL/enbdvytbvfs",
            mapOf("grant_type" to "client_credentials")
        )
    }
}
