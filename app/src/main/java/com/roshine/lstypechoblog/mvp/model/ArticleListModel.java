package com.roshine.lstypechoblog.mvp.model;

import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
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
 * @date 2017/7/31 17:52
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class ArticleListModel implements ContractUtil.IArticleListModel {

    private ContractUtil.IArticleListPresenter mIArticleListPresenter;
//    private BaseRequest request;

    public ArticleListModel(ContractUtil.IArticleListPresenter iArticleListPresenter) {
        this.mIArticleListPresenter = iArticleListPresenter;
    }

    @Override
    public Flowable<String> getArticleList(int count) {
        if (SPUtil.checkMethodAndUrl(Constants.MethodNames.METHOD_GET_MY_ARTICLES)) {
            List<Object> parameters = new ArrayList<>();
            parameters.add(1);
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, "").toString());
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, "").toString());
            parameters.add(count);

            return LsRetrofitManage.getInstance()
                    .baseUrl(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
                    .method(Constants.MethodNames.METHOD_GET_MY_ARTICLES)
                    .parameters(parameters)
                    .errorCallBack(e -> {
                        if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
                            mIArticleListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
                        } else if(e instanceof XMLRPCException){
                            mIArticleListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
                        } else{
                            mIArticleListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
                        }
                    })
                    .getService()
                    .compose(RxUtil.handleResult());

//            request = LsXmlRpcClient.request(context)
//                    .addParameters(parameters)
//                    .url(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
//                    .method(Constants.MethodNames.METHOD_GET_MY_ARTICLES)
//                    .callback(new RequestCallBack() {
//                        @Override
//                        public void onResponse(String result) {
//                            if (result != null) {
//                                Gson gson = new Gson();
//                                List<ArticleListBean> articleLists = gson.fromJson(result, new TypeToken<List<ArticleListBean>>() {
//                                }.getType());
//                                mIArticleListPresenter.loadSuccess(articleLists);
//                            } else {
//                                mIArticleListPresenter.loadFail(context.getResources().getString(R.string.load_failed));
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            mIArticleListPresenter.loadFail(error);
//                        }
//                    }).excute();
        } else {
            mIArticleListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
            return null;
        }
    }
}
