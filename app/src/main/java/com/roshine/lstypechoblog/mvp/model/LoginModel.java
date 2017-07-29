package com.roshine.lstypechoblog.mvp.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.request.BaseRequest;
import com.roshine.lstypechoblog.request.LsXmlRpcClient;
import com.roshine.lstypechoblog.request.RequestCallBack;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/7/21 0:04
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LoginModel implements ILoginModel {

    private ContractUtil.ILoginPresenter mILoginPresenter;

    public LoginModel(ContractUtil.ILoginPresenter iLoginPresenter) {
        mILoginPresenter = iLoginPresenter;
    }

    @Override
    public void getAllMethod(Context context, String url, String userName, String password) {
        List<Object> parameters = new ArrayList<>();
//        parameters.add(url);
        BaseRequest excute = LsXmlRpcClient.request(context)
                .url(url)
                .timeOut(30)
                .method("system.listMethods")
                .addParameters(parameters)
                .callback(new RequestCallBack() {
                    @Override
                    public void onResponse(String result) {
                        SPUtil.setParam(context, "userTotalMethod", result);
                        SPUtil.setParam(context, "blogUrl", url);
                        getAuthenty(context, userName, password);
                    }

                    @Override
                    public void onError(String error) {
                        LogUtil.showI("Roshine", "onError: " + error);
                        mILoginPresenter.loginFail(error);
                    }

                    @Override
                    public void onServerError(String error) {
                        LogUtil.showI("Roshine", "onError: " + error);
                        mILoginPresenter.loginFail(error);
                    }
                }).excute();
    }

    @Override
    public void getAuthenty(Context context, String userName, String password) {
        if (SPUtil.getParam(context, "userTotalMethod", "").toString().contains("wp.getUsersBlogs")
                && SPUtil.getParam(context, "blogUrl", "") != null) {
            List<Object> parameters = new ArrayList<>();
            parameters.add(userName);
            parameters.add(password);
            BaseRequest excute = LsXmlRpcClient.request(context)
                    .url(SPUtil.getParam(context, "blogUrl", "").toString())
                    .timeOut(30)
                    .method("wp.getUsersBlogs")
                    .addParameters(parameters)
                    .callback(new RequestCallBack() {
                        @Override
                        public void onResponse(String result) {
                            Log.d("Roshine", "onResponse: " + result);
                            SPUtil.setParam(context, "userName", userName);
                            SPUtil.setParam(context, "userPassword", password);
                            mILoginPresenter.loginSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            Log.d("Roshine", "onError: " + error);
                            mILoginPresenter.loginFail(error);
                        }

                        @Override
                        public void onServerError(String error) {
                            Log.d("Roshine", "onError: " + error);
                            mILoginPresenter.loginFail(error);
                        }
                    }).excute();
        }else {
            mILoginPresenter.loginFail(context.getResources().getString(R.string.load_failed));
        }
    }
}
