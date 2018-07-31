package com.roshine.lstypechoblog.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.roshine.lstypechoblog.imageloader.glide.GlideImageLoader;


/**
  *
  * @author Roshine
  * @date 2017/8/11 14:57
  * @desc imageLoader管理类
  *
  */
public class ImageLoaderManager implements IImageLoaderstrategy {
    private static final ImageLoaderManager INSTANCE=new ImageLoaderManager();
    private  IImageLoaderstrategy loaderstrategy;
    private ImageLoaderManager(){
    }
    public static ImageLoaderManager getInstance(){
        return INSTANCE;
    }

    public  void setImageLoaderStrategy(IImageLoaderstrategy strategy){
        loaderstrategy=strategy;
    }

    /*
     *   可创建默认的Options设置，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url){
        return new ImageLoaderOptions.Builder(container,url).isCrossFade(true).build();
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        if (loaderstrategy != null) {
            loaderstrategy.showImage(options);
        }
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options, ImageLoaderListener listener) {
        if (loaderstrategy != null) {
            loaderstrategy.showImage(options,listener);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        loaderstrategy.cleanMemory(context);
    }

    // 在application的oncreate中初始化
    @Override
    public void init(Context context) {
//        loaderstrategy=new FrescoImageLoader();
        loaderstrategy=new GlideImageLoader();
        loaderstrategy.init(context);
    }

}
