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


    protected fun showToast(str: CharSequence) {
        ToastManager.showToast(getApp(), str)
    }

    protected fun showToast(@StringRes resId: Int) {
        ToastManager.showToast(getApp(), resId)
    }

    protected fun logI(tag: String, msg: String) {
        LogManager.logI(tag, msg)
    }

    protected fun logE(tag: String, msg: String) {
        LogManager.logE(tag, msg)
    }
}
