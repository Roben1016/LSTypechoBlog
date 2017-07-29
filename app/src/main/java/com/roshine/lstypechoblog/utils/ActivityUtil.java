package com.roshine.lstypechoblog.utils;


import android.app.Activity;

import java.util.Stack;

/**
 * @author Roshine
 * @date 2017/7/24 10:34
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc activity管理工具类
 */
public class ActivityUtil {

    private static ActivityUtil mInstance;
    private static Stack<Activity> mActivityStack;//activity栈

    //单例,双重加锁
    public static ActivityUtil getInstance(){
        if(mInstance == null){
            synchronized (ActivityUtil.class){
                if(mInstance == null){
                    mInstance = new ActivityUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity到activity栈中
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    public void refreshAllActivityTheme(){
        if (mActivityStack != null) {
            for (int i = 0; i < mActivityStack.size(); i++) {
                mActivityStack.get(i).recreate();
            }
        }
    }

    public  void removeActivity(Activity activity) {
        if (mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取当前最上层Activity
     */
    public Activity getCurrentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }


    /**
     * 关闭指定Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 关闭当前最后一个压入的activity
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 关闭指定Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            mActivityStack.remove(activity);//清除
            activity.finish();
        }
    }


    /**
     * 循环关闭activity退出
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                finishActivity(mActivityStack.get(i));
                break;
            }
        }
        mActivityStack.clear();//清空数据
    }

    /**
     * 获取指定的Activity
     *
     */
    public static Activity getActivity(Class<?> cls) {
        if (mActivityStack != null)
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
