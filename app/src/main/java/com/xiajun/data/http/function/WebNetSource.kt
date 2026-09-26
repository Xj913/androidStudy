package com.xiajun.data.http.function;

import com.xiajun.entity.KuaiDi
import com.xiajun.http.core.BaseResp
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WebNetSource {

    @POST("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo")
    @FormUrlEncoded
    suspend fun getMobileLocation(@Field("mobileCode") mobileCode: String, @Field("userID") userID: String) : BaseResp<String>

    @POST("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather")
    @FormUrlEncoded
    suspend fun getWeatherInfo(@Field("theCityCode") cityCode: String, @Field("theUserID") userID: String) : BaseResp<String>

    @POST("http://www.kuaidi100.com/query?")
    @FormUrlEncoded
    suspend fun getKuaiDi(@Field("type") type: String, @Field("postid") postid: String) : BaseResp<String>

    @POST("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather")
    @FormUrlEncoded
    suspend fun getWeatherInfo2(@Field("theCityCode") cityCode: String, @Field("theUserID") userID: String) : BaseResp<String>

    @POST("kuaidi")
    suspend fun getkuaidi(@Body requestBody: RequestBody) : BaseResp<List<KuaiDi>>
}
