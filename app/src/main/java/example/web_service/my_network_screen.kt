package example.web_service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import example.base.CpgDialog

@Composable
fun NetworkScreen(vml: MyNetworkModel = hiltViewModel(), evt: () -> Unit = {}) {
    Column(modifier = Modifier.padding(all = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = vml.phone.value,
            onValueChange = { vml.phone.value = it },
            label = { Text("手机号码") }
        )
        Button(
            modifier = Modifier.padding(all = 20.dp).width(100.dp).height(40.dp),
            onClick = {
                vml.getPhoneInfo()
            }) { Text("查询归属地") }
        TextField(
            value = vml.city.value,
            onValueChange = { vml.city.value = it },
            placeholder = { Text("city id") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent, // 下划线颜色 focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                errorIndicatorColor = MaterialTheme.colorScheme.error, // 去掉背景圆角，让下划线贴底 focusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            shape = RectangleShape // 去除圆角，避免下划线上方有白边
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(all = 20.dp).width(100.dp).height(40.dp),
            onClick = {
                vml.getPhoneInfo()
            }) { Text("返回string") }
        Button(
            modifier = Modifier.fillMaxWidth().padding(all = 20.dp).width(100.dp).height(40.dp),
            onClick = {
                vml.getPhoneInfo()
            }) {
            Text("返回bean")
            Button(
                modifier = Modifier.fillMaxSize().padding(all = 20.dp).width(100.dp).height(40.dp),
                onClick = {
                    vml.getPhoneInfo()
                }) { Text("body") }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = vml.content.value
            )



            if (vml.isLoadingShow.value)
                CpgDialog { }

        }
    }
}

@Preview(showBackground = true)
@Composable()
fun ScreenPreview(){
    NetworkScreen()
}

