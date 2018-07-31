package com.roshine.lstypechoblog.mvp.view.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.customview.NormalProgressDialog;
import com.roshine.lstypechoblog.mvp.contract.BaseView;
import com.roshine.lstypechoblog.mvp.view.activity.base.BaseActivity;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Roshine
 * @date 2017/7/30 19:39
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc Fragment基类
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getEventBusEnable()){
            EventBus.getDefault().register(this);
        }
        if(getLayoutId() != 0){
            View view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            initViewData(savedInstanceState);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected boolean getEventBusEnable() {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initViewData(Bundle savedInstanceState);

    @Override
    public void showProgress() {
        showProgress(getActivity().getResources().getString(R.string.loading_text),false);
    }

    @Override
    public void showProgress(String message) {
        showProgress(message,false);
    }

    @Override
    public void showProgress(String message, boolean cancelable) {
        if (getActivity() != null) {
            NormalProgressDialog.showLoading(getActivity(),message,cancelable);
        }
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
        startActivity(new Intent(getActivity(), clz));
    }

    @Override
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) unbinder.unbind();
        this.unbinder = null;
        if(getEventBusEnable()){
            EventBus.getDefault().unregister(this);
        }
    }
}
