package example.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
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
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
           entryBuilder()
        }
    )
}

fun EntryProviderScope<NavKey>.entryBuilder() {
    entry<LoginNav> {
        LoginScreen(vml = hiltViewModel())
    }
}

@Serializable
data object HomeNav : NavKey
@Serializable
data object LoginNav : NavKey
@Serializable
sealed interface MyAppNavKey : NavKey