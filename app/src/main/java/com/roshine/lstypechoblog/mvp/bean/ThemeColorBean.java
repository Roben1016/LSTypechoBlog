package com.roshine.lstypechoblog.mvp.bean;

import android.graphics.drawable.Drawable;

/**
 * @author Roshine
 * @date 2017/7/31 14:09
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 主题颜色bean
 */
public class ThemeColorBean {

    private int colorName;
    private int colorValue;

    private int colorDrawable;

    public ThemeColorBean(int colorName, int colorValue, int drawable){
        this.colorName = colorName;
        this.colorValue = colorValue;
        this.colorDrawable = drawable;
    }

    public int getColorName() {
        return colorName;
    }

    public void setColorName(int colorName) {
        this.colorName = colorName;
    }

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }
    public int getColorDrawable() {
        return colorDrawable;
    }

    public void setColorDrawable(int colorDrawable) {
        this.colorDrawable = colorDrawable;
    }
}
