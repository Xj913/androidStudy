package example.album

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.FaceDetector
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xiajun.app.MyApp.R
import com.xiajun.app.MyApp.databinding.ActivityGlideDealBinding
import com.xiajun.base.BaseTitleBarActivity
import com.xiajun.data.glide.RectTopCornerTransform
import com.xiajun.data.glide.RoundRectTransform
import com.xiajun.data.glide.RoundTransform
import com.xiajun.dialog.SelAvatarDialog
import com.xiajun.utils.BitmapUtil
import java.io.File

class GlideDealActivity : BaseTitleBarActivity() {
    private lateinit var bd: ActivityGlideDealBinding
    private lateinit var mViewModel: ImageDealViewModel
    private var dialog: SelAvatarDialog? = null

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = ActivityGlideDealBinding.inflate(layoutInflater)
        setContentView(bd.getRoot())
        mViewModel = ViewModelProvider(this)[ImageDealViewModel::class]
        bd.btnCircle.setOnClickListener { _: View? -> dealCircle() }
        bd.btnCircleBorder.setOnClickListener { _: View? -> dealCircleBorder() }
        bd.btnRound.setOnClickListener { _: View? -> dealRound() }
        bd.btnRectStroke.setOnClickListener { _: View? -> dealRectStroke() }
        bd.btnRectTop.setOnClickListener { _: View? -> dealRectTop() }
        bd.btnImageSize.setOnClickListener { _: View? -> compressImage() }
        bd.btnAvatar.setOnClickListener { _: View? -> showSelPicPopupWindow() }
        bd.btnCatchColor.setOnClickListener { _: View? ->
            //bd.ivAvatar.setImageResource(R.mipmap.home_banner_3);
            bd.ivAvatar.setDrawingCacheEnabled(true)
            val bitmap = Bitmap.createBitmap(bd.ivAvatar.getDrawingCache())
            bd.ivAvatar.setDrawingCacheEnabled(false)
            // 用来提取颜色的Bitmap
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.home_banner_3);
            catchColor(bitmap)
        }
        downImage()
    }

    private fun compressImage() {
        //String path = "/storage/emulated/0/aaaaStyle/cache/1574660481789.jpg";
        val path = "/storage/emulated/0/aaaaStyle/cache/1574493622274.jpg"
        val b = BitmapUtil.getThumbnail(path, bd.iv3.width, bd.iv3.height)
        val b2 = BitmapUtil.centerCrop(b, bd.iv3.width, bd.iv3.height)
        bd.iv3.setImageBitmap(b2)
    }

    private fun dealRectTop() {
        val url =
            "http://test-assets.wujinpu.cn/goods/goodsInfoImg/1a8e3067505640458199991ead4ee66e/4811568189101270.jpg?x-oss-process=style/280"
        val myOptions = RequestOptions().transform(RectTopCornerTransform(8f))
            .error(R.mipmap.empty_photo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(url).apply(myOptions).into(bd!!.iv1)
    }

    private fun dealCircle() {
        val myOptions =
            RequestOptions().transform(RoundTransform()).diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(R.mipmap.empty_photo).apply(myOptions).into(bd!!.iv1)
    }

    private fun dealCircleBorder() {
        val myOptions = RequestOptions().transform(RoundTransform(1.5f, -0x5147))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(R.mipmap.empty_photo).apply(myOptions).into(bd!!.iv1)
    }

    private fun dealRound() {
        val myOptions = RequestOptions().transform(RoundRectTransform(8f))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(R.mipmap.empty_photo).apply(myOptions).into(bd!!.iv1)
    }

    private fun dealRectStroke() {
        val myOptions = RequestOptions().transform(RoundRectTransform(8f, 4f, -0x9cb9))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(R.mipmap.empty_photo).apply(myOptions).into(bd!!.iv1)
    }

    private fun downImage() {
        val url = "https://test-assets.wujinpu.cn/back/banner/8881573630042201.jpg"
        val myOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).asBitmap().load(url).apply(myOptions)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val b =
                        BitmapUtil.centerCrop(resource, bd!!.iv2.getWidth(), bd!!.iv2.getHeight())
                    bd!!.iv2.setImageBitmap(b)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

     fun onAvatarCropped() {
        val f = File(mViewModel.targetFilePath)
        Log.e(tag, "文件大小   " + f.length() / 1024)
        val myOptions = RequestOptions()
        Glide.with(this).asBitmap().load(f).apply(myOptions)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    catchColor(resource)
                    bd.ivAvatar.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        val b = BitmapFactory.decodeFile(mViewModel.targetFilePath, options)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //如果已知，则解码后的位图将具有配置。
            if (options.outConfig != null) {
                Log.e("outConfig", options.outConfig.toString() + "")
            }
        }
        val faceDet = FaceDetector(b.getWidth(), b.getHeight(), 1)
        val faceList = arrayOfNulls<FaceDetector.Face>(1)
        val n = faceDet.findFaces(b, faceList)
        Log.e("人脸数", n.toString() + "")
    }

    fun toCrop(isCamera: Boolean, uri: Uri?) {
        mViewModel.modifyPhoto(isCamera, uri)
        mCropPhotoResult.launch(mViewModel.getCropIntent())
    }

    fun selectPhoto() {
        mPhotoResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    fun takeCamera() {
        val uri: Uri? = mViewModel.getPhotoUri()
        uri?.let {
            mCameraResult.launch(uri)
        }
    }

     val mCropPhotoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            onAvatarCropped()
        }
     }
     val mCameraResult = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
         if (!it)
             showToast(R.string.file_does_not_exist)
         else {
             toCrop(true, null)
         }
     }
     val mPhotoResult = registerForActivityResult(ActivityResultContracts.PickVisualMedia()
     ) { it ->
         if (it == null)
             showToast(R.string.file_does_not_exist)
         it?.let {
             toCrop(false, it)
         }
     }
     val mRequestCamera = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
     ) { it ->
             val allGranted = it.entries.all { it.value }
             if (allGranted) {
                 takeCamera()
             } else {
                 showToast(R.string.error_no_camera_and_external_storage_permission)
             }
         }
     val mRequestStorage = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
     ) { it ->
             val allGranted = it.entries.all { it.value }
             if (allGranted) {
                 selectPhoto()
             } else {
                 showToast(R.string.error_no_external_storage_permission)
             }
         }

     fun showSelPicPopupWindow() {
             if (dialog == null) {
                 dialog = SelAvatarDialog(this)
                 dialog!!.setOnItemClickListener(object : SelAvatarDialog.OnItemClickListener {
                     override fun OnClickCamera() {
                         mRequestCamera.launch(
                             arrayOf(
                                 Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                 Manifest.permission.READ_EXTERNAL_STORAGE,
                                 Manifest.permission.CAMERA,
                             )
                         )
                     }

                     override fun OnClickPhoto() {
                         mRequestStorage.launch(
                             arrayOf(
                                 Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                 Manifest.permission.READ_EXTERNAL_STORAGE,
                             )
                         )
                     }

                     override fun OnClickCancel() {
                     }
                 })
             }
             dialog!!.show()
     }

     fun catchColor(bitmap: Bitmap) {
             val pb = Palette.Builder(bitmap)
             pb.generate { palette -> //暗、柔和
                 val darkMutedColor = palette!!.getDarkMutedColor(Color.BLACK) //如果分析不出来，则返回默认颜色
                 //亮、柔和
                 val lightMutedColor = palette.getLightMutedColor(Color.BLACK)
                 //暗、鲜艳
                 val darkVibrantColor = palette.getDarkVibrantColor(Color.BLACK)
                 //亮、鲜艳
                 val lightVibrantColor = palette.getLightVibrantColor(Color.BLACK)
                 //柔和
                 val mutedColor = palette.getMutedColor(Color.BLACK)
                 //鲜艳
                 val vibrantColor = palette.getVibrantColor(Color.BLACK)
                 bd!!.btnCatchColor.setBackgroundColor(vibrantColor)
                 /*//获取某种特性颜色的样品
                         //Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                         Palette.Swatch lightVibrantSwatch = palette.getVibrantSwatch();
                         //谷歌推荐：图片的整体的颜色rgb的混合值---主色调
                         int rgb = lightVibrantSwatch.getRgb();
                         //谷歌推荐：图片中间的文字颜色
                         int bodyTextColor = lightVibrantSwatch.getBodyTextColor();
                         //谷歌推荐：作为标题的颜色（有一定的和图片的对比度的颜色值）
                         int titleTextColor = lightVibrantSwatch.getTitleTextColor();
                         //颜色向量
                         float[] hsl = lightVibrantSwatch.getHsl();
                         //分析该颜色在图片中所占的像素多少值
                         int population = lightVibrantSwatch.getPopulation();*/
             }
     }
}