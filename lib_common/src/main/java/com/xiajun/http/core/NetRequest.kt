package com.example.net

import com.google.gson.Gson
import com.xiajun.http.core.NetClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.IOException

/** suspend 请求执行器 */
object NetRequest {

    /**
     * 执行请求，返回解析后的业务数据
     * @param request OkHttp Request
     * @param typeOfT Gson TypeToken，用于解析 data 字段
     */
    suspend fun <T> execute(
        request: Request,
        typeOfT: java.lang.reflect.Type
    ): NetResult<T> = withContext(Dispatchers.IO) {
        try {
            val response = NetClient.okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext NetResult.Error(response.code, "HTTP ${response.code}")
            }
            val body = response.body?.string()
                ?: return@withContext NetResult.Error(-1, "响应体为空")

            val apiResp = Gson().fromJson<ApiResponse<T>>(body, typeOfT)
                ?: return@withContext NetResult.Error(-1, "JSON 解析失败")

            if (apiResp.code != 0 || apiResp.data == null) {
                NetResult.Error(apiResp.code, apiResp.message)
            } else {
                NetResult.Success(apiResp.data)
            }
        } catch (e: IOException) {
            NetResult.Exception(e)
        } catch (e: Exception) {
            NetResult.Exception(e)
        }
    }
}
