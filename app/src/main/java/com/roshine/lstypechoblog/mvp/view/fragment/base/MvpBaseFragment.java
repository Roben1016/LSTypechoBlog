package com.roshine.lstypechoblog.mvp.view.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshine.lstypechoblog.mvp.contract.BaseView;
import com.roshine.lstypechoblog.mvp.contract.IBaseView;
import com.roshine.lstypechoblog.mvp.presenter.IBasePresenter;

/**
 * @author Roshine
 * @date 2017/7/30 19:50
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public abstract class MvpBaseFragment <V extends IBaseView,T extends IBasePresenter<V>> extends BasePageFragment {

    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.onAttachView((V) this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroyView();
        }
    }

    public abstract T getPresenter();
}
