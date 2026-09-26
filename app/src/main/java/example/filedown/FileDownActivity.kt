package example.filedown

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.xiajun.app.MyApp.databinding.FileDownListActivityBinding
import com.xiajun.base.BaseRecyclerViewAdapter
import com.xiajun.base.BaseTitleBarActivity
import com.xiajun.config.FileDirConfig
import com.xiajun.data.event.EventBusEvent
import com.xiajun.data.fileDown.CustomFileDownloadManager
import com.xiajun.data.fileDown.FileDownloadStateBean
import com.xiajun.data.fileDown.FileDownloadStateBean.DownStatus
import com.xiajun.data.fileDown.entity.CustomFileBean
import com.xiajun.myevent.EventReceiver
import com.xiajun.myevent.MyEventManager
import com.xiajun.service.fileDownload.FileDownloadService
import com.xiajun.utils.OpenFileUtil
import com.xiajun.view.diviver.DividerItemDecoration
import java.io.File

class FileDownActivity : BaseTitleBarActivity(), EventReceiver {

    private lateinit var bd: FileDownListActivityBinding
    private val targetPath = FileDirConfig.DIR_APP_FILE + "/apache-tomcat-8.0.24_multi_thread.exe"

    private lateinit var dataList: ArrayList<CustomFileBean>
    private lateinit var adapter: FileDownListAdapter
    private lateinit var mViewModel: FileDownListViewModel

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = FileDownListActivityBinding.inflate(layoutInflater)
        setContentView(bd.root)
        setTitleBarTitle("文件下载")
        MyEventManager.getInstance().register(this, EventBusEvent.FILE_DOWNLOAD_STATE_CHANGED)
        dataList = ArrayList()
        adapter = FileDownListAdapter(getContext(), dataList)
        val layoutManager = LinearLayoutManager(getContext())
        bd.recyclerView.layoutManager = layoutManager
        bd.recyclerView.addItemDecoration(DividerItemDecoration(getContext()))
        //解决默认动画造成的itemView闪烁
        (bd.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        bd.recyclerView.adapter = adapter
        adapter.onItemClickListener =
            BaseRecyclerViewAdapter.OnItemClickListener { position, _ -> logE("onItemClick", position.toString()) }
        adapter.setOnItemLongClickListener { _, position, _ ->
            logE(
                "onItemLongClick",
                position.toString()
            )
        }
        adapter.setOnClickOptionListener(object : FileDownListAdapter.OnClickOptionListener<CustomFileBean> {
            override fun onClickOption(position: Int, data: CustomFileBean) {
                logE("onClickOption", position.toString())
                val state = data.fileStatus
                if (state == null) {
                    newDownloadFile(data)
                    return
                }
                when (state.status) {
                    DownStatus.NOT_DOWNLOAD -> newDownloadFile(data)
                    DownStatus.DOWNLOAD_PREPARE -> {
                    }
                    DownStatus.DOWNLOAD_PAUSE -> continueDownloadFile(data)
                    DownStatus.DOWNLOADING -> pauseDownloadFile(data)
                    DownStatus.DOWNLOAD_COMPLETED -> {
                        val file = File(FileDirConfig.DIR_APP_FILE, data.fileName)
                        if (file.parentFile!!.exists() && file.exists()) {
                            try {
                                OpenFileUtil.openFile(context, FileDirConfig.FILE_PROVIDER_AUTHORITY, file)
                            } catch (e: Exception) {
                                showToast(e.message!!)
                            }
                        } else {
                            //未下载
                            showToast("文件不存在或已被删除")
                            state.status = DownStatus.NOT_DOWNLOAD
                            adapter.notifyItemChanged(position)
                        }
                    }
                }
            }
        })
        bd.viewBatchDownload.setOnClickListener { batchDownload() }
        mViewModel = ViewModelProvider(this)[FileDownListViewModel::class.java]
        mViewModel.files.observe(this, Observer<ArrayList<CustomFileBean>> { t ->
            refreshData(t)
        })
        getData()
    }

    private fun batchDownload() {
        val i = Intent(CustomFileDownloadManager.FLAG_BATCH_DOWNLOAD)
        i.setClass(getContext(), FileDownloadService::class.java)
        i.putExtra("fileBeanList", dataList)
        startService(i)
    }

    //暂停下载
    fun pauseDownloadFile(f: CustomFileBean) {
        val i = Intent(CustomFileDownloadManager.FLAG_PAUSE_DOWNLOAD)
        i.setClass(getContext(), FileDownloadService::class.java)
        i.putExtra("fileBean", f)
        startService(i)
    }

    //新下载任务
    fun newDownloadFile(f: CustomFileBean) {
        val i = Intent(CustomFileDownloadManager.FLAG_NEW_DOWNLOAD)
        i.setClass(getContext(), FileDownloadService::class.java)
        i.putExtra("fileBean", f)
        startService(i)
    }

    //暂停后继续下载
    fun continueDownloadFile(f: CustomFileBean) {
        val i = Intent(CustomFileDownloadManager.FLAG_CONTINUE_DOWNLOAD)
        i.setClass(getContext(), FileDownloadService::class.java)
        i.putExtra("fileBean", f)
        startService(i)
    }

    private fun getData() {
        mViewModel.getData()
    }

    private fun refreshData(list: ArrayList<CustomFileBean>?) {
        if (list != null) {
            dataList.clear()
            /*list.forEachIndexed { index, b ->
                val file = File(FileDirConfig.DIR_APP_FILE, b.fileName)
                if (file.parentFile.exists() && file.exists()) {
                    if (b.fileStatus == null)
                        b.fileStatus = FileDownloadStateBean(b.url)
                    b.fileStatus?.status = DownStatus.DOWNLOAD_COMPLETED
                }
            }*/
            dataList.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onFileDownloadStateChanged(f: FileDownloadStateBean) {
        for (i in dataList.indices) {
            if (dataList[i].url.equals(f.url)) {
                dataList[i].fileStatus = f
                adapter.notifyItemChanged(i)
                break
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MyEventManager.getInstance().unRegister(this)
    }

    override fun onMainThreadEvent(code: String, data: Any?) {
        if (EventBusEvent.FILE_DOWNLOAD_STATE_CHANGED == code) {
            onFileDownloadStateChanged(data as FileDownloadStateBean)
        }
    }
}
