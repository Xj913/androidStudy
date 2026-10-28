package example.album

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import com.dmcbig.mediapicker.PickerActivity
import com.dmcbig.mediapicker.PickerConfig
import com.dmcbig.mediapicker.entity.Media
import com.xiajun.base.BaseViewModel
import com.xiajun.utils.DeviceInfoUtil
import java.io.File
import java.util.Date
import java.util.Locale

class SelectLocalPictureViewModel : BaseViewModel() {
    var TAG_ADD: Media = Media();
    var paths: ArrayList<Media> = ArrayList();
    var cacheList = ArrayList<Media>();
    var photoFile: File? = null
    var uri: Uri? =null
    val bottomDlgShow = mutableStateOf(false)

    init {
        TAG_ADD.name = "addTag";
        paths.add(TAG_ADD);
    }

    fun addPhotoList(newPaths: ArrayList<Media>) {
        paths.clear();
        paths.addAll(newPaths);
        paths.add(TAG_ADD);
    }

    fun addPhoto(): Int {
        DeviceInfoUtil.scanFile(this.getApp(), photoFile);
        val size = paths.size
        if (size >= 10) {
            showToast("最多上传9张图片")
            return -1
        } else {
            var location = 0;
            if (size >= 1)
                location = size - 1;
            val media = Media();
            media.path = photoFile?.absolutePath
            media.size = photoFile!!.length();
            paths.add(location, media);
            return location
        }
    }

    fun getPhotos(): ArrayList<Media> {
        cacheList.clear()
        if (paths.size > 1) {
            for (i in 0 until paths.size - 1) {
                cacheList.add(paths[i]);
            }
        }
        return cacheList
    }

    fun getPikerPhotosIntent(): Intent {
        var intent = Intent(getApp(), PickerActivity::class.java);
        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE)
        var maxSize = 188743680L
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 40);
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, getPhotos());
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