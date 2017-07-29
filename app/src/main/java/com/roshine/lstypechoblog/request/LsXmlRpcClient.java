package com.roshine.lstypechoblog.request;

import android.content.Context;

/**
 * @author Roshine
 * @date 2017/7/18 16:26
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LsXmlRpcClient {

    public static XmlRequest request(Context context){
        return new XmlRequest(context);
    }
}
