package com.roshine.lstypechoblog.http;

/**
 * @author Roshine
 * @date 2017/8/17 20:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class RxException extends Exception{

    public RxException() {
        super();
    }

    public RxException(Exception ex) {
        super(ex);
    }

    public RxException(String ex) {
        super(ex);
    }

    public RxException(String msg, Exception ex) {
        super(msg, ex);
    }
}
