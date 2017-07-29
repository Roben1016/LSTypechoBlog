package com.roshine.lstypechoblog.mvp.contract;

/**
 * @author Roshine
 * @date 2017/7/20 23:00
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface BaseView {
    void showProgress();
    void hideProgress();
    void toast(String message);
    void toastLong(String message);
    void toastWithTime(int time,String message);

//    void loadSuccess(String result);
//    void loadServerError(String message);
//    void loadError(String error);
}
