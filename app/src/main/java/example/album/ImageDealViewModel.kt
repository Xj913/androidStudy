package example.album

import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.xiajun.base.BaseViewModel
import com.xiajun.config.FileDirConfig
import com.xiajun.utils.DeviceInfoUtil
import com.xiajun.utils.FileUtil
import com.xiajun.utils.rotateAndSavePhoto
import tech.gaolinfeng.imagecrop.lib.ImageCropActivity
import java.io.File

class ImageDealViewModel : BaseViewModel() {

    val maxCropSize: Int
        get() = 100

    fun cropAreaStr(): String {
            val dm = getApp().resources.displayMetrics
            val screenWidth = dm.widthPixels
            val screenHeight = dm.heightPixels
            val rectWidth = screenWidth * 2 / 3
            val left = screenWidth / 6
            val right = left + rectWidth
            val top = (screenHeight - rectWidth) / 2
            val bottom = top + rectWidth
            return "$left, $top, $right, $bottom"
        }

    fun getCropIntent(): Intent {
        logE(TAG, "待裁剪图片路径-->$copyFilePath")
        logE(TAG, "裁剪后图片路径-->$targetFilePath")
        val intent = ImageCropActivity.createIntent(getApp(), copyFilePath, targetFilePath,
            this.cropAreaStr(), false, this.maxCropSize
        )
        return intent
    }

    var photoFile: File? = null
    val cameraPhotoPath: String
        get() = FileDirConfig.DIR_APP_IMAGE_CAMERA + File.separatorChar + System.currentTimeMillis()
            .toString() + ".jpg"
    val copyFilePath: String
        get() = FileDirConfig.DIR_CACHE + File.separatorChar + System.currentTimeMillis()
            .toString() + ".image"
    val targetFilePath: String
        get() = FileDirConfig.DIR_CACHE + File.separatorChar + System.currentTimeMillis()
            .toString() + ".image"

    fun modifyPhoto(isCamera: Boolean, uri: Uri?): Boolean {
        if (isCamera)
            DeviceInfoUtil.scanFile(getApp(), photoFile)
        else
            photoFile = FileUtil.UriToFile(getApp(), uri)
        val isSucceed = rotateAndSavePhoto(photoFile!!, copyFilePath)
        return isSucceed
    }

    fun getPhotoUri(): Uri? {
        photoFile = File(this.cameraPhotoPath)
        if (FileUtil.isNewFileCanWrite(photoFile)) {
            return FileProvider.getUriForFile(getApp(), FileDirConfig.FILE_PROVIDER_AUTHORITY, photoFile!!)
        } else {
            showToast("图片创建失败")
        }
        return null
    }
}