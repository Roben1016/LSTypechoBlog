package com.roshine.lstypechoblog.mvp.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.LsRetrofitManage;
import com.roshine.lstypechoblog.http.RxSubUtil;
import com.roshine.lstypechoblog.http.RxUrlException;
import com.roshine.lstypechoblog.http.RxUtil;
import com.roshine.lstypechoblog.mvp.bean.ArticleListBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.model.ArticleListModel;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import de.timroes.axmlrpc.XMLRPCException;
import io.reactivex.Flowable;

/**
 * @author Roshine
 * @date 2017/7/20 23:52
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class ArticleListPresenter extends IBasePresenter<ContractUtil.IArticleListView> implements ContractUtil.IArticleListPresenter {
    private ArticleListModel mArticleListModel;

    public ArticleListPresenter() {
        mArticleListModel = new ArticleListModel(this);
    }

    @Override
    public void getArticles(Context context, int count) {
        compositeDisposable.add(mArticleListModel.getArticleList(count)
                .subscribeWith(new RxSubUtil<String>(compositeDisposable,context) {
                    @Override
                    protected void onSuccess(String s) {
                        Gson gson = new Gson();
                        try {
                            List<ArticleListBean> datas = gson.fromJson(s, new TypeToken<List<ArticleListBean>>() {
                            }.getType());
                            loadSuccess(datas);
                        }catch (Exception e){
                            getView().loadFail(context.getResources().getString(R.string.data_failed));
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));

//        List<Object> parameters = new ArrayList<>();
//        parameters.add(1);
//        parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, "").toString());
//        parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, "").toString());
//        parameters.add(10);
//
//        Flowable<String> compose = LsRetrofitManage.getInstance()
//                .baseUrl(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
//                .method("metaWeblog.getRecentPosts")
//                .parameters(parameters)
//                .errorCallBack(e -> {
//                    if (e instanceof RxUrlException || e instanceof IllegalArgumentException) {
//                        LogUtil.show(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
//                    } else if (e instanceof XMLRPCException) {
//                        LogUtil.show(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
//                    } else {
//                        LogUtil.show(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
//                    }
//                })
//                .getService()
//                .compose(RxUtil.handleResult());
//        compose.subscribeWith(new RxSubUtil<String>(compositeDisposable,context) {
//            @Override
//            protected void onSuccess(String s) {
//                LogUtil.show("测试数据："+s);
//            }
//
//            @Override
//            protected void onFail(String errorMsg) {
//                LogUtil.show("加载失败："+errorMsg);
//            }
//        });

    }

    @Override
    public void loadSuccess(List<ArticleListBean> articleListBeanList) {
        getView().loadSuccess(articleListBeanList);
    }

    @Override
    public void loadFail(String message) {
        getView().loadFail(message);
    }

}
