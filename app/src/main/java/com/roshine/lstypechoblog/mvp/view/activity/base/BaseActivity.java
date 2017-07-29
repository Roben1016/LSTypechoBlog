package com.roshine.lstypechoblog.mvp.view.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.roshine.lstypechoblog.customview.NormalProgressDialog;
import com.roshine.lstypechoblog.mvp.contract.BaseView;
import com.roshine.lstypechoblog.utils.ActivityUtil;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.ToastUtil;
import com.roshine.lstypechoblog.utils.Util;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        ActivityUtil.getInstance().addActivity(this);//添加activity栈
        activity = this;
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        initView();
    }

    protected int getLayoutId(){
        return 0;
    };
    protected void initView(){

    };

    @Override
    public void showProgress() {
        NormalProgressDialog.showLoading(this,"正在加载中",false);
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
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.getInstance().removeActivity(this);//移除activity栈
//        if (hasBus) {
//            EventBus.getDefault().unregister(this);
//        }
    }
}
