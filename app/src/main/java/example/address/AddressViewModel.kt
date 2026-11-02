package example.address

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.contents.MyContentsData
import com.contents.entity.UploadPhone
import com.xiajun.base.AdapterRefresh
import com.xiajun.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.IOException

class AddressViewModel : BaseViewModel() {
    var dataList: ArrayList<UploadPhone> = ArrayList()
    val mHeaderList = LinkedHashMap<Int, String>()
    val mAdapterRefresh: MutableLiveData<AdapterRefresh> = MutableLiveData<AdapterRefresh>()
    var player: MediaPlayer? = null

    fun getData() {
            viewModelScope.launch {
                val list = getContacts(getApp())
                list?.let {
                    if (list.isNotEmpty()) {
                        dataList.clear()
                        dataList.addAll(list)
                        mHeaderList.clear()
                        mHeaderList[0] = dataList[0].sortLetters
                        for (i in 1..<dataList.size) {
                            if (!dataList[i - 1].sortLetters.equals(dataList[i].sortLetters, ignoreCase = true)
                            ) {
                                mHeaderList[i] = dataList[i].sortLetters
                            }
                        }
                        mAdapterRefresh.postValue(AdapterRefresh())
                    }
                }
            }
    }

    fun getRringtones() {
            val list = MyContentsData.getRingtone(getApp())
            player = MediaPlayer()
            try {
                player!!.setDataSource(list[0].path)
                player!!.prepareAsync()
                player!!.setOnPreparedListener { _: MediaPlayer? -> player!!.start() }
            } catch (e: IOException) {
                e.printStackTrace()
            }
    }

    fun stopPlay() {
        if (player != null && player!!.isPlaying) {
            player!!.pause()
            player!!.stop()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (player != null && player!!.isPlaying) {
            player!!.pause()
            player!!.stop()
            player!!.release()
        }
    }
}
