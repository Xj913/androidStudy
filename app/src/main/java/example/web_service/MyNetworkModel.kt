package example.web_service;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.xiajun.base.BaseCompoModel
import com.xiajun.data.http.function.impl.WebNetSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyNetworkModel : BaseCompoModel() {
    val phone = mutableStateOf("")
    val city = mutableStateOf("")
    val content = mutableStateOf("")

    fun getPhoneInfo(phone: String = this.phone.value) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val s = async { WebNetSourceImpl.getPhoneInfo(phone) }.await()
            if (s.isOk()) {
                content.value = s.data!!
            }
        }
    }

    fun getWeather(code: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
                val s = async { WebNetSourceImpl.getWeather(code) }.await()
            if (s.isOk()) {
                withContext(Dispatchers.Main) {
                    content.value = s.data!!
                    showToast("查询成功")
                }
            }
        }
    }

    fun getKuaiDi(s: String = "", s1: String = "") {
        viewModelScope.launch(context = Dispatchers.IO) {
            val s = async { WebNetSourceImpl.getKuaiDi("", "") }.await()
            if (s.isOk()) {
                withContext(Dispatchers.Main) {
                    content.value = s.data!!
                    showToast("查询成功")
                }
            }
        }
    }

    fun getKuaiDilist(s: String, s1: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val s = async { WebNetSourceImpl.getkuaidi("") }.await()
            if (s.isOk()) {
                withContext(Dispatchers.Main) {
                    content.value = s.data!!.toString()
                    showToast("查询成功")
                }
            }
        }
    }


    fun getContact() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val s = async { getContacts(getApp()) }.await()
            }
        }
    }
}

