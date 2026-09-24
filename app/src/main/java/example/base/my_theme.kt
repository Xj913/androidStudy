package example.base

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val WindowbackgroundTranslucent = Color(0x30000000)
val BlackFull = Color(0xFE000000)
val Windowbackground = Color(0xFFF5F5F5)
val Colorprimary = Color(0xFF003D79)
val Colorprimarydark = Color(0xFF003D79)
val Textcolorprimary = Color(0xFF333333)
val TitleBarCommonTitle = Color(0xFF343434)
val White = Color(0xFFFFFFFF)
val ColorUiBgGray = Color(0xFFF6F6F6)
val ColorTextGrayLight = Color(0xFF999999)
val ColorThemeMain = Color(0xFFB68B48)
val CustomKeyboardText = Color(0xFF000000)
val Divider = Color(0xFFD1D1D1)
val ClickBgDark = Color(0xFFBFBFBF)
val ClickBgDarkLight = Color(0xFFE0E0E0)
val PullRefreshBg = Color(0xFFF5F5F5)
val PullRefreshText = Color(0xFF999999)

@Immutable
data class ReplacementTypography(
    val body: TextStyle,
    val title: TextStyle
)

@Immutable
data class ReplacementShapes(
    val component: Shape,
    val surface: Shape
)

val LocalReplacementTypography = staticCompositionLocalOf {
    ReplacementTypography(
        body = TextStyle.Default,
        title = TextStyle.Default
    )
}
val LocalReplacementShapes = staticCompositionLocalOf {
    ReplacementShapes(
        component = RoundedCornerShape(ZeroCornerSize),
        surface = RoundedCornerShape(ZeroCornerSize)
    )
}

@Composable
fun ReplacementTheme(
    content: @Composable () -> Unit
) {
    val replacementTypography = ReplacementTypography(
        body = TextStyle(fontSize = 16.sp),
        title = TextStyle(fontSize = 32.sp)
    )
    val replacementShapes = ReplacementShapes(
        component = RoundedCornerShape(percent = 50),
        surface = RoundedCornerShape(size = 40.dp)
    )
    CompositionLocalProvider(
        LocalReplacementTypography provides replacementTypography,
        LocalReplacementShapes provides replacementShapes
    ) {
        MaterialTheme(
            /* colors = ... */
            content = content
        )
    }
}

// Use with eg. ReplacementTheme.typography.body
object ReplacementTheme {
    val typography: ReplacementTypography
        @Composable
        get() = LocalReplacementTypography.current
    val shapes: ReplacementShapes
        @Composable
        get() = LocalReplacementShapes.current
}

