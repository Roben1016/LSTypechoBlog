package com.roshine.lstypechoblog.constants;

import android.Manifest;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.bean.ThemeColorBean;

/**
 * @author Roshine
 * @date 2017/7/18 16:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 常量类
 */
public class Constants {

    public static class NormalConstants{
        public static final int TIME_OUT = 30;//请求超时时间
        public static final String CONTENT_TYPE = "text/xml";
    }

    public static class SharedPreferancesKeys{
        public static final String APP_THEME_COLOR = "appThemeColor";
        public static final String APP_THEME_COLOR_POSITION = "appThemeColorPosition";
        public static final String BLOG_URL = "blogUrl";
        public static final String USER_TOTAL_METHOD = "userTotalMethod";
        public static final String USER_NAME = "userName";
        public static final String USER_PASSWORD = "userPassword";
    }

    public static class ThemeColors{
        public static final ThemeColorBean[] THEME_COLOR_LIST = {
                new ThemeColorBean(R.string.theme_blue_text, R.color.colorPrimary,R.drawable.pb_theme_colorprimary),
                new ThemeColorBean(R.string.theme_green_text, R.color.theme_green,R.drawable.pb_theme_green),
                new ThemeColorBean(R.string.theme_purple_text, R.color.theme_purple,R.drawable.pb_theme_purple),
                new ThemeColorBean(R.string.theme_yellow_text, R.color.theme_yellow,R.drawable.pb_theme_yellow),
                new ThemeColorBean(R.string.theme_red_text, R.color.red,R.drawable.pb_theme_red),
                new ThemeColorBean(R.string.theme_pink_text, R.color.theme_pink,R.drawable.pb_theme_pink),
                new ThemeColorBean(R.string.theme_brown_text, R.color.theme_brown,R.drawable.pb_theme_brown),
                new ThemeColorBean(R.string.theme_black_text, R.color.black,R.drawable.pb_theme_black),
                new ThemeColorBean(R.string.theme_orange_text, R.color.theme_orange,R.drawable.pb_theme_blue_light),
                new ThemeColorBean(R.string.theme_blue_light_text, R.color.theme_blue_light,R.drawable.pb_theme_blue_light),
                new ThemeColorBean(R.string.theme_gray_text, R.color.theme_gray,R.drawable.pb_theme_gray),
                new ThemeColorBean(R.string.theme_green_light_text, R.color.theme_green_light,R.drawable.pb_theme_green_light)
        };
    }

    public static class MethodNames{
        public static final String METHOD_LISTS = "system.listMethods";
        public static final String METHOD_GET_USER_BLOG = "wp.getUsersBlogs";
        public static final String METHOD_GET_MY_ARTICLES = "metaWeblog.getRecentPosts";//获取近期指定数量的博客列表
        public static final String METHOD_GET_MY_MEDIALS = "wp.getMediaLibrary";//获取博客中的媒体文件
        public static final String METHOD_GET_ARTICLE_DETAIL = "metaWeblog.getPost";//获取博客详情
        public static final String METHOD_EDIT_BLOG = "metaWeblog.editPost";//编辑博客
        public static final String METHOD_CREATE_BLOG = "metaWeblog.newPost";//创建博客
    }

    public static class PermissionNames{
        public static final String PERMISSION_READ_SD = Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String PERMISSION_WRITE_SD = Manifest.permission.WRITE_APN_SETTINGS;
    }
}
