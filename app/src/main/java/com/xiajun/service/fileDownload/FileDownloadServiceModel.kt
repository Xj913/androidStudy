package com.xiajun.service.fileDownload;

import com.xiajun.config.FileDirConfig
import com.xiajun.base.BaseServiceModel
import com.xiajun.data.fileDown.FileDownloadStateBean.DownStatus
import com.xiajun.data.fileDown.SingleFileDownloadTask
import com.xiajun.data.fileDown.CustomFileDownloadManager
import com.xiajun.data.fileDown.entity.CustomFileBean
import java.io.File

class FileDownloadServiceModel : BaseServiceModel() {


    //暂停下载
    fun pauseDownloadFile(data: CustomFileBean) {
        CustomFileDownloadManager.getInstance().stopDownloadTask(data.url)
    }

    //新下载
    fun newDownloadFile(f: CustomFileBean) {
        val task = SingleFileDownloadTask(f.url, 0, FileDirConfig.DIR_APP_FILE.plus("/").plus(f.fileName))
        CustomFileDownloadManager.getInstance().addDownloadTask(f.url, task)
    }

    //暂停后继续下载
    fun continueDownloadFile(f: CustomFileBean) {
        val task = SingleFileDownloadTask(f.url, f.fileStatus?.downloadSize!!, FileDirConfig.DIR_APP_FILE.plus("/").plus(f.fileName))
        CustomFileDownloadManager.getInstance().addDownloadTask(f.url, task)
    }

    //批量下载(多种情况:1.未下载过；2.已下载了部分；3.已完成下载。实际是从数据库中读取下载状态)
    fun batchDownloadFile(list: MutableList<CustomFileBean>) {
        list.forEachIndexed { index, data ->
            val state = data.fileStatus
            if (state == null) {
                newDownloadFile(data)
            } else {
                when (state.status) {
                    DownStatus.NOT_DOWNLOAD -> newDownloadFile(data)
                    DownStatus.DOWNLOAD_PREPARE -> {
                    }
                    DownStatus.DOWNLOAD_PAUSE -> continueDownloadFile(data)
                    DownStatus.DOWNLOADING -> {
                    }
                    DownStatus.DOWNLOAD_COMPLETED -> {
                        val file = File(FileDirConfig.DIR_APP_FILE, data.fileName)
                        if (!file.exists())
                            newDownloadFile(data)
                    }
                }
            }
        }
    }

    fun shutdown() {
        CustomFileDownloadManager.getInstance().shutdownNow()
    }
}
