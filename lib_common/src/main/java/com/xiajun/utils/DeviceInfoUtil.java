package com.xiajun.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DeviceInfoUtil {
    private static final String TAG = "DeviceInfoUtil";

    public static int dp2px(Context context, float dpValue) {
        float pxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
        return (int) (pxValue + 0.5f);//0.5f是为了四舍五入，但有时候四舍五入不一定就好
    }

    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static Display getDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        getDisplay(context).getMetrics(metric);
        int width = metric.widthPixels;      // 屏幕宽度（像素）
        int height = metric.heightPixels;    // 屏幕高度（像素）
        float density = metric.density;      // densityDpi/160的比值 sharpR3:4
        int densityDpi = metric.densityDpi;  // 屏幕密度The screen density expressed as dots-per-inchDPI(根据屏幕分辨率认为规定的一个值)(160像素/英寸) sharpR3:640
        float scaledDensity = metric.scaledDensity;  // 系统设置里面的字体大小缩放值
        return metric;
    }

    public static int getStatusHeight(Context context) {
        int statusBarHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics());
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG, "状态栏-高度:" + statusBarHeight);
        return statusBarHeight;
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String id = tm.getDeviceId();
            if (id != null) {
                return tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void openEditSms(Context context, String phone) {
        Uri smsToUri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "我最近成为了脉连的用户，可以随时查找与别人的连系，让交友变得简单可信。交朋友、找关系好用极了，推荐给你试试（下载链接:www.yimxl.com）。");
        context.startActivity(intent);
    }

    public static void scanFile(Context context, File photoFile) {
        MediaScannerConnection.scanFile(context, new String[]{photoFile.getAbsolutePath()}, null, (path, uri) -> {
            Log.e("onScanCompleted ", path);
            Log.e("onScanCompleted ", "" + uri);
        });
    }

    public static int[] getViewLocationOnScreen(View view) {
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        int x = locations[0];// 获取组件当前位置的横坐标
        int y = locations[1];// 获取组件当前位置的纵坐标
        return locations;
    }

    /**
     * 需要先判断SD卡权限，再判断是否可用
     *
     * @return
     */
    public static boolean isSDcardWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isSDcardReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.getHostAddress().isEmpty()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiIpAddress==", ex.toString());
        }
        return null;
    }
}
