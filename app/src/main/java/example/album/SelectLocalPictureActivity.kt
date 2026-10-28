package example.album;

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmcbig.mediapicker.PickerConfig
import com.dmcbig.mediapicker.entity.Media
import com.xiajun.app.MyApp.R
import com.xiajun.app.MyApp.databinding.ActivitySelectLocalPictureBinding
import com.xiajun.base.BaseTitleBarActivity
import com.xiajun.config.FileDirConfig
import com.xiajun.dialog.SelAvatarDialog
import com.xiajun.utils.BitmapUtil
import com.xiajun.utils.SystemShareUtil
import example.viewPager.ImageScanActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class SelectLocalPictureActivity : BaseTitleBarActivity() {

    lateinit var bd: ActivitySelectLocalPictureBinding;
    private lateinit var mViewModel: SelectLocalPictureViewModel
    private lateinit var adapter: DynamicPublishImageAdapter;
    private var dialog: SelAvatarDialog? = null

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = ActivitySelectLocalPictureBinding.inflate(layoutInflater)
        setContentView(bd.root)
        setTitleBarTitle("本地图片选择");
        mViewModel = ViewModelProvider(this)[SelectLocalPictureViewModel::class]
        adapter = DynamicPublishImageAdapter(context, mViewModel.paths);
        var gridLayoutManager = GridLayoutManager(context, 4);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bd.recyclerView.setLayoutManager(gridLayoutManager);
        //bd.recyclerView.addItemDecoration(GridDividerItemDecoration(20, Color.BLACK))
        bd.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener { position, data ->
            var count = adapter.itemCount;
            if (position == count - 1) {
                showSelPicPopupWindow();
            } else {
                var intent = Intent(context, ImageScanActivity::class.java);
                intent.putExtra("list", mViewModel.getPhotos());
                startActivityForResult(intent, PickerConfig.CODE_TAKE_ALBUM);
            }
        }
        adapter.setOnDeleteClickListener(object : DynamicPublishImageAdapter.OnDeleteClickListener {
            override fun onItemClickDelete(position: Int) {
                mViewModel.paths.removeAt(position);
                adapter.notifyItemRemoved(position);
            }
        })
        bd.btnCompress.setOnClickListener { compressImage() }
        bd.btnShareText.setOnClickListener({ v ->
            SystemShareUtil.shareText(context, "文章", "来自系统分享");
        });
        bd.btnShareImage.setOnClickListener { v ->
            SystemShareUtil.shareImage(context, FileDirConfig.FILE_PROVIDER_AUTHORITY,
                mViewModel.paths[0].path
            );
        }
    }

    @SuppressLint("CheckResult")
    fun compressImage() {
        showProgressDialog("压缩中···")
        val disposable = Observable.just(mViewModel.paths).subscribeOn(Schedulers.io())
            .map { it ->
                val list = ArrayList<String>()
                it.forEach {
                    if (!TextUtils.isEmpty(it.path)) {
                        val path =
                            FileDirConfig.DIR_CACHE + File.separatorChar + System.currentTimeMillis() + ".jpg"
                        val bytes = BitmapUtil.compress(it.path, 200)
                        BitmapUtil.saveByte(path, bytes)
                        list.add(path)
                    }
                }
                return@map list
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dismissProgressDialog()
                it.forEach {
                    logE("compressImage", it)
                }
            }, {
                dismissProgressDialog()
                logE("compressImage", "压缩出错")
            })
    }

    fun selAvatar(v: View) {
        showSelPicPopupWindow();
    }

    fun cameraBack(){
        val ind = mViewModel.addPhoto()
        if (ind >= 0)
            adapter.notifyItemInserted(ind);
    }

    fun selectPhotos() {
        mPikerPhotoResult.launch(mViewModel.getPikerPhotosIntent())
    }

    private fun takeCamera() {
        val uri: Uri = mViewModel.getPhotoUri()
        uri.let {
            mCameraResult.launch(uri)
        }
    }

    val mPikerPhotoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
                val newPaths: ArrayList<Media> = it.data!!.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT)!!;
                mViewModel.addPhotoList(newPaths)
                adapter.notifyDataSetChanged();
            }
    }
    val mCameraResult = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if (!it)
            showToast(R.string.file_does_not_exist)
        else {
            cameraBack()
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
            selectPhotos()
        } else {
            showToast(R.string.error_no_external_storage_permission)
        }
    }

    fun showSelPicPopupWindow() {
        if (dialog == null) {
            dialog = SelAvatarDialog(context);
            dialog?.setOnItemClickListener(object : SelAvatarDialog.OnItemClickListener {
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
                    )                }

                override fun OnClickCancel() {
                }
            });
        }
        dialog?.show();
    }
}