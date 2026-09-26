package com.xiajun.data.http.function.impl;


import com.xiajun.data.http.function.UserNetSource;
import com.xiajun.data.http.request.LoginRequest;
import com.xiajun.data.http.response.LoginBean;
import com.xiajun.data.http.response.TokenResponse;
import com.xiajun.entity.UserInfo;
import com.xiajun.http.core.RetrofitImpl;
import com.xiajun.http.core.BaseResp;

import okhttp3.ResponseBody


object UserNetSourceImpl {
    val mAPI: UserNetSource by lazy {
        RetrofitImpl.getInstance().getDefaultRetrofit().create(UserNetSource::class.java)
    }

    suspend fun login(userName: String, password: String) : BaseResp<LoginBean> {
        return mAPI.login(userName, password)
    }

    suspend fun getToken() : BaseResp<TokenResponse> {
        return mAPI.getToken("client_credentials")
    }

    suspend fun login2(userName: String, passWord: String) : BaseResp<UserInfo>{
        return mAPI.login(LoginRequest(userName, passWord))
    }

    suspend fun test() : BaseResp<ResponseBody>{
        return mAPI.test()
    }
}
