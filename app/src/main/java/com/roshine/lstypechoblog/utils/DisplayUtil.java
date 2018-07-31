package com.roshine.lstypechoblog.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param pxValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dp(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param dipValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dp2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static float dp2pxF(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return dipValue * scale;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param context
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param context
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取屏幕宽度，单位px
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}
	/**
	 * 获取屏幕高度，单位px
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}
}
