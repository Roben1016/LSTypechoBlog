package com.roshine.lstypechoblog.mvp.view.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.customview.NormalProgressDialog;
import com.roshine.lstypechoblog.mvp.contract.BaseView;
import com.roshine.lstypechoblog.utils.ActivityUtil;
import com.roshine.lstypechoblog.utils.DisplayUtil;
import com.roshine.lstypechoblog.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Roshine
 * @date 2017/7/21 21:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 所有activity通用基类
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    protected Activity activity;
    private Unbinder unbinder;
    protected int screenWidth;
    protected int screenHeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenHeight = DisplayUtil.getScreenHeight(this);
        screenWidth = DisplayUtil.getScreenWidth(this);
        ActivityUtil.getInstance().addActivity(this);//添加activity栈
        activity = this;
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
        }
        initViewData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initViewData(Bundle savedInstanceState);

    @Override
    public void showProgress() {
        showProgress(this.getString(R.string.loading_text),false);
    }

    @Override
    public void showProgress(String message) {
        showProgress(message,false);
    }

    @Override
    public void showProgress(String message, boolean cancelable) {
        NormalProgressDialog.showLoading(this,message,cancelable);
    }

    @Override
    public void hideProgress() {
        NormalProgressDialog.stopLoading();
    }

    @Override
    public void toast(String message) {
        ToastUtil.showSingleToast(message);
    }

    @Override
    public void toastLong(String message) {
        ToastUtil.showLong(message);
    }

    @Override
    public void toastWithTime(int time, String message) {
        ToastUtil.showWithTime(message,time);
    }

    @Override
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    @Override
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.getInstance().removeActivity(this);//移除activity栈
        if (unbinder != null && unbinder != Unbinder.EMPTY) unbinder.unbind();
        this.unbinder = null;
        NormalProgressDialog.stopLoading();
//        if (hasBus) {
//            EventBus.getDefault().unregister(this);
//        }
    }
}
