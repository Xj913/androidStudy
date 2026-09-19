package com.style.base

import android.app.Application
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.style.data.app.MyAppManager
import com.style.toast.ToastManager
import com.style.utils.LogManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseCompoModel : ViewModel() {
    protected val TAG = this.javaClass.simpleName
    val isLoadingShow = mutableStateOf(false)
    val refreshState = mutableIntStateOf(0)
    private val tasks = CompositeDisposable()
    private var singleTasks: MutableList<Disposable>? = null
    protected fun getApp(): Application {
        return MyAppManager.getInstance().app
    }

    override fun onCleared() {
        Log.e(TAG, "onCleared")
        removeAllTask()
        removeSingleTask()
    }

    protected fun addTask(d: Disposable) {
        tasks.add(d)
    }

    protected fun removeAllTask() {
        tasks.dispose()
    }

    /**
     * 频繁重复请求时
     *
     * @param d
     * @return
     */
    protected fun addSingleTask(d: Disposable): Boolean {
        removeSingleTask()
        if (singleTasks == null)
            singleTasks = ArrayList()
        return singleTasks!!.add(d)
    }

    protected fun removeSingleTask() {
        if (singleTasks != null) {
            for (d in singleTasks!!) {
                d.dispose()
            }
        }
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
