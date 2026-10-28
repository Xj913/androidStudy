package example.address

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xiajun.base.AdapterRefresh
import com.xiajun.base.BaseViewModel
import com.xiajun.utils.PinyinUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Collections
import kotlin.collections.get
import kotlin.text.clear

class AddressViewModel : BaseViewModel() {
    var dataList: ArrayList<UploadPhone> = ArrayList()
    val mHeaderList = LinkedHashMap<Int, String?>()
    var contacts: MutableLiveData<AdapterRefresh> = MutableLiveData<AdapterRefresh>()
    var player: MediaPlayer? = null

    fun getData() {
            viewModelScope.launch(context = Dispatchers.IO) {
                val list = async { getContacts(getApp()) }.await()
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
                        contacts.postValue(AdapterRefresh(begin = -1))
                    }
                }
            }
    }

    fun getRringtones() {
            val list = ContactHelper.getRingtone(getApp())
            player = MediaPlayer()
            try {
                player!!.setDataSource(list[0]!!.path)
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
