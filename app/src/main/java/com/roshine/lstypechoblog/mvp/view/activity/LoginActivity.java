package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.LoginPresenter;
import com.roshine.lstypechoblog.mvp.view.activity.base.MvpBaseActivity;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.SPUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/7/16 22:38
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 登录界面
 */
public class LoginActivity extends MvpBaseActivity<ContractUtil.ILoginView, LoginPresenter> implements ContractUtil.ILoginView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar toolbar;
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private LoginPresenter mPresenter;

    private boolean hasSaveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SPUtil.checkLogined()){
            hasSaveData = true;
            initData();
        }else{
            hasSaveData = false;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        tvTitle.setText(getResources().getString(R.string.login_text));
        btnLogin.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        toolbar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
    }
    @OnClick(R.id.btn_login)
    void textClick() {
        initData();
    }

    private void initData() {
        showProgress(this.getString(R.string.loading_text),false);
        mPresenter.getAllMethodAndAuthenty(this);
    }

    @Override
    public LoginPresenter getPresenter() {
        mPresenter = new LoginPresenter();
        return mPresenter;
    }

    @Nullable
    @Override
    public void loadSuccess(Object datas) {
        hideProgress();
        toast(this.getString(R.string.login_success));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loadFail(String message) {
        hideProgress();
        toast(message);
    }

    @Override
    public String getUrl() {
        if(SPUtil.checkLogined()){
            return SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL,"").toString();
        }
        return etUrl.getText().toString();
    }

    @Override
    public String getUserName() {
        if(hasSaveData){
            return SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME,"").toString();
        }
        return etUserName.getText().toString();
    }

    @Override
    public String getUserPasswd() {
        if(hasSaveData){
            return SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD,"").toString();
        }
        return etUserPwd.getText().toString();
    }
}
