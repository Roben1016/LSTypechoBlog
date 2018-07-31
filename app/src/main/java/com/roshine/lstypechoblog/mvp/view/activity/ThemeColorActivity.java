package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.callback.AutoDialogCallback;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.mvp.bean.ThemeColorBean;
import com.roshine.lstypechoblog.mvp.view.activity.base.BaseToolBarActivity;
import com.roshine.lstypechoblog.mvp.view.adapter.ThemeColorChooseAdapter;
import com.roshine.lstypechoblog.utils.ActivityUtil;
import com.roshine.lstypechoblog.utils.DialogUtil;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.StatusBarUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * @author Roshine
 * @date 2017/7/31 13:45
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 设置主题颜色界面
 */
public class ThemeColorActivity extends BaseToolBarActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.gv_theme_color)
    GridView gvThemeColor;
    @BindView(R.id.btn_sure)
    Button btnSure;

    private ThemeColorChooseAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_themecolor;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        btnSure.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        initGridView();
        setTitleText();
    }

    private void setTitleText() {
        int lastThemeColor = ThemeColorUtil.getThemeColor();
        for (int i = 0; i < Constants.ThemeColors.THEME_COLOR_LIST.length; i++) {
            if (lastThemeColor == Constants.ThemeColors.THEME_COLOR_LIST[i].getColorValue()) {
                tvTitle.setText(getResources().getString(Constants.ThemeColors.THEME_COLOR_LIST[i].getColorName()));
                gvThemeColor.setItemChecked(i,true);
                break;
            }
        }
    }

    private void initGridView() {
        mAdapter = new ThemeColorChooseAdapter(this, Constants.ThemeColors.THEME_COLOR_LIST);
        gvThemeColor.setAdapter(mAdapter);
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @OnClick(R.id.btn_sure)
    void sureClick() {
        int position = gvThemeColor.getCheckedItemPosition();
        int color = getResources().getColor(Constants.ThemeColors.THEME_COLOR_LIST[position].getColorValue());
        DialogUtil.getInstance().showNormalDialog(this,
                getResources().getString(R.string.kindly_reminder),
                Html.fromHtml(String.format(getResources().getString(R.string.choose_theme_color_text), "<b><font color=\"" + Color.parseColor("#"+Integer.toHexString(color)) + "\">" + getResources().getString(Constants.ThemeColors.THEME_COLOR_LIST[position].getColorName()) + "</font></b>")),
                getResources().getString(R.string.sure_text),
                getResources().getString(R.string.cancel_text),
                0, new AutoDialogCallback() {
                    @Override
                    public void onSureClick(DialogInterface dialogInterface, int i, int dialogCode) {
                        ThemeColorUtil.setThemeColorPosition(position);
                        ActivityUtil.getInstance().refreshAllActivityTheme();
                        finish();
                    }

                    @Override
                    public void onCancelClick(DialogInterface dialogInterface, int i, int dialogCode) {

                    }
                });
    }
    @OnItemClick(R.id.gv_theme_color)
    void listItemClick(int position){
        if(position < Constants.ThemeColors.THEME_COLOR_LIST.length){
            ThemeColorBean themeColorBean = Constants.ThemeColors.THEME_COLOR_LIST[position];
            btnSure.setBackgroundColor(getResources().getColor(themeColorBean.getColorValue()));
            tvTitle.setText(getResources().getString(themeColorBean.getColorName()));
            tbBaseToolBar.setBackgroundColor(getResources().getColor(themeColorBean.getColorValue()));
            StatusBarUtil.setColorBar(this, getResources().getColor(themeColorBean.getColorValue()));
        }
    }
}
