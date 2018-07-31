package com.roshine.lstypechoblog.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;

/**
 * @author Roshine
 * @date 2017/7/30 16:34
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 主题色设置获取工具类
 */
public class ThemeColorUtil {
    public static void setThemeColor(int colorSource) {
        SPUtil.setParam(Constants.SharedPreferancesKeys.APP_THEME_COLOR, colorSource);
    }

    public static void setThemeColor(String colorSource) {
        SPUtil.setParam(Constants.SharedPreferancesKeys.APP_THEME_COLOR, Color.parseColor(colorSource));
    }


    public static int getThemeColor() {
        return Constants.ThemeColors.THEME_COLOR_LIST[(int) SPUtil.getParam(Constants.SharedPreferancesKeys.APP_THEME_COLOR_POSITION, 0)].getColorValue();
    }
    public static void setThemeColorPosition(int position){
        SPUtil.setParam(Constants.SharedPreferancesKeys.APP_THEME_COLOR_POSITION,position);
    }

    public static int getThemeColorPosition(){
        return (int)SPUtil.getParam(Constants.SharedPreferancesKeys.APP_THEME_COLOR_POSITION,0);
    }

    public static int getThemeDrawable(){
        return Constants.ThemeColors.THEME_COLOR_LIST[(int) SPUtil.getParam(Constants.SharedPreferancesKeys.APP_THEME_COLOR_POSITION, 0)].getColorDrawable();
    }
}
