package example.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.FlexAlignContent
import androidx.compose.foundation.layout.FlexAlignItems
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.FlexDirection
import androidx.compose.foundation.layout.FlexJustifyContent
import androidx.compose.foundation.layout.FlexWrap
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiajun.lib.common.R
import com.xiajun.ui.MyColors
import com.xiajun.ui.MyDimens
import com.xiajun.ui.MyTextStyles
import example.home.HomeScreen
import example.home.MyRouteEvents
import example.home.RouteEvent

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun TitleBarDefault(back: ()-> Unit = { MyRouteEvents.send(RouteEvent(isBack = true)) }, title: String = " ", menu: @Composable ()-> Unit = { MenuPlace() }) {
    Column(
        modifier = Modifier.fillMaxWidth().background(MyColors.ColorPrimary)
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
            FlexBox(modifier = Modifier.fillMaxWidth().height(MyDimens.TitlebarH),
                config = {
                    direction(FlexDirection.Row)
                    justifyContent(FlexJustifyContent.SpaceBetween)
                    alignItems(FlexAlignItems.Center)
                }) {
                Image(painter = painterResource(id = R.drawable.ic_menu_back_white),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp).padding(10.dp).clickable {
                        back()
                    }
                )
                Text(text = title, style = MyTextStyles.textWhite18sp)
                menu()
            }
    }
}

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun TitleBarWhite(back: ()-> Unit = { MyRouteEvents.send(RouteEvent(isBack = true)) }, title: String = "", menu: @Composable ()-> Unit = { MenuPlace() }) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White).statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
            FlexBox(modifier = Modifier.fillMaxWidth().height(MyDimens.TitlebarH),
                config = {
                    direction(FlexDirection.Row)
                    alignContent(FlexAlignContent.SpaceBetween)
                    alignItems(FlexAlignItems.Center)
                }) {
                Image(painter = painterResource(id = R.drawable.ic_menu_back_black),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp).padding(10.dp).clickable {
                        back()
                    }
                )
                Text(text = title, style = MyTextStyles.textSecond18sp)
                menu()
            }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = MyDimens.DividerH,
            color = MyColors.Divider
        )
    }
}

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun TitleBarTransparent(back: ()-> Unit = { MyRouteEvents.send(RouteEvent(isBack = true)) }, title: String = "", menu: @Composable ()-> Unit = { MenuPlace() }) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.Transparent)
    )  {
        Spacer(modifier = Modifier.fillMaxWidth().height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
            FlexBox(modifier = Modifier.fillMaxWidth().height(MyDimens.TitlebarH),
                config = {
                    direction(FlexDirection.Row)
                    alignContent(FlexAlignContent.SpaceBetween)
                    alignItems(FlexAlignItems.Center)
                }) {
                Image(painter = painterResource(id = R.drawable.ic_menu_back_white),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp).padding(10.dp).clickable {
                        back()
                    }
                )
                Text(text = title, style = MyTextStyles.textWhite18sp)
                menu()
            }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = MyDimens.DividerH,
            color = MyColors.Divider
        )
    }
}

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun TitleBarTransparentDark(back: ()-> Unit = { MyRouteEvents.send(RouteEvent(isBack = true)) }, title: String = "", menu: @Composable ()-> Unit = { MenuPlace() }) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.Transparent)
    )  {
        Spacer(modifier = Modifier.fillMaxWidth().height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
        FlexBox(modifier = Modifier.fillMaxWidth().height(MyDimens.TitlebarH),
            config = {
                direction(FlexDirection.Row)
                alignContent(FlexAlignContent.SpaceBetween)
                alignItems(FlexAlignItems.Center)
            }) {
            Image(painter = painterResource(id = R.drawable.ic_menu_back_black),
                contentDescription = "",
                modifier = Modifier.size(40.dp).padding(10.dp).clickable {
                    back()
                }
            )
            Text(text = title, style = MyTextStyles.textSecond18sp)
            menu()
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = MyDimens.DividerH,
            color = MyColors.Divider
        )
    }
}

@Composable
fun MenuPlace() {
    Text(text = " ", modifier = Modifier.size(40.dp))
}

@Preview(showBackground = true)
@Composable()
fun Pre2(){
    TitleBarDefault({})
}
