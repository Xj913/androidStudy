package example.address

import android.content.Context
import com.xiajun.utils.LogManager.logI
import com.xiajun.utils.PinyinUtils
import java.util.*

    suspend fun getContacts(con: Context) : ArrayList<UploadPhone>? {
                try {
                    val list = ContactHelper.getContacts(con)
                    if (null != list) {
                        val size = list.size
                        for (i in 0..size) {
                            val sortLetter = PinyinUtils.getAbbreviation(list[i].name).substring(0, 1)
                            list[i].sortLetters = sortLetter
                        }
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