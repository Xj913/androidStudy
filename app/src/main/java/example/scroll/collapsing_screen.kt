package example.scroll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun CollapsingScreen() {
    CollapsingToolbarScaffold(
        state = rememberCollapsingToolbarScaffoldState(),
        toolbarClipToBounds = false,
        modifier = Modifier,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbarModifier = Modifier,
        toolbar = {
            Row() {
                Text(
                    text = "Title",
                    modifier = Modifier.width(100.dp)
                        //.road(whenCollapsed = Alignment.CenterStart, whenExpanded = Alignment.BottomEnd)
                )
                Text(
                    text = "Title",
                    modifier = Modifier.width(100.dp)
                        //.road(whenCollapsed = Alignment.CenterStart, whenExpanded = Alignment.BottomEnd)
                )
                Text(
                    text = "Title",
                    modifier = Modifier.width(100.dp)
                        //.road(whenCollapsed = Alignment.CenterStart, whenExpanded = Alignment.BottomEnd)
                )
            }
                  },

    ) {
        Column(
            modifier = Modifier.height(500.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ...
        }
    }

}

@Preview(showBackground = true)
@Composable()
fun Pre(){
    CollapsingScreen()
}

