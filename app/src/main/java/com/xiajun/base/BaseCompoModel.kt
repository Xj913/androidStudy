package com.xiajun.base

import android.app.Application
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xiajun.data.app.MyAppManager
import com.xiajun.toast.ToastManager
import com.xiajun.utils.LogManager

abstract class BaseCompoModel : ViewModel() {
    protected val TAG = this.javaClass.simpleName
    val isLoadingShow = mutableStateOf(false)
    val refreshState = mutableIntStateOf(0)

    protected fun getApp(): Application {
        return MyAppManager.getInstance().app
    }

    override fun onCleared() {
        Log.e(TAG, "onCleared")
    }

    fun showToast(str: CharSequence) {
        ToastManager.showToast(getApp(), str)
    }

    fun showToast(@StringRes resId: Int) {
        ToastManager.showToast(getApp(), resId)
    }

    fun logI(tag: String, msg: String) {
        LogManager.logI(tag, msg)
    }

    fun logE(tag: String, msg: String) {
        LogManager.logE(tag, msg)
    }
}
