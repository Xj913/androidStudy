package example.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.FlexAlignContent
import androidx.compose.foundation.layout.FlexAlignItems
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.FlexDirection
import androidx.compose.foundation.layout.FlexJustifyContent
import androidx.compose.foundation.layout.FlexWrap
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.style.lib.common.R

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun TitleBarDefault(back: ()-> Unit, title: String = "") {
    Row(modifier = Modifier.background(Colorprimary)) {
        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
        FlexBox(modifier = Modifier.fillMaxWidth().height(48.dp),
            config = {
                direction(FlexDirection.Row)
                alignContent(FlexAlignContent.SpaceAround)
                alignItems(FlexAlignItems.Center)
                justifyContent(FlexJustifyContent.Center)
                wrap(FlexWrap.Wrap)
                gap(16.dp)
            }) {
            Image(painter = painterResource(id = R.drawable.ic_menu_back_white),
                contentDescription = "",
                modifier = Modifier.size(40.dp).clickable {
                    back()
                }
            )
            Text(text = "标题标题", fontSize = 18.sp)
        }
    }
}