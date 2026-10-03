package example.home

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class MyNavKey : NavKey {
    @Serializable
    data object LoginNav : MyNavKey()
    @Serializable
    data object HomeNav : MyNavKey()
    @Serializable
    data object ContactsNav : MyNavKey()
    @Serializable
    data object AlbumNav : MyNavKey()
    @Serializable
    data object DialogNav : MyNavKey()
    @Serializable
    data object WheelNav : MyNavKey()
    @Serializable
    data object SuspendWindowNav : MyNavKey()
    @Serializable
    data object BigimageNav : MyNavKey()
    @Serializable
    data object DrawNav : MyNavKey()
    @Serializable
    data object WriteWordNav : MyNavKey()
    @Serializable
    data object CustomViewNav : MyNavKey()
    @Serializable
    data object HeartViewNav : MyNavKey()
    @Serializable
    data object TempViewNav : MyNavKey()
    @Serializable
    data object BpViewNav : MyNavKey()
    @Serializable
    data object SleepViewNav : MyNavKey()
    @Serializable
    data object SportViewNav : MyNavKey()
    @Serializable
    data object EcgViewNav : MyNavKey()
    @Serializable
    data object RecordAudioViewNav : MyNavKey()
    @Serializable
    data object RefreshViewNav : MyNavKey()
}
