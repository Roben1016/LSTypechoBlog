package com.roshine.lstypechoblog.imageloader.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.roshine.lstypechoblog.imageloader.IImageLoaderstrategy;
import com.roshine.lstypechoblog.imageloader.ImageLoaderListener;
import com.roshine.lstypechoblog.imageloader.ImageLoaderOptions;
import com.roshine.lstypechoblog.utils.BitmapUtils;
import com.roshine.lstypechoblog.utils.LogUtil;

/**
  *
  * @author Roshine
  * @date 2017/8/11 14:59
  * @desc glide imgeloader
  *
  */
public class GlideImageLoader implements IImageLoaderstrategy {
    private Handler mainHandler=new Handler();
    @Override
    public void showImage(ImageLoaderOptions options) {
        GenericRequestBuilder mGenericRequestBuilder = init(options);
        if (mGenericRequestBuilder != null) {
            showImageLast(mGenericRequestBuilder, options,null);
        }
    }
    @Override
    public void showImage(ImageLoaderOptions options,ImageLoaderListener listener) {
        GenericRequestBuilder mGenericRequestBuilder = init(options);
        if (mGenericRequestBuilder != null) {
            showImageLast(mGenericRequestBuilder, options,listener);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory();

        }
    }

    @Override
    public void init(Context context) {

    }


    public DrawableTypeRequest getGenericRequestBuilder(RequestManager manager, ImageLoaderOptions options) {

        if (!TextUtils.isEmpty(options.getUrl())) {
            return manager.load(options.getUrl());
        }
        return manager.load(options.getResource());
    }

    public RequestManager getRequestManager(Context context) {
        return Glide.with(context);

    }

    public GenericRequestBuilder init(ImageLoaderOptions options) {
        View v = options.getViewContainer();
        //存在问题
        // java.lang.IllegalArgumentException You cannot start a load for a destroyed activity
        //RequestManager manager=getRequestManager(v.getContext());
        RequestManager manager = getRequestManager(v.getContext());
        if (v instanceof ImageView) {
            GenericRequestBuilder mDrawableTypeRequest = getGenericRequestBuilder(manager, options).asBitmap();
            if (options.isAsGif()) {
                mDrawableTypeRequest = getGenericRequestBuilder(manager, options);
            }
            //装载参数
            mDrawableTypeRequest = loadGenericParams(mDrawableTypeRequest, options);
            return mDrawableTypeRequest;
        }
        return null;
    }


    private GenericRequestBuilder loadGenericParams(GenericRequestBuilder mGenericRequestBuilder, final ImageLoaderOptions options) {
        final View view = options.getViewContainer();
        GenericRequestBuilder builder = mGenericRequestBuilder;
        if (mGenericRequestBuilder instanceof DrawableTypeRequest) {
            if (options.isCrossFade()) {
                ((DrawableTypeRequest) mGenericRequestBuilder).crossFade();
            }
            if (options.isAsGif()) {
                builder = ((DrawableTypeRequest) mGenericRequestBuilder).asGif();
            }
        }
        builder.skipMemoryCache(options.isSkipMemoryCache());
        if (options.getImageSize() != null) {
            int width = getSize(options.getImageSize().getWidth(), view);
            int height = getSize(options.getImageSize().getHeight(), view);
            Log.i("tag ","load params " + options.getImageSize().getWidth() + "  : " + options.getImageSize().getHeight());
            builder.override(width, height);
        }
        if (options.getHolderDrawable() != -1) {
            builder.placeholder(options.getHolderDrawable());
        }
        if (options.getErrorDrawable() != -1) {
            builder.error(options.getErrorDrawable());
        }

        if (options.getDiskCacheStrategy() != ImageLoaderOptions.DiskCacheStrategy.DEFAULT) {
            switch (options.getDiskCacheStrategy()) {
                case NONE:
                    builder.diskCacheStrategy(DiskCacheStrategy.NONE);
                    break;
                case All:
                    builder.diskCacheStrategy(DiskCacheStrategy.ALL);
                    break;
                case SOURCE:
                    builder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                    break;
                case RESULT:
                    builder.diskCacheStrategy(DiskCacheStrategy.RESULT);
                    break;
                default:
                    break;
            }
        }
        return builder;

    }

    private void showImageLast(GenericRequestBuilder mDrawableTypeRequest, ImageLoaderOptions options,ImageLoaderListener listener) {
        final ImageView img = (ImageView) options.getViewContainer();

        // 是否使用高斯模糊
        if (options.isBlurImage()) {
            // 具体的高斯模糊这里就不实现了，直接展示图片
            mDrawableTypeRequest.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                    if (resource != null && img!=null) {
                        try {
                            final Bitmap result = BitmapUtils.fastBlur(img.getContext().getApplicationContext(), resource, 15);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (result != null && img!=null) {
                                        img.setImageBitmap(result);
                                    }
                                }
                            });
                        } catch (OutOfMemoryError e) {
                            if (img != null && resource!=null) {
                                img.setImageBitmap(resource);
                            }
                        }
                    } else {
                        LogUtil.show("Roshine","resource was null");
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    LogUtil.show("Roshine","resource load failed");
                    if (e != null) e.printStackTrace();
                }
            });
            return;
        }
        if(listener != null){
            mDrawableTypeRequest.listener(listener);
//            mDrawableTypeRequest.into(img);
//            return;
        }
        // 是否展示一个gif
        if (options.isAsGif()) {
            ((GifRequestBuilder) mDrawableTypeRequest).dontAnimate().into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                    img.setImageDrawable(resource);
                    resource.start();
                }
            });
            return;
        }
        if (options.getTarget() != null) {
            mDrawableTypeRequest.into(options.getTarget());
            return;
        }
        mDrawableTypeRequest.into(img);
    }

    /**
     * 获取资源尺寸
     *
     * @param resSize
     * @return 默认返回原始尺寸
     */
    private int getSize(int resSize, View container) {
        if (resSize <= 0) {
            return SimpleTarget.SIZE_ORIGINAL;
        } else {
            try {
                return container.getContext().getResources().getDimensionPixelOffset(resSize);

            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                return resSize;
            }
        }
    }

}
