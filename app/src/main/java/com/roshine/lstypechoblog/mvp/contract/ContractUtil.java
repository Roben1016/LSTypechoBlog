package com.roshine.lstypechoblog.mvp.contract;

import android.content.Context;

import com.roshine.lstypechoblog.mvp.presenter.LoginPresenter;

/**
 * @author Roshine
 * @date 2017/7/20 22:27
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 契约工具接口
 */
public interface ContractUtil {

    /*=============登录契约===============*/
    interface ILoginView  extends BaseView {
        void loginSuccess();
        void loginFail(String message);
    }
    //presenter 的接口类
    interface ILoginPresenter{
        void getAllMethodAndAuthenty(Context context,String url, String userName, String password);
        void loginSuccess();
        void loginFail(String message);
    }
}
