package com.example.wx.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wx.myapplication.R;

import java.io.ByteArrayOutputStream;

/**
 * 记载图片的工具类
 */
public class ImageLoadUtil {

    public static <T> void loadImageWithDefault(final ImageView imageView, T imgUrl, int defaultId) {
        if (imgUrl != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(defaultId);
            Glide.with(imageView.getContext()).load(imgUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .thumbnail(0.4f)
                    .apply(requestOptions).into(imageView);
        }
    }

    public static void loadImageWithDefault(ImageView imageView, String url) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_spinner_image_load).error(R.drawable.ic_image_load_error).centerCrop().override(452, 256);
            Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
        }
    }


    public static <T>void loadImage(final ImageView imageView, T t){
        Glide.with(imageView.getContext()).load(t)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }


    public static void loadImageByUrl(final ImageView imageView, String imgUrl){
        if(!TextUtils.isEmpty(imgUrl)){
            Glide.with(imageView.getContext()).load(imgUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .thumbnail(0.25f)
                    .into(imageView);
        }
    }

    /**加载图片转换为圆形*/
    public static <T>void loadCircleImage(final ImageView imageView, T path){
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop());
        Glide.with(imageView.getContext())
                .load(path)
                .apply(options)
                .into(imageView);
    }


    public static void loadImageByUrl(final ImageView imageView, String imgUrl, int defaultResId){
        RequestOptions options = new RequestOptions();
        options.placeholder(defaultResId);
        Glide.with(imageView.getContext()).load(imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade()).apply(options)
                .into(imageView);
    }




    public static <T> void loadCircleImageWithDefault(final ImageView imageView, final T imgUrl, final int defaultResId) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CenterCrop());
        Glide.with(imageView.getContext()).load(imgUrl).apply(requestOptions).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                loadCircleImage(imageView, imgUrl);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                loadCircleImage(imageView, defaultResId);
            }
        });

//        Glide.with(imageView.getContext()).load(imgUrl)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .apply(requestOptions)
//                .into(imageView);
    }


    public static void getBitmapFromUrl(String url, int defaultImg, SimpleTarget<Drawable> simpleTarget) {
        Glide.with(BaseLibApp.get()).load(url).into(simpleTarget);
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    public static void doLoadAvatar(ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.error(R.drawable.ic_avatar);
        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }
}
