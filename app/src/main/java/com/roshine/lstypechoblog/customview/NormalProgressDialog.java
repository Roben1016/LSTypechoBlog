package com.roshine.lstypechoblog.customview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;


import com.roshine.lstypechoblog.utils.LogUtil;

import java.lang.ref.WeakReference;

 /**
  *
  * @author Roshine
  * @date 2017/7/17 23:34
  * @desc 封装全局ProgressDialog
  *
  */
public class NormalProgressDialog extends ProgressDialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContextRef = new WeakReference<>(null);
    private volatile static NormalProgressDialog sDialog;

    private NormalProgressDialog(Context context) {
        this(context, -1);
    }

    private NormalProgressDialog(Context context, int theme) {
        super(context, theme);

        mContextRef = new WeakReference<>(context);
        setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
//        Context context = mContextRef.get();
//        if (context != null) {
//            Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
//            MyHttpClient.cancelRequests(context);
//        }
    }

    public static synchronized void showLoading(Context context) {
        showLoading(context, "loading...");
    }

    public static synchronized void showLoading(Context context, CharSequence message) {
        showLoading(context, message, true);
    }

    public static synchronized void showLoading(Context context, CharSequence message, boolean cancelable) {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }

        if (context == null || !(context instanceof Activity)) {
            return;
        }
        sDialog = new NormalProgressDialog(context);
        sDialog.setMessage(message);
        sDialog.setCancelable(cancelable);

        if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
            sDialog.show();
        }
    }

    public static synchronized void stopLoading() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }
}
