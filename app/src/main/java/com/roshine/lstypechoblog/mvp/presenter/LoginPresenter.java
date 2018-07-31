package com.roshine.lstypechoblog.mvp.presenter;

import android.content.Context;
import android.os.Looper;

import com.google.gson.Gson;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.RxSubUtil;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.model.LoginModel;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.SPUtil;

import io.reactivex.Flowable;

/**
 * @author Roshine
 * @date 2017/7/20 23:52
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LoginPresenter extends IBasePresenter<ContractUtil.ILoginView> implements ContractUtil.ILoginPresenter {
    private LoginModel mLoginModel;

    public LoginPresenter() {
        mLoginModel = new LoginModel(this);
    }

    @Override
    public void getAllMethodAndAuthenty(Context context) {
        String url = getView().getUrl();
        String userName = getView().getUserName();
        String password = getView().getUserPasswd();
        if (url == null || url.equals("")) {
            loginFail(context.getResources().getString(R.string.null_url));
        } else if (userName == null || userName.equals("")) {
            loginFail(context.getResources().getString(R.string.null_user_name));
        } else if (password == null || password.equals("")) {
            loginFail(context.getResources().getString(R.string.null_user_pwd));
        } else {
            Flowable<String> flowable = mLoginModel.getAllMethod(view.getUrl(), view.getUserName(), view.getUserPasswd());
            if (flowable != null) {
                compositeDisposable.add(flowable.subscribeWith(new RxSubUtil<String>(compositeDisposable,context) {
                    @Override
                    protected void onSuccess(String s) {
                        SPUtil.setParam(Constants.SharedPreferancesKeys.USER_TOTAL_METHOD, s);
                        SPUtil.setParam(Constants.SharedPreferancesKeys.BLOG_URL, url);
                        Flowable<String> flowable1 = mLoginModel.getAuthenty(getView().getUserName(), getView().getUserPasswd());
                        if (flowable1 != null) {
                            //要先清除再添加
                            compositeDisposable.clear();
                            compositeDisposable.add(flowable1.subscribeWith(new RxSubUtil<String>(compositeDisposable,context) {
                                @Override
                                protected void onSuccess(String s) {
                                    SPUtil.setParam(Constants.SharedPreferancesKeys.USER_NAME, userName);
                                    SPUtil.setParam(Constants.SharedPreferancesKeys.USER_PASSWORD, password);
                                    loginSuccess();
                                }

                                @Override
                                protected void onFail(String errorMsg) {
                                    loginFail(errorMsg);
                                }
                            }));
                        } else {
                            loginFail(context.getResources().getString(R.string.load_failed));
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        loginFail(errorMsg);
                    }
                }));
            } else {
                loginFail(context.getResources().getString(R.string.load_failed));
            }
        }
    }

    @Override
    public void loginSuccess() {
        getView().loadSuccess(null);
    }

    @Override
    public void loginFail(String message) {
        getView().loadFail(message);
    }
}
