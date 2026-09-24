package com.style.base;

import android.app.Application
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.style.data.app.MyAppManager
import com.style.toast.ToastManager
import com.style.utils.LogManager

abstract class BaseServiceModel : ViewModel() {
    protected val TAG = this.javaClass.simpleName

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
