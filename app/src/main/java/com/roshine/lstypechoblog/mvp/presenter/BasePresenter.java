package com.roshine.lstypechoblog.mvp.presenter;

/**
 * @author Roshine
 * @date 2017/7/20 22:28
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc presenter基类
 */
public abstract class BasePresenter<V> {
    V view;
    public void onAttachView(V view){
        this.view = view;
    };

    public void onDestroyView(){
        if (view != null) {
            view = null;
        }
    };
}
