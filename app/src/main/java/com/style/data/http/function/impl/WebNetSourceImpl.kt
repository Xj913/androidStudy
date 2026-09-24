package com.style.data.http.function.impl

import com.style.data.http.function.WebNetSource
import com.style.entity.KuaiDi
import com.style.http.core.RetrofitImpl
import com.style.http.response.BaseResp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject


object WebNetSourceImpl {

    val mAPI: WebNetSource by lazy {
        RetrofitImpl.getInstance().getDefaultRetrofit().create(WebNetSource::class.java)
    }

    suspend fun getPhoneInfo(phone: String) : BaseResp<String> {
        return mAPI.getMobileLocation(phone, "")
    }

    suspend fun getWeather(cityCode: String) : BaseResp<String> {
        return mAPI.getWeatherInfo(cityCode, "")
    }

    suspend fun getKuaiDi(type: String, postid: String) : BaseResp<String> {
        return mAPI.getKuaiDi("yuantong", "11111111111")
    }

    suspend fun getkuaidilist(id: String) : BaseResp<List<KuaiDi>> {
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

}
