package com.xiajun.base;

import android.content.Intent;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.xiajun.toast.ToastManager;
import com.xiajun.utils.DeviceInfoUtil;
import com.xiajun.utils.LogManager;

public abstract class BaseFragment extends Fragment {

    protected String TAG = this.getClass().getSimpleName();

    protected int getStatusHeight() {
        return DeviceInfoUtil.getStatusHeight(requireContext());
    }

    protected void skip(Class<?> cls) {
        if (isAdded())
            startActivity(new Intent(getContext(), cls));
    }

    protected void logE(String tag, String msg) {
        LogManager.logE(tag, msg);
    }

    protected void showToast(CharSequence str) {
        if (isAdded())
            ToastManager.showToast(getContext(), str);
    }

    protected void showToast(@StringRes int resId) {
        if (isAdded())
            ToastManager.showToast(getContext(), resId);
    }
}
