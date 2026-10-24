package example.album

import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import com.xiajun.base.BaseCompoModel
import com.xiajun.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PikerPhotoViewModel @Inject constructor() : BaseCompoModel() {
    var photoFile: File? = null
    val bottomDlgShow = mutableStateOf(false)

    fun getPhotoUri(): Uri {
        photoFile = createCacheImageFile()
        val uri = FileProvider.getUriForFile(getApp(), "${getApp().packageName}.fileprovider", photoFile!!)
        return uri!!
    }

    fun createCacheImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storeDir = File(getApp().cacheDir, "camera")
        if (!storeDir.exists()) {
            storeDir.mkdirs()
        }
        return File.createTempFile("IMG_${timeStamp}_", ".jpg", storeDir)
    }

}