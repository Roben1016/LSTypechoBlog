package com.roshine.lstypechoblog.mvp.view.fragment.base;

import android.os.Bundle;


/**
 *
 * @date 2017/1/14 11:53
 * @desc ViewPager中Fragment的父类，用于进行懒加载和页面可见时统计(如果父类不是Activity,而是ViewPager中的一个Fragment,则不能使用)
 *
 */
public abstract class BasePageFragment extends BaseFragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            onVisibilityChangedToUser(true, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getUserVisibleHint()){
            onVisibilityChangedToUser(false, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }
    //查看这个fragment的可见状态
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isResumed()){
            onVisibilityChangedToUser(isVisibleToUser, true);
        }
    }


    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     * @param isVisibleToUser true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser, boolean isHappenedInSetUserVisibleHintMethod){
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
        if(isVisibleToUser){
            onPageStart();
        }else{
            onPageEnd();
        }
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadNetData();//页面可见并且ActivityOnCreate初始化后进行网络操作
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    //统计页面 开始--写相关页面统计逻辑 或其它在页面可见时的操作
    public abstract void onPageStart();

    //统计页面 结束--写相关页面统计逻辑 或其它在页面不可见时的操作
    public abstract void onPageEnd();

    //在这个方法中写网络请求
    public abstract void loadNetData();
}

