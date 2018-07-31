package com.roshine.lstypechoblog.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Roshine
 * @date 2017/8/1 13:33
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 日期转换工具类
 */
public class DateUtil {

    public static String formatDate(String dateStr){
        String returnDate = dateStr;
        try {
            Date dates = new Date(dateStr);
            DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
//            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            returnDate = dateInstance.format(dates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }
}
