package com.style.data.http.function.impl

import com.style.data.http.function.WebNetSource
import com.style.http.core.RetrofitImpl


object WebNetSourceImpl {

    val mAPI: WebNetSource by lazy {
        RetrofitImpl.getInstance().getDefaultRetrofit().create(WebNetSource::class.java)
    }

    suspend fun getPhoneInfo(phone: String) : String {
        return mAPI.getMobileLocation(phone, "")
    }

    suspend fun getWeather(cityCode: String) : String{
        return mAPI.getWeatherInfo(cityCode, "")
    }

    suspend fun getKuaiDi(type: String, postid: String) : String {
        return mAPI.getKuaiDi("yuantong", "11111111111")
    }
}
