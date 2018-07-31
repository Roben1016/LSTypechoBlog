package com.roshine.lstypechoblog.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.constants.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Roshine
 * @date 2017/7/24 10:34
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc SharedPreferences工具类
 */
public class SPUtil {
	/**
	 * 保存在手机里面的文件名
	 */
	private static final String FILE_NAME = "share_date";
	
	
	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * @param key
	 * @param object 
	 */
	public static void setParam(String key, Object object){
		
		String type = object.getClass().getSimpleName();
		SharedPreferences sp = LsXmlRpcApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		
		if("String".equals(type)){
			editor.putString(key, (String)object);
		}
		else if("Integer".equals(type)){
			editor.putInt(key, (Integer)object);
		}
		else if("Boolean".equals(type)){
			editor.putBoolean(key, (Boolean)object);
		}
		else if("Float".equals(type)){
			editor.putFloat(key, (Float)object);
		}
		else if("Long".equals(type)){
			editor.putLong(key, (Long)object);
		}
		
		editor.commit();
	}
	
	
	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object getParam(String key, Object defaultObject) {
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = LsXmlRpcApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		
		if("String".equals(type)){
			return sp.getString(key, (String)defaultObject);
		}
		else if("Integer".equals(type)){
			return sp.getInt(key, (Integer)defaultObject);
		}
		else if("Boolean".equals(type)){
			return sp.getBoolean(key, (Boolean)defaultObject);
		}
		else if("Float".equals(type)){
			return sp.getFloat(key, (Float)defaultObject);
		}
		else if("Long".equals(type)){
			return sp.getLong(key, (Long)defaultObject);
		}
		
		return null;
	}

	/**
	 * writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
	 * 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
	 *
	 * @param object 待加密的转换为String的对象
	 * @return String   加密后的String
	 */
	private static String Object2String(Object object) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
			objectOutputStream.close();
			return string;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用Base64解密String，返回Object对象
	 *
	 * @param objectString 待解密的String
	 * @return object      解密后的object
	 */
	private static Object String2Object(String objectString) {
		byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 使用SharedPreference保存对象
	 *
	 * @param key        储存对象的key
	 * @param saveObject 储存的对象
	 */
	public static void setObjectBean(String key, Object saveObject) {
		SharedPreferences sharedPreferences = LsXmlRpcApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
//		SharedPreferences sharedPreferences = MeiYin.getInstance().getApplication().getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
//		SharedPreferences.Editor editor = sharedPreferences.edit();
		String string = Object2String(saveObject);
		editor.putString(key, string);
		editor.commit();
	}

	/**
	 * 获取SharedPreference保存的对象
	 *
	 * @param key     储存对象的key
	 * @return object 返回根据key得到的对象
	 */
	public static Object getObjectBean(String key) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = sharedPreferences.edit();

		SharedPreferences sharedPreferences = LsXmlRpcApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		String string = sharedPreferences.getString(key, null);
		if (string != null) {
			Object object = String2Object(string);
			return object;
		} else {
			return null;
		}
	}

	public static boolean checkMethodAndUrl(String method){
		if(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_TOTAL_METHOD, "") != null
				&& SPUtil.getParam(Constants.SharedPreferancesKeys.USER_TOTAL_METHOD, "").toString().contains(method)
				&& SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "") != null){
		    return true;
		}
		return false;
	}

	public static boolean checkLogined(){
		if(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "") != null
				&& !SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL,"").toString().equals("")
				&& SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, "") != null
				&& !SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, "").toString().equals("")
				&& SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, "") != null
				&& !SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, "").toString().equals("")){
		    return true;
		}
		return false;
	}
}
