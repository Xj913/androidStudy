package example.album

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.FlexAlignContent
import androidx.compose.foundation.layout.FlexAlignItems
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.FlexDirection
import androidx.compose.foundation.layout.FlexWrap
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.xiajun.app.MyApp.R
import example.base.SelectAvatarDialog
import example.base.TitleBarWhite
import example.base.rememberExecuteOnPermissions

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun PikerPhotoScreen(vml: PikerPhotoViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val mCropResult = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
                it.data?.let { data ->
                val bitmap = data.extras!!.getParcelable<Bitmap>("data")
                capturedImageBitmap = bitmap
            }
        }
    }
    val mCameraResult = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            mCropResult.launch(vml.getCropIntent())
        }
    }
    val mPhotoResult = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            vml.uri = it
            mCropResult.launch(vml.getCropIntent())
        }
    }
    val mRequestCamera = rememberExecuteOnPermissions(
        context = context,
        permissionMap = mapOf(Manifest.permission.CAMERA to "此权限用于拍照，无此权限无法进行拍照")
    ) {
            mCameraResult.launch(vml.getPhotoUri())
    }
    val mSinglePike = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let { vml.paths.add(it) }
    }
    val mMultiPike = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) {
        it.let { vml.paths.addAll(it) }
    }
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        TitleBarWhite(title = "媒体选择")
                if (capturedImageBitmap != null) {
                    Image(
                        bitmap = capturedImageBitmap!!.asImageBitmap(),
                        modifier = Modifier,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))
        FlexBox(modifier = Modifier.fillMaxWidth().height(48.dp),
            config = {
                direction(FlexDirection.Row)
                wrap(FlexWrap.Wrap)
                alignContent(FlexAlignContent.Start)
                alignItems(FlexAlignItems.Start)
                gap(8.dp)
            }) {
            vml.paths.forEach { it ->
                AsyncImage(modifier = Modifier.size(100.dp),
                    model = it,
                    contentDescription = null,
                    error = painterResource(R.mipmap.image_fail)
                )
            }
            Image(painter = painterResource(R.mipmap.ic_add_photo),
                "", modifier = Modifier.clickable(onClick = { vml.bottomDlgShow.value = true }))
        }
            Text("请选择裁剪图片",
                modifier = Modifier.clickable(onClick = { vml.bottomDlgShow.value = true }),
                textAlign = TextAlign.Center
            )
        Button(onClick = {
            mSinglePike.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) { Text(text = "选择一张图片") }
        Button(onClick = {
            mMultiPike.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) { Text(text = "选择多张图片") }
    }
    if (vml.bottomDlgShow.value)
        SelectAvatarDialog(
            camera = { mRequestCamera.execute(context) }
            ,album = {
                mPhotoResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
            vml.bottomDlgShow.value = false
        }
}


