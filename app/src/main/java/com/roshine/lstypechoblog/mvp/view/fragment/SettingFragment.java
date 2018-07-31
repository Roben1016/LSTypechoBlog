package com.roshine.lstypechoblog.mvp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.callback.AutoDialogCallback;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.mvp.view.activity.LoginActivity;
import com.roshine.lstypechoblog.mvp.view.activity.ThemeColorActivity;
import com.roshine.lstypechoblog.mvp.view.fragment.base.BaseFragment;
import com.roshine.lstypechoblog.utils.ActivityUtil;
import com.roshine.lstypechoblog.utils.DialogUtil;
import com.roshine.lstypechoblog.utils.SPUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/7/31 13:26
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 设置界面
 */
public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.rl_setting_themes)
    RelativeLayout rlSettingThemes;
    @BindView(R.id.rl_setting_logout)
    RelativeLayout rlSettingLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        tvTitle.setText(getActivity().getString(R.string.setting_text));
        tbBaseToolBar.setBackgroundColor(getActivity().getResources().getColor(
                ThemeColorUtil.getThemeColor()));
    }

    @OnClick(R.id.rl_setting_themes)
    void textClick() {
        startActivity(ThemeColorActivity.class);
    }

    @OnClick(R.id.rl_setting_logout)
    void logoutClick(){
        DialogUtil.getInstance().showNormalDialog(getActivity(),
                false, getActivity().getResources().getString(R.string.kindly_reminder),
                getActivity().getResources().getString(R.string.ask_logout_text),
                getActivity().getResources().getString(R.string.sure_text),
                getActivity().getResources().getString(R.string.cancel_text),
                0, new AutoDialogCallback() {
                    @Override
                    public void onSureClick(DialogInterface dialogInterface, int i, int dialogCode) {
                        SPUtil.setParam(Constants.SharedPreferancesKeys.BLOG_URL,"");
                        SPUtil.setParam(Constants.SharedPreferancesKeys.USER_NAME,"");
                        SPUtil.setParam(Constants.SharedPreferancesKeys.USER_PASSWORD,"");
                        startActivity(LoginActivity.class);
                        ActivityUtil.getInstance().finishAllActivity();
                    }

                    @Override
                    public void onCancelClick(DialogInterface dialogInterface, int i, int dialogCode) {

                    }
                });
    }


}
