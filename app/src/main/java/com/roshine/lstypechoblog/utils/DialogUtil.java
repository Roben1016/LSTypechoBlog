package com.roshine.lstypechoblog.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.callback.AutoDialogCallback;


/**
 * @author Roshine
 * @date 2017/7/18 16:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc suppertv7中AlertDialog工具类
 */
public class DialogUtil {
    private static DialogUtil instance = null;
    public static DialogUtil getInstance(){
        if(instance == null){
            synchronized (DialogUtil.class){
                if(instance == null){
                    instance = new DialogUtil();
                }
            }
        }
        return instance;
    }
    private DialogUtil(){}

    public void showNormalDialog(Context context, String title, CharSequence message, String btnSureText, String btnCancelText,
                                 final int dialogCode, final AutoDialogCallback callback){
        showNormalDialog(context,true,title,message,btnSureText,btnCancelText,dialogCode,callback);
    }
    public void showNormalDialog(Context context,boolean cancelable, String title, CharSequence message, String btnSureText, String btnCancelText,
                                 final int dialogCode, final AutoDialogCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogThemeV7);
        builder.setTitle(title == null?"":title)
                .setMessage(message == null?"":message)
                .setCancelable(cancelable);
        if(btnSureText != null && !btnSureText.equals("")){
            builder.setPositiveButton(btnSureText, (dialog, i) -> {
                if (callback != null) {
                    callback.onSureClick(dialog,i,dialogCode);
                }
            });
        }
        if(btnCancelText != null && !btnCancelText.equals("")){
            builder.setNegativeButton(btnCancelText, (dialog, i) -> {
                if (callback != null) {
                    callback.onCancelClick(dialog,i,dialogCode);
                }
            });
        }
        builder.show();
    }
}
