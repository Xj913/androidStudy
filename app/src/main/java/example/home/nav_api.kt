package example.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.xiajun.ui.MyDimens
import kotlinx.serialization.Serializable

@Serializable
sealed interface MyAppNavKey : NavKey
@Serializable
data object LoginNav : NavKey
@Serializable
data object HomeNav : NavKey
@Serializable
data object ContactsNav : NavKey
@Serializable
data object AlbumNav : NavKey
@Serializable
data object DialogNav : NavKey
@Serializable
data object WheelNav : NavKey
@Serializable
data object SuspendWindowNav : NavKey
@Serializable
data object BigimageNav : NavKey
@Serializable
data object DrawNav : NavKey
@Serializable
data object WriteWordNav : NavKey
@Serializable
data object CustomViewNav : NavKey
@Serializable
data object HeartViewNav : NavKey
@Serializable
data object TempViewNav : NavKey
@Serializable
data object BpViewNav : NavKey
@Serializable
data object SleepViewNav : NavKey
@Serializable
data object SportViewNav : NavKey
@Serializable
data object EcgViewNav : NavKey
@Serializable
data object RecordAudioViewNav : NavKey
@Serializable
data object RefreshViewNav : NavKey

