package example.album

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import com.xiajun.base.BaseCompoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PikerPhotoViewModel @Inject constructor() : BaseCompoModel() {
    var photoFile: File? = null
    var uri: Uri? =null
    val bottomDlgShow = mutableStateOf(false)

    fun getCropIntent(): Intent {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputx", 300)
        intent.putExtra("outputy", 300)
        intent.putExtra("return-data", true)
        return intent
    }

    fun createCacheImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storeDir = File(getApp().cacheDir, "camera")
        if (!storeDir.exists()) {
            storeDir.mkdirs()
        }
        return File.createTempFile("IMG_${timeStamp}_", ".jpg", storeDir)
    }

    fun getPhotoUri(): Uri {
        photoFile = createCacheImageFile()
        uri = FileProvider.getUriForFile(getApp(), "${getApp().packageName}.fileprovider", photoFile!!)
        return uri!!
    }
}