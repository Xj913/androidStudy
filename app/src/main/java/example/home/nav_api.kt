package example.home

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.Serializable


data class RouteEvent (val route: String? = null, val key : NavKey?, val isBack: Boolean = false )

object MyRouteEvents {
    private val _events = MutableSharedFlow<RouteEvent>(
        replay = 0,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<RouteEvent> = _events.asSharedFlow()

    fun send(event: RouteEvent) {
        _events.tryEmit(event)
    }
}
@Serializable
data object HomeNav : NavKey
@Serializable
data object LoginNav : NavKey
@Serializable
sealed interface MyAppNavKey : NavKey

