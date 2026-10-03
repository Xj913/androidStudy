package example.home

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.Serializable


open class RouteEvent (val route: String? = null, val key : MyNavKey?, val isBack: Boolean = false )
data object LoginSucceed : RouteEvent(key = null)

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
