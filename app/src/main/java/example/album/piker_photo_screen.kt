package example.album

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import example.base.rememberExecuteOnPermissions

@Composable
fun PikerPhotoScreen(vml: PikerPhotoViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            imageUri?.let { uri ->
                val bitmap = ImageDecoder.createSource(context.contentResolver, uri
                )
                capturedImageBitmap = ImageDecoder.decodeBitmap(bitmap)
            }
        }
    }
    val executeLauncher = rememberExecuteOnPermissions(
        context = context,
        permissionMap = mapOf(Manifest.permission.CAMERA to "此权限用于拍照，无此权限无法进行拍照")
    ) {
            cameraLauncher.launch(vml.getPhotoUri())
    }

    if (capturedImageBitmap != null) {
        Image(
            bitmap = capturedImageBitmap!!.asImageBitmap(),
            modifier = Modifier,
            contentDescription = null
        )
    } else {
        Box(
            Modifier.fillMaxSize()
        ) {
            Text("请选择图片",
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                    .clickable(enabled = true, onClick = { executeLauncher.execute(context) }), textAlign = TextAlign.Center)
        }
    }
}


