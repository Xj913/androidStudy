package example.music

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.contents.entity.MediaFolder
import com.xiajun.app.MyApp.R
import com.xiajun.app.MyApp.databinding.ActivityMusicListBinding
import com.xiajun.base.BaseTitleBarActivity
import com.xiajun.service.music.PlayMusicService
import com.xiajun.service.music.PlayMusicService.MyBinder
import com.xiajun.view.diviver.DividerItemDecoration

class MusicListActivity : BaseTitleBarActivity() {
    lateinit var bd: ActivityMusicListBinding
    lateinit var adapter: AudioAdapter
    lateinit var mViewModel: MusicsViewModel

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = ActivityMusicListBinding.inflate(layoutInflater)
        setContentView(bd.getRoot())
        setTitleBarTitle("播放列表4")
        mViewModel = ViewModelProvider(this)[MusicsViewModel::class]
        adapter = AudioAdapter(this, mViewModel.dataList)
        val layoutManager = LinearLayoutManager(this)
        bd.recyclerView.setLayoutManager(layoutManager)
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        bd.recyclerView.setHasFixedSize(true)
        bd.recyclerView.addItemDecoration(DividerItemDecoration(this))
        bd.recyclerView.setAdapter(adapter)
        adapter.setOnItemClickListener { _,data ->
                myBinder!!.play(data)
            }
        mViewModel.mAdapterRefresh.observe(this) {
            adapter.notifyDataSetChanged()
        }
        mRequestAudios.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    var mRequestAudios = registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { it ->
        if (it) {
            mViewModel.getData()
        } else {
            showToast(R.string.error_no_external_storage_permission)
        }
    }

    fun start(v: View?) {
        val i = Intent(Intent(this, PlayMusicService::class.java))
        startService(i)
    }

    fun stop(v: View?) {
        val i = Intent(Intent(this, PlayMusicService::class.java))
        stopService(i)
    }

    var isBind: Boolean = false

    fun bind(v: View?) {
        val i = Intent(Intent(this, PlayMusicService::class.java))
        bindService(i, mConnection, BIND_AUTO_CREATE)
        isBind = true
    }

    fun unbind(v: View?) {
        unbind2()
    }

    fun unbind2() {
        if (isBind) {
            try {
                //绑定了才调用此方法，不然会报错
                unbindService(mConnection)
                isBind = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        unbind2()
        super.onDestroy()
    }

    var myBinder: MyBinder? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            Log.e(getTAG(), "onServiceConnected")
            myBinder = service as MyBinder
        }

        //连接意外断开时调用,unbindservice不触发此回调
        override fun onServiceDisconnected(className: ComponentName?) {
            Log.e(getTAG(), "onServiceDisconnected")
        }
    }
}
