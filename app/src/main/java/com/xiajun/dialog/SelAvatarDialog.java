package com.xiajun.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.xiajun.app.MyApp.databinding.DlgSelAvatarBinding;
import com.xiajun.base.BaseDialog;
import com.xiajun.app.MyApp.R;

public class SelAvatarDialog extends BaseDialog {

    private DlgSelAvatarBinding bd;
    private Button bt_takePhoto;
    private Button bt_selPhoto;
    private OnItemClickListener mListener;
    private Button bt_selCancel;

    public SelAvatarDialog(Context context) {
        super(context, com.xiajun.lib.common.R.style.Dialog_General);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = DlgSelAvatarBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        Window window = getWindow();
        //默认对话框会有边距，宽度不能占满屏幕
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.Animations_SlideInFromBottom_OutToBottom);
        bd.itemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.OnClickCamera();
                dismiss();
            }
        });
        bd.itemPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.OnClickPhoto();
                dismiss();
            }
        });
        bd.itemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.OnClickCancel();
                dismiss();
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        if (mListener != null)
            this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void OnClickCamera();

        void OnClickPhoto();

        void OnClickCancel();
    }
}
