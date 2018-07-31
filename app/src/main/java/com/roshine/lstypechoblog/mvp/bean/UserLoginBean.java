package com.roshine.lstypechoblog.mvp.bean;

/**
 * @author Roshine
 * @date 2017/7/30 23:49
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 登录时的数据bean
 */
public class UserLoginBean {

    private String url;
    private String userName;
    private String userPasswd;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

}
