package com.roshine.lstypechoblog.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.roshine.lstypechoblog.LsXmlRpcApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Description:
 * Created by guzhenfu on 16/1/12.
 */
public class NetWorkUtil {
    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean isConnect() {
        ConnectivityManager cm = (ConnectivityManager) LsXmlRpcApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable() {
        try {
            NetworkInfo localNetworkInfo = ((ConnectivityManager) LsXmlRpcApplication.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            boolean bool1 = false;
            if (localNetworkInfo != null) {
                boolean bool2 = localNetworkInfo.isAvailable();
                bool1 = false;
                if (bool2) {
                    boolean bool3 = localNetworkInfo.isConnected();
                    bool1 = false;
                    if (bool3) {
                        bool1 = true;
                    }
                }
            }
            return bool1;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) LsXmlRpcApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi() {
        ConnectivityManager cm = (ConnectivityManager) LsXmlRpcApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null || cm.getActiveNetworkInfo() == null)
            return false;
        else
            return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 判断是否是手机连接
     */
    public static boolean isMobile() {
        ConnectivityManager cm = (ConnectivityManager) LsXmlRpcApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null || cm.getActiveNetworkInfo() == null)
            return false;
        else
            return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;

    }


    /**
     * 获取手机ip
     *
     * @return
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
//		Intent intent = new Intent("/");
//		ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
//		intent.setComponent(cm);
//		intent.setAction("android.intent.action.VIEW");
//		activity.startActivityForResult(intent, 0);
        // 跳转到系统的网络设置界面
        Intent intent = null;
        // 先判断当前系统版本
        if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        }else{
            intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
        }
        activity.startActivity(intent);
    }

}