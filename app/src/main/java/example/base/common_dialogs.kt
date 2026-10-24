package example.base

import android.view.Gravity
import android.view.View
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.xiajun.app.MyApp.R
import com.xiajun.app.MyApp.databinding.DlgSelAvatarBinding

@Composable
fun CpgDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )) {
        Card(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
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