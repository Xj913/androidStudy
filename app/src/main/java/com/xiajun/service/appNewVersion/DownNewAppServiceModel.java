package com.xiajun.service.appNewVersion;


import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.xiajun.base.BaseServiceModel;

import java.io.File;

public class DownNewAppServiceModel extends BaseServiceModel {
    MutableLiveData<Boolean> downLoadNewVersionSuccess = new MutableLiveData<>();

    private File mAppFile;

    /**
     * 开始下载新版App
     * @param versionInfo
     */
    @SuppressLint("CheckResult")
    public void startDownloadApp(String versionInfo) {
        //执行子线程run中的代码逻辑
    }

}
