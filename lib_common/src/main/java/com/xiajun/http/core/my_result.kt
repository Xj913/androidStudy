package com.xiajun.http.core

import com.xiajun.http.core.ApiResp.Companion.NETWORK_ERROR

class ApiResp<T>(var code: Int, var data: T? = null, var msg: String?) {
    companion object {
        const val OK = 0
        const val NETWORK_ERROR = 9999 //网络异常
        const val SERVER_ERROR = 500   //服务器异常
        const val TOKEN_INVALID = 999  //TOKEN无效
        const val TOKEN_EXPIRED = 401  //TOKEN过期
    }

    fun isOk() : Boolean { return code == OK }
}

fun <T> netResp(code: Int = NETWORK_ERROR, d: T? = null, msg: String? = "服务器异常") : ApiResp<T> {
    return ApiResp(code, d, msg )
}

open class MyHttpException(val code: Int) : RuntimeException()