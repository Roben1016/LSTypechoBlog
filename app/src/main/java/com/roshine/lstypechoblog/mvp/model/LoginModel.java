package com.roshine.lstypechoblog.mvp.model;

import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.ErrorCallBack;
import com.roshine.lstypechoblog.http.LsRetrofitManage;
import com.roshine.lstypechoblog.http.RxUrlException;
import com.roshine.lstypechoblog.http.RxUtil;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import de.timroes.axmlrpc.XMLRPCException;
import io.reactivex.Flowable;

/**
 * @author Roshine
 * @date 2017/7/21 0:04
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LoginModel implements ContractUtil.ILoginModel {

    private ContractUtil.ILoginPresenter mILoginPresenter;
//    private ErrorCallBack errorCallBack;
//    private BaseRequest excute;
//    private BaseRequest excute2;

    public LoginModel(ContractUtil.ILoginPresenter iLoginPresenter) {
        mILoginPresenter = iLoginPresenter;
//        errorCallBack = new ErrorCallBack() {
//            @Override
//            public void error(Exception e) {
//                if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
//                    mILoginPresenter.loginFail(context.getResources().getString(R.string.load_failed_url));
//                } else if(e instanceof XMLRPCException){
//                    mILoginPresenter.loginFail(context.getResources().getString(R.string.load_failed_parameters));
//                } else{
//                    mILoginPresenter.loginFail(context.getResources().getString(R.string.load_failed));
//                }
//                errorCallBack = null;
//            }
//        };
    }

    @Override
    public Flowable<String> getAllMethod(String url, String userName, String password) {
        if(url != null && !url.equals("")
                && userName != null && !userName.equals("")
                && password != null && !password.equals("")){
            return LsRetrofitManage.getInstance()
                    .baseUrl(url)
                    .method(Constants.MethodNames.METHOD_LISTS)
                    .errorCallBack(e -> {
                        if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
                            mILoginPresenter.loginFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
                        } else if(e instanceof XMLRPCException){
                            mILoginPresenter.loginFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
                        } else{
                            mILoginPresenter.loginFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
                        }
                    })
                    .getService()
                    .compose(RxUtil.handleResult());
        }else{
            return null;
        }
//        if(url == null || url.equals("") ){
//            mILoginPresenter.loginFail(context.getResources().getString(R.string.null_url));
//        }else if(userName == null || userName.equals("")){
//            mILoginPresenter.loginFail(context.getResources().getString(R.string.null_user_name));
//        }else if(password == null || password.equals("")){
//            mILoginPresenter.loginFail(context.getResources().getString(R.string.null_user_pwd));
//        }else{
//            List<Object> parameters = new ArrayList<>();
//            return LsRetrofitManage.getInstance()
//                    .baseUrl(url)
//                    .method(Constants.MethodNames.METHOD_LISTS)
//                    .getService()
//                    .compose(RxUtil.handleResult());
//            excute = LsXmlRpcClient.request(context)
//                    .url(url)
//                    .method(Constants.MethodNames.METHOD_LISTS)
//                    .addParameters(parameters)
//                    .callback(new RequestCallBack() {
//                        @Override
//                        public void onResponse(String result) {
//                            Gson gson = new Gson();
//                            SPUtil.setParam(Constants.SharedPreferancesKeys.USER_TOTAL_METHOD, result);
//                            SPUtil.setParam(Constants.SharedPreferancesKeys.BLOG_URL, url);
//                            getAuthenty(context, userName, password);
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            mILoginPresenter.loginFail(error);
//                        }
//                    }).excute();
//        }
    }

    @Override
    public Flowable<String> getAuthenty( String userName, String password) {
        if (SPUtil.checkMethodAndUrl(Constants.MethodNames.METHOD_GET_USER_BLOG)) {
            List<Object> parameters = new ArrayList<>();
            parameters.add(userName);
            parameters.add(password);
            return LsRetrofitManage.getInstance()
                    .baseUrl(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
                    .method(Constants.MethodNames.METHOD_GET_USER_BLOG)
                    .parameters(parameters)
                    .errorCallBack(new ErrorCallBack() {
                        @Override
                        public void error(Exception e) {
                            if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
                                mILoginPresenter.loginFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
                            } else if(e instanceof XMLRPCException){
                                mILoginPresenter.loginFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
                            } else{
                                mILoginPresenter.loginFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
                            }
                        }
                    })
                    .getService()
                    .compose(RxUtil.handleResult());
//            excute2 = LsXmlRpcClient.request(context)
//                    .url(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
//                    .timeOut(30)
//                    .method(Constants.MethodNames.METHOD_GET_USER_BLOG)
//                    .addParameters(parameters)
//                    .callback(new RequestCallBack() {
//                        @Override
//                        public void onResponse(String result) {
//                            SPUtil.setParam(Constants.SharedPreferancesKeys.USER_NAME, userName);
//                            SPUtil.setParam(Constants.SharedPreferancesKeys.USER_PASSWORD, password);
//                            mILoginPresenter.loginSuccess();
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            mILoginPresenter.loginFail(error);
//                        }
//                    }).excute();
        }else {
            return null;
        }
    }
}
