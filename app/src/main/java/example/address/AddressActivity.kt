package example.address

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import com.xiajun.app.MyApp.R
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbruyelle.rxpermissions3.RxPermissions
import com.xiajun.app.MyApp.databinding.ActivityAddressBinding
import com.xiajun.base.BaseTitleBarActivity
import com.xiajun.view.diviver.DividerItemDecoration
import com.xiajun.view.sort.FloatingBarItemDecoration
import com.xiajun.view.sort.IndexBar.OnTouchingLetterChangeListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import androidx.core.net.toUri
import com.xiajun.base.AdapterRefresh

class AddressActivity : BaseTitleBarActivity() {
    lateinit var bd: ActivityAddressBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: UploadPhoneAdapter
    lateinit var mPresenter: AddressViewModel

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(bd.getRoot())
        setTitleBarTitle("通讯录")
        mPresenter = ViewModelProvider(this)[AddressViewModel::class.java]
        mPresenter.contacts.observe(this, Observer { d: AdapterRefresh ->
            setData(d)
        })
        adapter = UploadPhoneAdapter(context, mPresenter.dataList)
        layoutManager = LinearLayoutManager(context)
        bd.recyclerView.setLayoutManager(layoutManager)
        bd.recyclerView.addItemDecoration(DividerItemDecoration(context))
        bd.recyclerView.addItemDecoration(FloatingBarItemDecoration(this, mHeaderList))
        bd.recyclerView.setAdapter(adapter)
        adapter.setOnItemClickListener { position: Int, data: UploadPhone? ->
            call(data!!.telephone)
        }
        // 设置右侧触摸监听
        bd.indexBar.setOnTouchingLetterChangedListener(object : OnTouchingLetterChangeListener {
            override fun onTouchingLetterChanged(s: String?) {
                showLetterHint(s)
                for (position in mPresenter.mHeaderList.keys) {
                    if (mPresenter.mHeaderList[position] == s) {
                        //layoutManager.smoothScrollToPosition(bd.recyclerView, null, position);
                        //移动到屏幕顶端， 距离屏幕顶端offset
                        layoutManager.scrollToPositionWithOffset(position, 0)
                        return
                    }
                }
            }

            override fun onTouchingStart(s: String?) {
                showLetterHint(s)
            }

            override fun onTouchingEnd(s: String?) {
                hideLetterHint()
            }
        })
        mRequestContacts.launch(Manifest.permission.READ_CONTACTS)
        val v2 = bd.commonLoadingLayout.setEmptyView(com.xiajun.lib.common.R.layout.common_loading_layout_empty_2)
        v2.findViewById<View>(com.xiajun.lib.common.R.id.common_loading_layout_tv_empty_2)
            .setOnClickListener { _: View? -> bd.commonLoadingLayout.showLoading() }
        bd.btn1.setOnClickListener { _: View? -> bd.commonLoadingLayout.showLoading() }
        bd.btn2.setOnClickListener { _: View? -> bd.commonLoadingLayout.showContent() }
        bd.btn3.setOnClickListener { _: View? -> bd.commonLoadingLayout.showEmpty() }
        bd.btn4.setOnClickListener { _: View? -> bd.commonLoadingLayout.showNetworkError() }
        bd.commonLoadingLayout.setOnClickRetryListener { bd.commonLoadingLayout.showContent() }
        bd.commonLoadingLayout.showLoading()

        bd.btnImei.setOnClickListener { _: View? -> this.isNotificationEnabled }
        getImei(this)
    }

    private val isNotificationEnabled: Unit
        get() {
            val b = NotificationManagerCompat.from(this).areNotificationsEnabled()
            if (!b) requestNotify(this)
        }

    private fun hideLetterHint() {
        bd.tvDialog.visibility = View.INVISIBLE
    }

    private fun showLetterHint(s: String?) {
        bd.tvDialog.text = s
        bd.tvDialog.visibility = View.VISIBLE
    }

    fun setData(data: AdapterRefresh) {
        adapter.notifyDataSetChanged()
        bd.indexBar.setNavigators(ArrayList(mPresenter.mHeaderList.values))
    }

    var mRequestContacts = registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { it ->
        if (it) {
            mPresenter.getData()
        } else {
            showToast(R.string.please_open_contacts_permission)
        }
    }

    var mRequestImei = registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { it ->
        if (it) {
            getImei(this)
        } else {
            showToast("no imei permission")
        }
    }

    override fun onPause() {
        super.onPause()
        mPresenter.stopPlay()
    }

    private fun call(number: String?) {
        AlertDialog.Builder(context).setTitle("拨号").setMessage("拨打客服电话$number")
            .setPositiveButton(R.string.ok
            ) { dialog: DialogInterface?, _: Int ->
                dialog!!.dismiss()
                openDial(number)
            }
            .setNegativeButton(R.string.cancel
            ) { dialog: DialogInterface?, _: Int ->
                dialog!!.dismiss()
            }.create().show()
    }

    private fun openDial(number: String?) {
        val dialIntent = Intent(Intent.ACTION_DIAL, ("tel:$number").toUri())
        startActivity(dialIntent)
    }

    @SuppressLint("CheckResult")
    private fun getImei(context: Context) {
        //64位数字（表示为十六进制字符串）是在用户首次设置设备时随机生成的
        //如果在设备上执行恢复出厂设置或APK签名密钥更改，则该值可能会更改。
        val ANDROID_ID = Settings.System.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val telephonyManager = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val clazz: Class<*> = telephonyManager.javaClass
        var getImei: Method? = null
        try {
            getImei = clazz.getDeclaredMethod("getImei", Int::class.java)
            val m1 = getImei.invoke(telephonyManager, 0) as String //卡1
            val m2 = getImei.invoke(telephonyManager, 1) as String// 卡2
            //Log.e(TAG, "IMEI1 : "+getImei.invoke(telephonyManager, 0)); //卡1
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun requestNotify(context: Context) {
            val localIntent = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            } else {
                localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
                localIntent.putExtra("app_package", context.packageName)
                localIntent.putExtra("app_uid", context.applicationInfo.uid)
            }
            context.startActivity(localIntent)
        }
    }
}
