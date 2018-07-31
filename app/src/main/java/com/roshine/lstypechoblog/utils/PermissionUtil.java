package com.roshine.lstypechoblog.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.roshine.lspermission.activity.LSPermission;
import com.roshine.lspermission.interfaces.OnPermissionListener;
import com.roshine.lspermission.interfaces.Rationale;
import com.roshine.lspermission.interfaces.ShowRationableListener;
import com.roshine.lstypechoblog.callback.PermissionCallBack;

public class PermissionUtil {

	public static boolean checkPermissions(Context context, String[] permissions) {
		return LSPermission.checkPermissions(context,permissions);
	}

	public static void requestPermissions(Context context, String[] permissions, int requestCode, final PermissionCallBack permissionCallBack){
		LSPermission.with(context)
				.requestCode(requestCode)
				.permissions(permissions)
				.onRationable(new ShowRationableListener() {
					@Override
					public void showRationablDialog(int requestCode, Rationale rationale) {
						if (permissionCallBack != null) {
							permissionCallBack.onRationShow(requestCode,rationale);
						}
					}
				})
				.callBack(new OnPermissionListener() {
					@Override
					public void onPermissionRequestSuc(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestSuccess(requestCode,permissions);
						}
					}

					@Override
					public void onPermissionRequestFail(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestFail(requestCode,permissions);
						}
					}
				})
				.start();
	}
	public static void requestPermissions(Activity activity, String[] permissions, int requestCode, final PermissionCallBack permissionCallBack){
		LSPermission.with(activity)
				.requestCode(requestCode)
				.permissions(permissions)
				.onRationable(new ShowRationableListener() {
					@Override
					public void showRationablDialog(int requestCode, Rationale rationale) {
						if (permissionCallBack != null) {
							permissionCallBack.onRationShow(requestCode,rationale);
						}
					}
				})
				.callBack(new OnPermissionListener() {
					@Override
					public void onPermissionRequestSuc(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestSuccess(requestCode,permissions);
						}
					}

					@Override
					public void onPermissionRequestFail(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestFail(requestCode,permissions);
						}
					}
				})
				.start();
	}
	public static void requestPermissions(android.support.v4.app.Fragment fragment, String[] permissions, int requestCode, final PermissionCallBack permissionCallBack){
		LSPermission.with(fragment)
				.requestCode(requestCode)
				.permissions(permissions)
				.onRationable(new ShowRationableListener() {
					@Override
					public void showRationablDialog(int requestCode, Rationale rationale) {
						if (permissionCallBack != null) {
							permissionCallBack.onRationShow(requestCode,rationale);
						}
					}
				})
				.callBack(new OnPermissionListener() {
					@Override
					public void onPermissionRequestSuc(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestSuccess(requestCode,permissions);
						}
					}

					@Override
					public void onPermissionRequestFail(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestFail(requestCode,permissions);
						}
					}
				})
				.start();
	}
	public static void requestPermissions(android.app.Fragment fragment, String[] permissions, int requestCode, final PermissionCallBack permissionCallBack){
		LSPermission.with(fragment)
				.requestCode(requestCode)
				.permissions(permissions)
				.onRationable(new ShowRationableListener() {
					@Override
					public void showRationablDialog(int requestCode, Rationale rationale) {
						if (permissionCallBack != null) {
							permissionCallBack.onRationShow(requestCode,rationale);
						}
					}
				})
				.callBack(new OnPermissionListener() {
					@Override
					public void onPermissionRequestSuc(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestSuccess(requestCode,permissions);
						}
					}

					@Override
					public void onPermissionRequestFail(int requestCode, String[] permissions) {
						if (permissionCallBack != null) {
							permissionCallBack.onRequestFail(requestCode,permissions);
						}
					}
				})
				.start();
	}

	public static boolean checkSdkVersion(int targetSdkVersion){
		if(Build.VERSION.SDK_INT >= targetSdkVersion){
		    return true;
		}
		return false;
	}

	public static void goSettingActivityForResult(Activity activity,int activityRequestCode){
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", activity.getPackageName(), (String) null);
		intent.setData(uri);
		activity.startActivityForResult(intent, activityRequestCode > 0 ? activityRequestCode : -1);
	}
	public static void goSettingActivity(Context context){
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", context.getPackageName(), (String) null);
		intent.setData(uri);
		context.startActivity(intent);
	}

}
