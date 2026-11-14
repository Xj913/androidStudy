package com.xiajun.helper;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.xiajun.utils.DeviceInfoUtil;

public abstract class InputMethodStateListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "InputMethodState";

    final Rect rect = new Rect();
    private final int screenHeight;
    //默认键盘高度最低为100dp
    private final int keyHeight;
    private final int statusBarHeight;
    private View target;

    public InputMethodStateListener(View root) {
        this.target = root;
        //竖屏时：不包括状态栏和导航栏
        screenHeight = target.getContext().getResources().getDisplayMetrics().heightPixels;
        statusBarHeight = DeviceInfoUtil.getStatusHeight(target.getContext());
        keyHeight = DeviceInfoUtil.dp2px(target.getContext(), 100);
    }

    @Override
    public void onGlobalLayout() {
        //排除系统栏的可见区域尺寸
        target.getWindowVisibleDisplayFrame(rect);
        int invisibleHeight = screenHeight + statusBarHeight - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
        Log.e("onGlobalLayout", "屏幕可用高度-> " + screenHeight + "   底部坐标-> " + rect.bottom + "  被遮挡高度-> " + invisibleHeight);
        if (invisibleHeight > keyHeight) {
            Log.e("onGlobalLayout", "监听到软键盘弹起");
            onInputMethodOpened(invisibleHeight);
        } else {
            Log.e("onGlobalLayout", "监听到软件盘关闭");
            onInputMethodClosed();
        }
    }

    protected abstract void onInputMethodClosed();

    protected abstract void onInputMethodOpened(int invisibleHeight);
}
