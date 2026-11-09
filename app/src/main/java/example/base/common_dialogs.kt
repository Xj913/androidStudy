package example.base

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.xiajun.app.MyApp.R
import com.xiajun.app.MyApp.databinding.DlgSelAvatarBinding
import com.xiajun.ui.MyColors

@Composable
fun CpgDialog() {
    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Box(
            modifier = Modifier.size(100.dp)
                .background(MyColors.BlackTranslucent, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = Color.LightGray,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun Pre(){
    CpgDialog()
}

@Composable
fun SelectAvatarDialog(camera: () -> Unit, album: () -> Unit, cancel: () -> Unit) {
    Dialog(onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.apply {
                /*setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )*/
                setGravity(Gravity.BOTTOM)
                setWindowAnimations(R.style.Animations_SlideInFromBottom_OutToBottom)
                val bd = DlgSelAvatarBinding.inflate(layoutInflater)
                setContentView(bd.root)
                bd.itemCamera.setOnClickListener {
                    camera()
                }
                bd.itemPhoto.setOnClickListener {
                    album()
                }
                bd.itemCancel.setOnClickListener {
                    cancel()
                }
            }
        }
    }
}