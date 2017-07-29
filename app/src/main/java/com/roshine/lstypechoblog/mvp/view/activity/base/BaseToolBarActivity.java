package com.roshine.lstypechoblog.mvp.view.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.utils.SPUtil;
import com.roshine.lstypechoblog.utils.StatusBarUtil;

/**
 * @author Roshine
 * @date 2017/7/29 16:28
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 带toolbar的activity,设置沉浸式状态栏
 */
public class BaseToolBarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorBar(this, (SPUtil.getParam(this,"appThemeColor",0) == null
                || (int)SPUtil.getParam(this,"appThemeColor",0) == 0)
                ?getResources().getColor(R.color.colorPrimary)
                :getResources().getColor((int)SPUtil.getParam(this,"appThemeColor",0)));
    }
}
