package com.roshine.lstypechoblog.http;

/**
 * @author Roshine
 * @date 2017/8/19 15:27
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class RxUrlException extends RxException {

    public RxUrlException() {
        super();
    }

    public RxUrlException(Exception ex) {
        super(ex);
    }

    public RxUrlException(String ex) {
        super(ex);
    }

    public RxUrlException(String msg, Exception ex) {
        super(msg, ex);
    }
}
