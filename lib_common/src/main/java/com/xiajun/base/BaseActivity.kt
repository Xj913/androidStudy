package com.xiajun.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.xiajun.dialog.LoadingDialog
import com.xiajun.utils.DeviceInfoUtil
import com.xiajun.utils.InputMethodUtil
import com.xiajun.utils.LogManager

abstract class BaseActivity : AppCompatActivity() {
    protected var tag: String = javaClass.getSimpleName()
    protected lateinit var context: Context
    private var progressDialog: LoadingDialog? = null
    private var toast: Toast? = null

    override fun attachBaseContext(newBase: Context) {
        val config = newBase.resources.configuration
        config.fontScale = 1.0f
        config.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
        val newContext = newBase.createConfigurationContext(config)
        super.attachBaseContext(newContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logI(this.tag, "onCreate-------------")
        context = this
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onPause() {
        super.onPause()
        logI(this.tag, "onPause-------------")
        InputMethodUtil.hiddenSoftInput(this)
    }

    fun getTAG() : String {
        return tag
    }
    override fun onDestroy() {
        super.onDestroy()
        logI(this.tag, "onDestroy-------------")
        dismissProgressDialog()
    }

    protected fun showToast(str: CharSequence?) {
        if (toast == null) toast = Toast.makeText(context, str, Toast.LENGTH_SHORT)
        toast!!.setText(str)
        toast!!.show()
    }

    protected fun showToast(@StringRes resId: Int) {
        showToast(getString(resId))
    }

    protected fun cancelToast() {
        toast!!.cancel()
    }

    protected fun showProgressDialog(@StringRes msgId: Int): LoadingDialog? {
        return showProgressDialog(getString(msgId))
    }

    protected fun showProgressDialog(msg: CharSequence?): LoadingDialog? {
        if (progressDialog == null) progressDialog = LoadingDialog(context)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        progressDialog!!.setMessage(msg)
        return progressDialog
    }

    protected fun dismissProgressDialog() {
        if (progressDialog != null) progressDialog!!.dismiss()
    }

    protected fun dp2px(dpValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().displayMetrics).toInt()
    }

    protected fun logI(tag: String?, msg: String?) {
        LogManager.logI(tag, msg)
    }

    protected fun logE(tag: String?, msg: String?) {
        LogManager.logE(tag, msg)
    }
}
