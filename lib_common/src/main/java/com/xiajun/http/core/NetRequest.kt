package com.xiajun.http.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiajun.data.app.MyAppManager
import com.xiajun.http.core.ApiResp.Companion.OK
import com.xiajun.toast.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import okhttp3.ResponseBody
import okio.BufferedSource
import java.io.IOException

/** suspend 请求执行器 */
object NetRequest {

    /**
     * 执行请求，返回解析后的业务数据
     * @param request OkHttp Request
     * @param typeOfT Gson TypeToken，用于解析 data 字段
     */
    suspend inline fun <reified T : Any> execute(request: Request): ApiResp<T> {
        runCatching {
            NetClient.okHttpClient.newCall(request).execute()
        }.onSuccess {
            if (it.isSuccessful) {
                if (T::class == ResponseBody::class) {
                    return ApiResp(OK, it.body as T?, it.message)
                }
                val bs: BufferedSource = it.body.source().buffer
                val tempStr = bs.readUtf8()
                bs.close()
                if (T::class == String::class) {
                    return ApiResp(OK, tempStr as T?, it.message)
                }
                val type = object : TypeToken<T>() {}.type
                val t: T = Gson().fromJson(tempStr, type)
                return ApiResp(OK, t, "请求成功")
            }
            withContext(Dispatchers.Main) {
                ToastManager.showToast(MyAppManager.getInstance().app, it.message)
            }
            return netResp(code = it.code, msg = it.message)
        }.onFailure { e ->
            e.printStackTrace()
            if (e is IOException) {
                withContext(Dispatchers.Main) {
                    ToastManager.showToast(MyAppManager.getInstance().app, e.message)
                }
            }
        }
        return netResp(d = null)
    }
}
