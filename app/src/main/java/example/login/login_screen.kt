package example.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import example.base.CpgDialog

@Composable
fun LoginScreen(vml: LoginModel = hiltViewModel(), evt: () -> Unit = {}) {
    val phone = vml.phone.collectAsStateWithLifecycle()
    Column(modifier = Modifier.padding(all = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = phone.value,
            onValueChange = { vml.phone.value = it },
            label = { Text("手机号码") }
        )
        OutlinedTextField(
            value = vml.password.value,
            onValueChange = { vml.password.value = it },
            label = { Text("密码") }
        )
        Button(modifier = Modifier.padding(all = 20.dp).width(100.dp).height(40.dp),
            onClick = {
                vml.login()
            }) { Text("登陆") }

        if (vml.isLoadingShow.value)
            CpgDialog {  }

        LaunchedEffect(0) {
            while (vml.loginState.value == true) {

            }
        }
    }

}

@Preview(showBackground = true)
@Composable()
fun ScreenPreview(){
  LoginScreen()
}

