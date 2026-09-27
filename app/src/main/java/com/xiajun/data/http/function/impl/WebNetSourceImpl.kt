package com.xiajun.data.http.function.impl

import com.xiajun.config.AssembleConfig
import com.xiajun.entity.KuaiDi
import com.xiajun.http.core.ApiResp
import com.xiajun.http.core.NetClient
import com.xiajun.http.core.NetRequest


object WebNetSourceImpl {
    private const val BASE_URL = AssembleConfig.URL_BASE

    suspend fun getPhoneInfo(phone: String) : ApiResp<String> {
        val request = NetClient.postForm(
            "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo",
            mapOf("mobileCode" to phone
                ,"userID" to "")
        )
        return NetRequest.execute(request)
    }

    suspend fun getWeather(cityCode: String) : ApiResp<String> {
        val request = NetClient.postForm(
            "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather",
            mapOf("theCityCode" to cityCode
                ,"theUserID" to "")
        )
        return NetRequest.execute(request)
    }

    suspend fun getKuaiDi(type: String, postid: String) : ApiResp<String> {
        val request = NetClient.postForm(
            "http://www.kuaidi100.com/query?",
            mapOf("yuantong" to type
                ,"postid" to "11111111111")
        )
        return NetRequest.execute(request)
    }

    suspend fun getkuaidi(id: String) : ApiResp<KuaiDi> {
        val json = """{"Id":"$id"}"""
        val request = NetClient.postJson(
            "http://www.kuaidi100.com/query?", json)
        return NetRequest.execute(request)
    }

}
