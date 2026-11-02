package example.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.contents.entity.MediaBean
import com.xiajun.base.AdapterRefresh
import com.xiajun.base.BaseViewModel
import example.address.getMusics
import kotlinx.coroutines.launch

class MusicsViewModel : BaseViewModel() {
    var dataList: ArrayList<MediaBean> = ArrayList()
    val mAdapterRefresh: MutableLiveData<AdapterRefresh> = MutableLiveData<AdapterRefresh>()

    fun getData() {
        viewModelScope.launch {
            val s = getMusics(getApp())
            s?.let { it ->
                dataList.clear()
                it.forEach {
                    dataList.addAll(it.medias)
                }
            }
            mAdapterRefresh.postValue(AdapterRefresh())
        }
    }
}