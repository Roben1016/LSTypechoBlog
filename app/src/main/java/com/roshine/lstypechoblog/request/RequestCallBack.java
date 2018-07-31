package com.roshine.lstypechoblog.request;

import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

/**
 * @author Roshine
 * @date 2017/7/18 17:05
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface RequestCallBack {
    /**
     * This callback is called whenever the server successfully responds.
     *
     * @ param The id as returned by the XMLRPCClient.asyncCall(..) method for this request.
     * @param result The Object returned from the server.
     */
    void onResponse(String result);

    /**
     * This callback is called whenever an error occurs during the method call.
     *
     * @ param id The id as returned by the XMLRPCClient.asyncCall(..) method for this request.
     * @param error The error occured.
     */
    void onError(String error);

    /**
     * This callback is called whenever the server returns an error.
     *
     * @ param id The id as returned by the XMLRPCClient.asyncCall(..) method for this request.
     * @param error The error returned from the server.
     */
//    void onServerError(String error);

}
