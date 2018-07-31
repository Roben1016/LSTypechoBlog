package com.roshine.lstypechoblog.mvp.view.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.utils.DisplayUtil;
import com.roshine.lstypechoblog.utils.SPUtil;
import com.roshine.lstypechoblog.utils.StatusBarUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

/**
 * @author Roshine
 * @date 2017/7/29 16:28
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 带toolbar的activity,设置沉浸式状态栏
 */
public abstract class BaseToolBarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorBar(this, getResources().getColor(ThemeColorUtil.getThemeColor()));
    }

//    public void initAppBarLayout(AppBarLayout appBar) {
//        if (appBar == null) return;
//        if (Build.VERSION.SDK_INT >= 21) {
//            appBar.setElevation(DisplayUtil.dp2px(this,3));
//        }
//    }

//    @Override
//    protected void initViewData(Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return 0;
//    }
}
