package example.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.result.LocalResultEventBus
import androidx.navigation3.runtime.result.ResultEffect
import com.xiajun.app.MyApp.R
import com.xiajun.base.DividerHorizontal16
import com.xiajun.base.myitem
import com.xiajun.ui.MyDimens
import com.xiajun.ui.MyTextStyles
import example.base.CpgDialog
import example.base.TitleBarDefault
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(vml: HomeViewModel = hiltViewModel()) {
    val resultBus = LocalResultEventBus.current
    ResultEffect<String>(resultKey = "from") { f ->
        vml.logI("f", f)
        resultBus.removeResult(resultKey = "from")
    }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        5
    })
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleBarDefault({}) {}
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            HorizontalPager(state = pagerState) { page ->
                when(page) {
                    0 -> MyViewScreen(vml)
                    1 -> ListScreen(vml)
                    2 -> GestureScreen(vml)
                    3 -> OriginalScreen(vml)
                    4 -> OtherScreen(vml)
                }
            }

        }
        DividerHorizontal16()
        Row(modifier = Modifier.fillMaxWidth().height(55.dp).background(color = Color.White)) {
            Column(modifier = Modifier.fillMaxHeight().weight(1f).clickable(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(0)
                    }
                    vml.setPageIndex(0)
                }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (0 == vml.currentPage.intValue) {
                    Image(painter = painterResource(R.mipmap.tap_conversation_selected),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                } else {
                    Image(painter = painterResource(R.mipmap.tap_conversation_normal),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                }
            }
            Column(modifier = Modifier.fillMaxHeight().weight(1f).clickable(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(1)
                    }
                    vml.setPageIndex(1)
                }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (1 == vml.currentPage.intValue) {
                    Image(painter = painterResource(R.mipmap.tap_home_selected),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                } else {
                    Image(painter = painterResource(R.mipmap.tap_home_normal),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                }
            }
            Column(modifier = Modifier.height(70.dp).weight(1f).clickable(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(2)
                    }
                    vml.setPageIndex(2)
                }),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (2 == vml.currentPage.intValue) {
                    Image(painter = painterResource(R.mipmap.tap_active_selected),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                } else {
                    Image(painter = painterResource(R.mipmap.tap_active_normal),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                }
            }
            Column(modifier = Modifier.fillMaxHeight().weight(1f).clickable(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(3)
                    }
                    vml.setPageIndex(3)
                }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (3 == vml.currentPage.intValue) {
                    Image(painter = painterResource(R.mipmap.tap_home_selected),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                } else {
                    Image(painter = painterResource(R.mipmap.tap_home_normal),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                }
            }
            Column(modifier = Modifier.fillMaxHeight().weight(1f).clickable(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(4)
                    }
                    vml.setPageIndex(4)
                }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (4 == vml.currentPage.intValue) {
                    Image(painter = painterResource(R.mipmap.tap_contact_list_selected),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                } else {
                    Image(painter = painterResource(R.mipmap.tap_contact_list_normal),
                        modifier = Modifier.size(40.dp), contentDescription = "")
                }
            }
        }
        if (vml.isLoadingShow.value)
            CpgDialog {  }

        LaunchedEffect(0) {

        }
    }
}

@Composable
fun OnItem(s: String, routeEvent: RouteEvent) {
    Text(text = s, modifier = Modifier.myitem().clickable(
        onClick = { MyRouteEvents.send(routeEvent) }
    ))
    DividerHorizontal16()
}

@Composable
fun MyViewScreen(vml: HomeViewModel) {
    Column(modifier = Modifier.fillMaxWidth().padding(MyDimens.MarginW16).
    verticalScroll(rememberScrollState())) {
        OnItem("contacts", RouteEvent(key = MyNavKey.ContactsNav))
        OnItem("album", RouteEvent(key = MyNavKey.AlbumNav))
        OnItem("弹窗", RouteEvent(key = MyNavKey.DialogNav))
        OnItem("wheel", RouteEvent(key = MyNavKey.WheelNav))
        OnItem("悬浮窗", RouteEvent(key = MyNavKey.SuspendWindowNav))
        OnItem("bigimages", RouteEvent(key = MyNavKey.BigimageNav))
        OnItem("画图测试", RouteEvent(key = MyNavKey.DrawNav))
        OnItem("写字板", RouteEvent(key = MyNavKey.WriteWordNav))
        OnItem("customviews", RouteEvent(key = MyNavKey.CustomViewNav))
        OnItem("心率图", RouteEvent(key = MyNavKey.HeartViewNav))
        OnItem("体温图", RouteEvent(key = MyNavKey.TempViewNav))
        OnItem("血压图", RouteEvent(key = MyNavKey.BpViewNav))
        OnItem("睡眠图", RouteEvent(key = MyNavKey.SleepViewNav))
        OnItem("运动图", RouteEvent(key = MyNavKey.SportViewNav))
        OnItem("心电图", RouteEvent(key = MyNavKey.EcgViewNav))
        OnItem("录音动画图", RouteEvent(key = MyNavKey.RecordAudioViewNav))
        OnItem("自定义下拉刷新", RouteEvent(key = MyNavKey.RefreshViewNav))
    }
}

enum class DragValue { Start, Center, End }

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun ListScreen(vml: HomeViewModel) {
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
                                .offset { IntOffset(x = statew.requireOffset().toInt(), y = 0) })
                    }
                }
        }
    }
}

@Composable
fun OriginalScreen(vml: HomeViewModel) {
    TODO("Not yet implemented")
}

@Composable
fun OtherScreen(vml: HomeViewModel) {
    TODO("Not yet implemented")
}

@Composable
fun GestureScreen(vml: HomeViewModel) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable()
fun Preview(){
    HomeScreen()
}

