package example.web_service

import android.content.Context
import com.xiajun.utils.LogManager.logI
import com.xiajun.utils.PinyinUtils
import example.address.ContactHelper
import example.address.UploadPhone
import example.address.UploadPhoneComparator
import java.util.*

    suspend fun getContacts(con: Context) : List<UploadPhone?>? {
                try {
                    val list = ContactHelper.getContacts(con)
                    if (null != list) {
                        val size = list.size
                        for (i in 0 until size) {
                            val sortLetter =
                                PinyinUtils.getAbbreviation(list[i].name).substring(0, 1)
                            list[i].sortLetters = sortLetter
                        }
                        // 根据a-z进行排序源数据
                        Collections.sort(list, UploadPhoneComparator())
                        list.forEach {
                            logI("info", it.toString())
                        }
                    }
                    return list
                } catch (e: Exception) {
                    e.printStackTrace()
                }
        return null
    }