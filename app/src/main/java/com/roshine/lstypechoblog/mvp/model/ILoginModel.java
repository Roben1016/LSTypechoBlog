package com.roshine.lstypechoblog.mvp.model;

import android.content.Context;

/**
 * @author Roshine
 * @date 2017/7/21 0:05
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface ILoginModel {
    void getAllMethod(Context context, String url, String userName, String password);
    void getAuthenty(Context context, String userName,String password);
}
