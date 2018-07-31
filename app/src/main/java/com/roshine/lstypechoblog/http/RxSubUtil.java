package com.roshine.lstypechoblog.http;

import android.content.Context;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.utils.NetWorkUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author Roshine
 * @date 2017/8/17 21:40
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public abstract class RxSubUtil<T> extends DisposableSubscriber<T> {
    private CompositeDisposable compositeDisposable;
    private Context mContext;
    private String msg;

    public RxSubUtil(CompositeDisposable mCompositeSubscription) {
        this.compositeDisposable = mCompositeSubscription;
    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxSubUtil(CompositeDisposable mCompositeSubscription, Context context, String msg) {
        this.compositeDisposable = mCompositeSubscription;
        this.mContext = context;
        this.msg = msg;
    }

    /**
     * @param context context
     */
    public RxSubUtil(CompositeDisposable mCompositeSubscription, Context context) {
        this(mCompositeSubscription, context, context.getResources().getString(R.string.wait_text));
    }

    /**
     * 这个一定要有 Presenter的逻辑在这里处理
     * @param t
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetWorkUtil.isNetworkAvailable()) {
            onFail(mContext.getResources().getString(R.string.network_null));
        } else if (e instanceof RxException) {
            String code = e.getMessage();
            if(code.startsWith("5")){
                onFail(String.format(mContext.getResources().getString(R.string.servie_error_with_code),e.getMessage()));
            }else{
                onFail(String.format(mContext.getResources().getString(R.string.load_error_with_code),e.getMessage()));
            }
        }else if(e instanceof FileNotFoundException){
            onFail(mContext.getResources().getString(R.string.load_failed_url));
        } else if(e instanceof UnknownHostException){
            onFail(mContext.getResources().getString(R.string.net_error));
        } else if(e instanceof SocketTimeoutException){
            onFail(mContext.getResources().getString(R.string.time_out));
        } else if(e instanceof IOException){
            onFail(mContext.getResources().getString(R.string.data_failed));
        } else if(e instanceof XMLRPCServerException){
            onFail(e.getMessage());
        }else if(e instanceof XMLRPCException){
            onFail(mContext.getResources().getString(R.string.data_failed));
        }else {
//            ToastUtil.show(R.string.error);
            onFail(mContext.getResources().getString(R.string.service_error));
        }
    }

    @Override
    public void onComplete() {
        if (compositeDisposable != null)
            compositeDisposable.clear();

//        LoadingDialogManager.getLoadingDialog().hideDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (mContext != null) {
//            LoadingDialogManager.getLoadingDialog().showDialog(mContext);
//        }
    }

    protected abstract void onSuccess(T t);

    /**
     * 错误处理，需要的话重写这个方法
     */
    protected abstract void onFail(String errorMsg);
}
