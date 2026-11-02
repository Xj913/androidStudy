package example.address

import android.content.Context
import com.contents.MyContentsData
import com.xiajun.utils.LogManager.logI
import com.xiajun.utils.PinyinUtils
import com.contents.entity.MediaFolder
import com.contents.entity.UploadPhone
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.util.*

suspend fun getContacts(con: Context) : ArrayList<UploadPhone>? = withContext(IO) {
                try {
                    val list = MyContentsData.getContacts(con)
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
                    return@withContext list
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                null
}

suspend fun getMusics(con: Context) : ArrayList<MediaFolder>? = withContext(IO) {
    try {
        return@withContext MyContentsData.getAudios(con)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    null
}