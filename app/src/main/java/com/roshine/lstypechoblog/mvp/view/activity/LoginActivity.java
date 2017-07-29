package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.LoginPresenter;
import com.roshine.lstypechoblog.mvp.view.activity.base.MvpBaseActivity;

/**
 * @author Roshine
 * @date 2017/7/16 22:38
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 登录界面
 */
public class LoginActivity extends MvpBaseActivity<ContractUtil.ILoginView,LoginPresenter> implements ContractUtil.ILoginView {
    private Button btnLogin;
    private EditText etUrl,etUserName,etUserPwd;
    private LoginPresenter mPresenter;
    private TextView tvTitle;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        etUrl = (EditText) findViewById(R.id.et_url);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etUserPwd = (EditText) findViewById(R.id.et_user_pwd);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        toolbar = (Toolbar) findViewById(R.id.tb_base_tool_bar);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tvTitle.setText(getResources().getString(R.string.login_text));
        btnLogin.setOnClickListener(v -> {
            if(TextUtils.isEmpty(etUrl.getText().toString())){
                toast(LoginActivity.this.getString(R.string.null_url));
                return;
            } else if(TextUtils.isEmpty(etUserName.getText().toString())){
                toast(LoginActivity.this.getString(R.string.null_user_name));
                return;
            } else if(TextUtils.isEmpty(etUserPwd.getText().toString())){
                toast(LoginActivity.this.getString(R.string.null_user_pwd));
                return;
            }
            initData();
        });
    }

    private void initData() {
        showProgress();
        mPresenter.getAllMethodAndAuthenty(this,etUrl.getText().toString(),
                etUserName.getText().toString(),
                etUserPwd.getText().toString());
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        hideProgress();
        toast(this.getString(R.string.login_success));
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void loginFail(String message) {
        hideProgress();
        toast(message);
    }
}
