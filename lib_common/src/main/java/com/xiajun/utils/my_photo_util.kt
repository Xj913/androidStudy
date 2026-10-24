package com.xiajun.utils

import android.graphics.BitmapFactory
import com.xiajun.utils.LogManager.logE
import java.io.File
import java.io.IOException

fun rotateAndSavePhoto(photo: File, savePath: String): Boolean {
    try {
        val degree = PictureUtil.readPictureDegree(photo.absolutePath)
        logE("TAG", "拍照后的角度：$degree")
        val isSucceed: Boolean
        //旋转图片,保存
        if (degree != 0) {
            var bitmap = BitmapFactory.decodeFile(photo.path)
            bitmap = BitmapUtil.rotateImageView(bitmap, degree)
            BitmapUtil.saveBitmap(savePath, bitmap, 100)
            isSucceed = true
        } else {
            isSucceed = FileUtil.copyFile(photo, savePath)
        }
        return isSucceed
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}
