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

@Immutable
data class ColorSystem(
    val color: Color,
    val gradient: List<Color>
)

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

@Immutable
data class CustomSystem(
    val value1: Int,
    val value2: String
)

val LocalColorSystem = staticCompositionLocalOf {
    ColorSystem(
        color = Color.Unspecified,
        gradient = emptyList()
    )
}

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

val LocalCustomSystem = staticCompositionLocalOf {
    CustomSystem(
        value1 = 0,
        value2 = ""
    )
}

@Composable
fun ReplacementTheme(content: @Composable () -> Unit) {
    val colorSystem = ColorSystem(
        color = Color(0xFF3DDC84),
        gradient = listOf(Color.White, Color(0xFFD7EFFF))
    )
    val replacementTypography = ReplacementTypography(
        body = TextStyle(fontSize = 16.sp),
        title = TextStyle(fontSize = 32.sp)
    )
    val replacementShapes = ReplacementShapes(
        component = RoundedCornerShape(percent = 50),
        surface = RoundedCornerShape(size = 40.dp)
    )
    val customSystem = CustomSystem(
        value1 = 1000,
        value2 = "Custom system"
    )
    CompositionLocalProvider(
        LocalColorSystem provides colorSystem,
        LocalReplacementTypography provides replacementTypography,
        LocalReplacementShapes provides replacementShapes,
        LocalCustomSystem provides customSystem,
        ) {
        MaterialTheme(
            content = content
        )
    }
}

object CustomTheme {
    val colorSystem: ColorSystem
        @Composable
        get() = LocalColorSystem.current
    val typography: ReplacementTypography
        @Composable
        get() = LocalReplacementTypography.current
    val shapes: ReplacementShapes
        @Composable
        get() = LocalReplacementShapes.current
    val customSystem: CustomSystem
        @Composable
        get() = LocalCustomSystem.current
}

