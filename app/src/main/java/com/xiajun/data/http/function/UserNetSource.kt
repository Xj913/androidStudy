package com.xiajun.data.http.function;

import com.xiajun.data.http.request.LoginRequest
import com.xiajun.data.http.response.LoginBean
import com.xiajun.data.http.response.TokenResponse
import com.xiajun.entity.UserInfo
import com.xiajun.http.core.BaseResp
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserNetSource {

    @POST("/app/changePsd.html")
    suspend fun login(@Field("userName") name: String, @Field("password") password: String) : BaseResp<LoginBean>

    @Headers("Authorization:YW5kcm9pZGNsaWVudDo4QTcyOUZENC04NjdGLTREMTItOEE4Ri1CQTNFOEQ4MzhERjM=")
    @POST("https://192.168.0.3/OAuth/Token")
    @FormUrlEncoded
    suspend fun getToken(@Field("grant_type") granttype: String) : BaseResp<TokenResponse>

    @POST("guardian/login")
    suspend fun login(@Body requestBody: LoginRequest) : BaseResp<UserInfo>

    @POST("gsegserf")
    suspend fun test() : BaseResp<ResponseBody>
}
