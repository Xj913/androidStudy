package example.myrefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lx.compose.refresh.SmartSwipeRefresh
import com.lx.compose.refresh.rememberSmartSwipeRefreshState
import com.xiajun.ui.MyTextStyles
import example.base.TitleBarDefault
import example.base.TitleBarWhite
import example.home.Myitem
import example.home.RefreshState
import example.login.LoginScreen

@Composable
fun MyRefreshScreen(vml: MyRefreshViewModel = hiltViewModel()) {
    val llState = rememberLazyListState()
    val sizePx = with(LocalDensity.current) { 80.dp.toPx() }
    val anchors = DraggableAnchors {
        DragValue.Start at -sizePx
        DragValue.Center at 0f
        DragValue.End at sizePx
    }
    val statew = remember {
        AnchoredDraggableState(DragValue.Start, anchors = anchors)
    }
    val scope = rememberCoroutineScope()
    val state = rememberSmartSwipeRefreshState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleBarWhite(title = "swipe menu") {}
        SmartSwipeRefresh(
            state = state,
            onRefresh = {
                vml.refresh()
            },
            onLoadMore = {
                vml.loadMore()
            }
        ) {
            LazyColumn(state = llState) {
                items(
                    vml.mList.size, key = { vml.mList[it].id },
                    contentType = { vml.mList[it] }
                ) {
                    for (m in vml.mList)
                        when (m) {
                            is Myitem -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(50.dp)
                                        .background(Color.LightGray)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = "更新",
                                            modifier = Modifier.fillMaxHeight().width(80.dp)
                                                .background(Color.Cyan).clickable(true) {
                                                    m.id *= 10
                                                },
                                            textAlign = TextAlign.Center,
                                            style = MyTextStyles.textPrimary16sp
                                        )
                                        Text(
                                            text = "删除",
                                            modifier = Modifier.fillMaxHeight().width(80.dp)
                                                .background(Color.Red).clickable(true) {
                                                    vml.mList.remove(m)
                                                },
                                            textAlign = TextAlign.Center,
                                            style = MyTextStyles.textPrimary16sp
                                        )
                                    }
                                }
                                Text(
                                    text = "text-${m.id}",
                                    modifier = Modifier.fillMaxSize()
                                        .offset {
                                            IntOffset(
                                                x = statew.requireOffset().toInt(),
                                                y = 0
                                            )
                                        })
                            }
                        }
                }
            }
        }
    }
    LaunchedEffect(vml.mRefreshState.value) {
        when(vml.mRefreshState.value) {
            RefreshState.Refreshing -> {}
            RefreshState.LoadingMore -> {}
            RefreshState.RefreshedNoData, RefreshState.LoadedMoreNoMoreData -> state.finish(true)
            RefreshState.RefreshedHasMoreData, RefreshState.LoadedMoreHasMoreData -> state.finish(false)
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun Pre(){
    MyRefreshScreen()
}