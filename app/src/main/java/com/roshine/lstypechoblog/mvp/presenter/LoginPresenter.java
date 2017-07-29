package com.roshine.lstypechoblog.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.model.ILoginModel;
import com.roshine.lstypechoblog.mvp.model.LoginModel;
import com.roshine.lstypechoblog.mvp.view.activity.ArticleListActivity;
import com.roshine.lstypechoblog.mvp.view.activity.LoginActivity;
import com.roshine.lstypechoblog.request.BaseRequest;
import com.roshine.lstypechoblog.request.LsXmlRpcClient;
import com.roshine.lstypechoblog.request.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/7/20 23:52
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LoginPresenter extends BasePresenter<ContractUtil.ILoginView> implements ContractUtil.ILoginPresenter {
    private ContractUtil.ILoginView mILoginView;
    private LoginModel mLoginModel;

    public LoginPresenter(ContractUtil.ILoginView iloginview) {
        mILoginView = iloginview;
        mLoginModel = new LoginModel(this);
    }

    @Override
    public void getAllMethodAndAuthenty(Context context,String url, String userName, String password) {
        mLoginModel.getAllMethod(context,url,userName,password);
    }

    @Override
    public void loginSuccess() {
        mILoginView.loginSuccess();
    }

    @Override
    public void loginFail(String message) {
        mILoginView.loginFail(message);
    }

//    @Override
//    public void loadSuccess(String result) {
//        mILoginView.loadSuccess(result);
//    }
//
//    @Override
//    public void loadServerError(String message) {
//        mILoginView.loadServerError(message);
//    }
//
//    @Override
//    public void loadError(String error) {
//        mILoginView.loadError(error);
//    }
//
//    public void login(String url, String userName, String password) {
//        mLoginModel.login(url,userName,password);
//    }
}
