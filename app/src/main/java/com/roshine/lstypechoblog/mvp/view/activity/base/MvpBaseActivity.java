package com.roshine.lstypechoblog.mvp.view.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.roshine.lstypechoblog.mvp.contract.BaseView;
import com.roshine.lstypechoblog.mvp.presenter.BasePresenter;
import com.roshine.lstypechoblog.mvp.view.activity.base.BaseToolBarActivity;

/**
 * @author Roshine
 * @date 2017/7/20 23:37
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public abstract class MvpBaseActivity<V extends BaseView,T extends BasePresenter<V>> extends BaseToolBarActivity {
    T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onAttachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroyView();
        }
    }

    public abstract T getPresenter();
}
