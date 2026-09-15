package com.style.data.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.style.app.MyApp.R;

public class ImageLoader {
    public static void loadNormalAvatar(Context context, ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            //Glide.with(context).load(url).error(R.mipmap.ic_launcher).into(imageView);
        }
    }

    public static void loadPicture(FragmentActivity context, ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_photo)
                    .error(R.mipmap.image_fail)
                    .priority(Priority.HIGH);
            Glide.with(context).load(url).apply(options).into(imageView);
        }
    }

    public static void load(Fragment fragment, String url, ImageView iv) {
        if (!TextUtils.isEmpty(url)) {
            //Glide.with(fragment).load(url).into(imageView);
            Glide.with(fragment).load(url).into(iv);
        }
    }

    public static void load(Fragment fragment, Drawable url, ImageView iv) {
        if (url != null) {
            //Glide.with(fragment).load(url).into(imageView);
            Glide.with(fragment).load(url).into(iv);
        }
    }

    public static void load(FragmentActivity fragment, String url, ImageView iv) {
        if (!TextUtils.isEmpty(url)) {
            //Glide.with(fragment).load(url).into(imageView);
            Glide.with(fragment).load(url).into(iv);
        }
    }

    public void release(Context context) {
        //Glide.get(context).
        /*if (getActivity() != null && !getActivity().isDestroyed() && bd.iv != null) {
            Glide.with(context).clear(bd.iv);
        }*/
    }
}
