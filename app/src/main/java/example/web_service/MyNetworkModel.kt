package example.web_service;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.xiajun.base.BaseCompoModel
import com.xiajun.data.http.function.impl.WebNetSourceImpl
import example.address.getContacts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MyNetworkModel : BaseCompoModel() {
    val phone = mutableStateOf("")
    val city = mutableStateOf("")
    val content = mutableStateOf("")

    fun getPhoneInfo(phone: String = this.phone.value) {
        viewModelScope.launch {
            val s = WebNetSourceImpl.getPhoneInfo(phone)
            if (s.isOk()) {
                content.value = s.data!!
            }
        }
    }

    fun getWeather(code: String) {
        viewModelScope.launch {
                val s = WebNetSourceImpl.getWeather(code)
            if (s.isOk()) {
                withContext(Main) {
                    content.value = s.data!!
                    showToast("查询成功")
                }
            }
        }
    }

    fun getKuaiDi(s: String = "", s1: String = "") {
        viewModelScope.launch {
            val s = WebNetSourceImpl.getKuaiDi("", "")
            if (s.isOk()) {
                withContext(Main) {
                    content.value = s.data!!
                    showToast("查询成功")
                }
            }
        }
    }

    fun getKuaiDilist(s: String, s1: String) {
        viewModelScope.launch {
            val s = WebNetSourceImpl.getkuaidi("")
            if (s.isOk()) {
                withContext(Main) {
                    content.value = s.data!!.toString()
                    showToast("查询成功")
                }
            }
        }
    }
}

