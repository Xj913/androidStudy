package com.style.http.response

import com.style.data.app.MyAppManager
import com.style.http.response.BaseResp.Companion.NETWORK_ERROR
import com.style.toast.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

class BaseResp<T>(var code: Int?, var data: T? = null, var msg: String?) {
    companion object {
        const val SUCCEED = 0
        const val NETWORK_ERROR = 9999 //网络异常
        const val SERVER_ERROR = 9998  //服务器异常
        const val TOKEN_INVALID = 999  //TOKEN无效
        const val TOKEN_EXPIRED = 401  //TOKEN过期
    }

    fun isSucceed() : Boolean { return code == SUCCEED }
}
fun <T> networkError(d: T?) : BaseResp<T> {
    return BaseResp(NETWORK_ERROR, d, "服务器异常")
}

fun <T> httpError(code: Int, d: T?, msg: String?) : BaseResp<T> {
    return BaseResp(code, d, msg )
}

suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResp<T>): BaseResp<T> {
    runCatching {
        apiCall()
    }.onSuccess {
        return it
    }.onFailure { e ->
        e.printStackTrace()
        if (e is MyHttpException) {
            withContext(Dispatchers.Main) {
                ToastManager.showToast(MyAppManager.getInstance().app, e.message)
            }
            val d: T? = null
            return httpError(e.code, d, e.message)
        }
    }
    withContext(Dispatchers.Main) {
        ToastManager.showToast(MyAppManager.getInstance().app, "服务器异常")
    }
    val d: T? = null
    return networkError(d)
}

open class MyHttpException(val code: Int) : RuntimeException()