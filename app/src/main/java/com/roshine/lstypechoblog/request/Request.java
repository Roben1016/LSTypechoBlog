package com.roshine.lstypechoblog.request;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/7/18 17:00
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface Request <T extends Request>{

    T excute();
    T callback(RequestCallBack callBack);
    T timeOut(int timeOut);
    T addParameters(List<Object> parameters);
    T method(String methodName);
    T url(String url);
}
