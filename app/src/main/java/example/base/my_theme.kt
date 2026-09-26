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

