package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.roshine.lspermission.interfaces.Rationale;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.callback.AutoDialogCallback;
import com.roshine.lstypechoblog.callback.PermissionCallBack;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.imageloader.ImageLoaderListener;
import com.roshine.lstypechoblog.imageloader.ImageLoaderManager;
import com.roshine.lstypechoblog.imageloader.ImageLoaderOptions;
import com.roshine.lstypechoblog.mvp.view.activity.base.BaseToolBarActivity;
import com.roshine.lstypechoblog.utils.DialogUtil;
import com.roshine.lstypechoblog.utils.FileUtil;
import com.roshine.lstypechoblog.utils.PermissionUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/8/11 16:36
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 图片预览界面
 */
public class PreImageActivity extends BaseToolBarActivity {

    private static final int PERMISSION_REQUEST_CODE = 30;
    private static final int ACTIVITY_SETTING_REQUEST_CODE = 31;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.iv_media)
    ImageView photoView;

    private String imageTitle = "";
    private String url = "";
    private Bitmap currentBitmap;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
        initText();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.hasExtra("imgTitle")) {
            imageTitle = intent.getStringExtra("imgTitle");
        }
        if (intent.hasExtra("imgUrl")) {
            url = intent.getStringExtra("imgUrl");
        }
    }

    private void initText() {
        tvTitle.setText(imageTitle);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        photoView.setLayoutParams(layoutParams);
//        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(View view, float v, float v1) {
//                LogUtil.show("Roshine", "onPhotoTap");
//            }
//
//            @Override
//            public void onOutsidePhotoTap() {
//                LogUtil.show("Roshine", "onOutsidePhotoTap");
//            }
//        });

//        Glide.with(this).load(url).asBitmap().listener(new RequestListener<String, Bitmap>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                LogUtil.showI("Roshine","resource为空:"+(resource == null));
//                return false;
//            }
//        }).into(photoView);
        ImageLoaderOptions.Builder build = new ImageLoaderOptions.Builder(photoView, url);
        if (url.endsWith(".gif") || url.endsWith(".GIF")) {
            build.asGif(true)
                    .override(screenWidth, 100)
                    .diskCacheStrategy(ImageLoaderOptions.DiskCacheStrategy.SOURCE)
                    .placeholder(-1)
                    .error(-1);
            ImageLoaderOptions option = build.build();
            ImageLoaderManager.getInstance().showImage(option, new ImageLoaderListener<String, GifDrawable>() {

                @Override
                public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    currentBitmap = FileUtil.drawableToBitmap(resource);
                    return false;
                }
            });
        } else {
            build.asGif(false)
                    .override(screenWidth, 100)
                    .placeholder(-1)
                    .error(-1);
            ImageLoaderOptions option = build.build();
            ImageLoaderManager.getInstance().showImage(option, new ImageLoaderListener<String, Bitmap>() {
                @Override
                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    currentBitmap = resource;
                    return false;
                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pre_image;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        ivBack.setVisibility(View.VISIBLE);
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText(getResources().getString(R.string.save_text));
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        supportFinishAfterTransition();
    }

    @OnClick(R.id.tv_title_right)
    void saveClick() {
        if (!PermissionUtil.checkPermissions(this,new String[]{Constants.PermissionNames.PERMISSION_READ_SD, Constants.PermissionNames.PERMISSION_WRITE_SD})) {
            PermissionUtil.requestPermissions(this,
                    new String[]{Constants.PermissionNames.PERMISSION_READ_SD, Constants.PermissionNames.PERMISSION_WRITE_SD},
                    PERMISSION_REQUEST_CODE,
                    new PermissionCallBack() {
                        @Override
                        public void onRationShow(int requestCode, Rationale rationale) {
                            rationale.goPermissionActivity();
                        }

                        @Override
                        public void onRequestSuccess(int requestCode, String[] permissions) {
                            savePicture();
                        }

                        @Override
                        public void onRequestFail(int requestCode, String[] permissions) {
                            DialogUtil.getInstance().showNormalDialog(PreImageActivity.this,
                                    false, getResources().getString(R.string.kindly_reminder),
                                    getResources().getString(R.string.sd_permission_fail),
                                    getResources().getString(R.string.sure_text),
                                    getResources().getString(R.string.cancel_text),
                                    -1,
                                    new AutoDialogCallback() {
                                        @Override
                                        public void onSureClick(DialogInterface dialogInterface, int i, int dialogCode) {
                                            PermissionUtil.goSettingActivityForResult(PreImageActivity.this, ACTIVITY_SETTING_REQUEST_CODE);
                                        }

                                        @Override
                                        public void onCancelClick(DialogInterface dialogInterface, int i, int dialogCode) {

                                        }
                                    });
                        }
                    });
        } else {
            savePicture();
        }
    }

    private void savePicture() {
        if(!flag){
            File mediaFile = FileUtil.getMediaFile();
            String fileName = FileUtil.setFileName() + ".jpg";
            boolean saveFlag = FileUtil.saveBitmap(mediaFile, fileName, currentBitmap);
            if (saveFlag) {
                toast(String.format(getResources().getString(R.string.picture_path_text), mediaFile.getAbsoluteFile()));
                flag = true;
            } else {
                toast(getResources().getString(R.string.picture_save_failed));
            }
        }else{
            toast(getResources().getString(R.string.has_saved_picture));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentBitmap = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTIVITY_SETTING_REQUEST_CODE == requestCode) {
            if (PermissionUtil.checkPermissions(PreImageActivity.this, new String[]{Constants.PermissionNames.PERMISSION_WRITE_SD, Constants.PermissionNames.PERMISSION_READ_SD})) {
                savePicture();
            } else {
                toast(getResources().getString(R.string.open_sd_failed));
            }
        }
    }
}
