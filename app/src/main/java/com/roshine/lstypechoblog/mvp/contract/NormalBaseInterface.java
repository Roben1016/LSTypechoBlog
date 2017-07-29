package com.roshine.lstypechoblog.mvp.contract;

/**
 * @author Roshine
 * @date 2017/7/21 0:13
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface NormalBaseInterface extends BaseView {
    void loginSuccess();
    void loginFail(String message);
    void getAllMethodAndAuthenty(String url,String userName,String password);

}
