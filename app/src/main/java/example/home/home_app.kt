package example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import example.login.LoginScreen
import kotlinx.serialization.Serializable


@Composable
fun HomeApp() {
    val backStack = rememberNavBackStack(LoginNav)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
           entryBuilder(backStack)
        }
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(backStack, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            MyRouteEvents.events.collect {
                route(backStack, it)
            }
        }
    }
}

fun route(backStack: NavBackStack<NavKey>, e : RouteEvent) {
    e.key?.let { backStack.add(e.key) }
    if (e.isBack) backStack.removeLastOrNull()
    e.route?.isNotEmpty().let {  }
}

fun EntryProviderScope<NavKey>.entryBuilder(backStack: NavBackStack<NavKey>) {
    entry(key = LoginNav) {
        LoginScreen(evt = { route(backStack, it) })
    }
}

