package com.style.data.http.function.impl;


import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

import com.style.data.http.function.UserNetSource;
import com.style.data.http.request.LoginRequest;
import com.style.data.http.response.LoginBean;
import com.style.data.http.response.TokenResponse;
import com.style.entity.KuaiDi;
import com.style.entity.UserInfo;
import com.style.http.core.RetrofitImpl;
import com.style.http.response.BaseDataResponse;

import org.json.JSONException
import org.json.JSONObject
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody


object UserNetSourceImpl {
    val mAPI: UserNetSource by lazy {
        RetrofitImpl.getInstance().getDefaultRetrofit().create(UserNetSource::class.java)
    }

    suspend fun login(userName: String, password: String) : BaseDataResponse<LoginBean> {
        return mAPI.login(userName, password)
    }

    suspend fun getkuaidi(id: String) : List<KuaiDi> {
        val o = JSONObject()
        try {
            o.put("Id", id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody =
            o.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return mAPI.getkuaidi(requestBody)
    }

    suspend fun getToke() : BaseDataResponse<TokenResponse> {
        return mAPI.getToken("client_credentials")
    }

    suspend fun login2(userName: String, passWord: String) : BaseDataResponse<UserInfo>{
        return mAPI.login2(LoginRequest(userName, passWord))
    }

    suspend fun test() : ResponseBody{
        return mAPI.test()
    }
}
