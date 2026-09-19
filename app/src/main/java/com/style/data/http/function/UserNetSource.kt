package com.style.data.http.function;

import com.style.data.http.request.LoginRequest
import com.style.data.http.response.LoginBean
import com.style.data.http.response.TokenResponse
import com.style.entity.KuaiDi
import com.style.entity.UserInfo
import com.style.http.response.BaseDataResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserNetSource {

    @POST("/app/changePsd.html")
    suspend fun login(@Field("userName") name: String, @Field("password") password: String) : BaseDataResponse<LoginBean>

    @POST("kuaidi")
    suspend fun getkuaidi(@Body requestBody: RequestBody) : List<KuaiDi>

    @Headers("Authorization:YW5kcm9pZGNsaWVudDo4QTcyOUZENC04NjdGLTREMTItOEE4Ri1CQTNFOEQ4MzhERjM=")
    @POST("https://192.168.0.3/OAuth/Token")
    @FormUrlEncoded
    suspend fun getToken(@Field("grant_type") granttype: String) : BaseDataResponse<TokenResponse>

    @POST("guardian/login")
    suspend fun login2(@Body requestBody: LoginRequest) : BaseDataResponse<UserInfo>

    @POST("gsegserf")
    suspend fun test() : ResponseBody
}
