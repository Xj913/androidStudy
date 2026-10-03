package example.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.result.LocalResultEventBus
import androidx.navigation3.runtime.result.ResultEffect
import com.xiajun.app.MyApp.R
import com.xiajun.base.DividerHorizontal16
import com.xiajun.base.myitem
import com.xiajun.ui.MyDimens
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
                    1 -> GestureScreen(vml)
                    2 -> ListScreen(vml)
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
    Column(modifier = Modifier.fillMaxWidth().padding(MyDimens.MarginW16)) {
        OnItem("contacts", RouteEvent(key = ContactsNav))
        OnItem("album", RouteEvent(key = AlbumNav))
        OnItem("弹窗", RouteEvent(key = DialogNav))
        OnItem("wheel", RouteEvent(key = WheelNav))
        OnItem("悬浮窗", RouteEvent(key = SuspendWindowNav))
        OnItem("bigimages", RouteEvent(key = BigimageNav))
        OnItem("画图测试", RouteEvent(key = DrawNav))
        OnItem("写字板", RouteEvent(key = WriteWordNav))
        OnItem("customviews", RouteEvent(key = CustomViewNav))
        OnItem("心率图", RouteEvent(key = HeartViewNav))
        OnItem("体温图", RouteEvent(key = TempViewNav))
        OnItem("血压图", RouteEvent(key = BpViewNav))
        OnItem("睡眠图", RouteEvent(key = SleepViewNav))
        OnItem("运动图", RouteEvent(key = SportViewNav))
        OnItem("心电图", RouteEvent(key = EcgViewNav))
        OnItem("录音动画图", RouteEvent(key = RecordAudioViewNav))
        OnItem("自定义下拉刷新", RouteEvent(key = RefreshViewNav))
        OnItem("", RouteEvent(key = ContactsNav))
        OnItem("", RouteEvent(key = ContactsNav))
        OnItem("", RouteEvent(key = ContactsNav))
        OnItem("", RouteEvent(key = ContactsNav))

    }
}

@Composable
fun ListScreen(vml: HomeViewModel) {
    TODO("Not yet implemented")
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

