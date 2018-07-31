package com.roshine.lstypechoblog.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 *
 * @author Roshine
 * @date 2017/8/11 14:57
 * @desc imageLoader接口
 *
 */
public interface IImageLoaderstrategy {
    void showImage(@NonNull ImageLoaderOptions options);
    void showImage(@NonNull ImageLoaderOptions options,ImageLoaderListener listener);
    void cleanMemory(Context context);
    // 在application的oncreate中初始化
    void init(Context context);
}
