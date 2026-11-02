package example.web_service;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.xiajun.base.BaseViewModel
import com.xiajun.data.http.function.impl.WebNetSourceImpl
import kotlinx.coroutines.launch

class MyNetworkModel : BaseViewModel() {
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
                    content.value = s.data!!
                    showToast("查询成功")
            }
        }
    }

    fun getKuaiDi(s: String = "", s1: String = "") {
        viewModelScope.launch {
            val s = WebNetSourceImpl.getKuaiDi("", "")
            if (s.isOk()) {
                    content.value = s.data!!
                    showToast("查询成功")
            }
        }
    }

    fun getKuaiDilist(s: String, s1: String) {
        viewModelScope.launch {
            val s = WebNetSourceImpl.getkuaidi("")
            if (s.isOk()) {
                    content.value = s.data!!.toString()
                    showToast("查询成功")
            }
        }
    }
}

