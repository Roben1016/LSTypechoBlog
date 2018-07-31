package com.roshine.lstypechoblog.request;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.customs.WeakRefHandler;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.NetWorkUtil;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;
import de.timroes.axmlrpc.XMLRPCTimeoutException;

/**
 * @author Roshine
 * @date 2017/7/18 16:27
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class XmlRequest implements BaseRequest{
    private static final int SERVER_SUCCESS = 111;
//    private static final int XMLRPC_EXCEPTION = 112;
    private static final int SERVER_ERROR = 113;
    private String url;
    private String method;
    private int timeOut = Constants.NormalConstants.TIME_OUT;
    private List<Object> parameters;
    private RequestCallBack callBack;
    private WeakRefHandler handler;
    private XMLRPCClient client;
    private long id;
    private Context context;

    public XmlRequest(Context context){
        handler = new WeakRefHandler(mCallback, Looper.getMainLooper());
        this.context = context;
    }

    @Override
    public BaseRequest method(String methodName) {
        if (methodName != null) {
            this.method = methodName;
        }
        return this;
    }

    @Override
    public BaseRequest url(String url) {
        if (url != null) {
            this.url = url;
        }
        return this;
    }

    @Override
    public BaseRequest timeOut(int timeOut) {
        if(timeOut > 0){
            this.timeOut = timeOut;
        }
        return this;
    }

    @Override
    public BaseRequest addParameters(List<Object> parameters) {
        if (parameters != null) {
            this.parameters = parameters;
        }
        return this;
    }

    @Override
    public BaseRequest callback(RequestCallBack callBack) {
        if (callBack != null) {
            this.callBack = callBack;
        }
        return this;
    }

    @Override
    public BaseRequest excute() {
        if(NetWorkUtil.isConnect()){
            XMLRPCCallback listener = new XMLRPCCallback() {
                public void onResponse(long id, Object result) {
                    Gson gson = new Gson();
                    String json = gson.toJson(result);
                    Message msg = handler.obtainMessage();
                    msg.what = SERVER_SUCCESS;
                    msg.obj = json;
                    msg.sendToTarget();
                }
                public void onError(long id, XMLRPCException error) {
                    Message msg = handler.obtainMessage();
                    msg.what = SERVER_ERROR;
                    msg.obj = error.getMessage();
                    LogUtil.showI("Roshine","onError:"+error.getMessage());
                    if(error.getCause() instanceof FileNotFoundException){
                        msg.obj = context.getResources().getString(R.string.load_failed_url);
                    } else if(error.getCause() instanceof XMLRPCTimeoutException){
                        msg.obj = context.getResources().getString(R.string.time_out);
                    }else if(error.getCause() instanceof UnknownHostException){
                        msg.obj = context.getResources().getString(R.string.net_error);
                    }
                    msg.sendToTarget();
                }
                public void onServerError(long id, XMLRPCServerException error) {
                    LogUtil.showI("Roshine","onServerError:"+error.getMessage());
                    Message msg = handler.obtainMessage();
                    msg.what = SERVER_ERROR;
                    msg.obj = error.getMessage();
                    if(error.getCause() instanceof UnknownHostException){
                        msg.obj = context.getResources().getString(R.string.net_error);
                    }
                    msg.sendToTarget();
                }
            };
            try {
                URL urlRequest = new URL(url);
                client = new XMLRPCClient(urlRequest);
                client.setTimeout(timeOut);
                id = client.callAsync(listener, method, parameters.toArray());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                LogUtil.showI("Roshine","MalformedURLException:"+e.getMessage());
                Message msg = handler.obtainMessage();
                msg.what = SERVER_ERROR;
                msg.obj = context.getResources().getString(R.string.load_failed_url);
                msg.sendToTarget();
            }
        }else{
            Message msg = handler.obtainMessage();
            msg.what = SERVER_ERROR;
            msg.obj = context.getResources().getString(R.string.network_null);
            msg.sendToTarget();
        }
        return this;
    }

    @Override
    public void cancel() {
        if (client != null) {
            LogUtil.showI("Roshine","取消的id:"+id);
            client.cancel(id);
        }
    }

    private Handler.Callback mCallback = msg -> {
        switch(msg.what){
            case SERVER_SUCCESS:
                if (callBack != null && msg.obj!=null) {
                    callBack.onResponse((String)msg.obj);
                }
                break;
            case SERVER_ERROR:
                if (callBack != null && msg.obj != null) {
                    callBack.onError((String)msg.obj);
                }
                break;
        }
        return true;
    };
}
